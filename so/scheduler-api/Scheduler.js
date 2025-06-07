export class Scheduler {
  constructor(numProcessors, logCallback, algorithm = "priority", quantum = 2) {
    if (numProcessors < 1 || quantum < 1) {
      throw new Error("O número de processadores e o quantum devem ser maiores que zero.")
    }

    this.numProcessors = numProcessors
    this.logCallback = logCallback
    this.processQueue = []
    this.ganttData = []
    this.processorTimes = Array(numProcessors).fill(0)
    this.algorithm = algorithm
    this.quantum = quantum
  }

  addProcess(process) {
    if (!process || typeof process !== "object") {
      throw new Error("Processo inválido.");
    }
    this.processQueue.push(process);
  
    // Ordena por prioridade e desempate pela ordem de chegada
    this.processQueue.sort((a, b) => {
      if (a.priority === b.priority) {
        return a.arrivalOrder - b.arrivalOrder;
      }
      return a.priority - b.priority;
    });
  }
  

  async run() {
    this.logCallback(`Iniciando o escalonador (${this.algorithm})...`)
    try {
      if (this.algorithm === "priority") {
        await this.runPriority()
      } else if (this.algorithm === "round_robin") {
        await this.runRoundRobin()
      } else {
        throw new Error(`Algoritmo não suportado: ${this.algorithm}`)
      }
      this.logCallback("Escalonador finalizado.")
    } catch (error) {
      this.logCallback(`Erro no escalonador: ${error.message}`)
    }
  }

  async runPriority() {
    let currentTime = 0

    while (this.processQueue.length > 0) {
      const promises = []
      const availableProcesses = this.processQueue.filter(
        (process) => process.arrivalOrder <= currentTime
      )

      // Avança o tempo se nenhum processo está disponível no momento atual
      if (availableProcesses.length === 0) {
        currentTime++
        continue
      }

      for (let i = 0; i < this.numProcessors && availableProcesses.length > 0; i++) {
        const leastBusyProcessor = this.getLeastBusyProcessor()
        const process = availableProcesses.shift() // Remove o processo disponível com maior prioridade
        this.processQueue = this.processQueue.filter((p) => p !== process) // Remove da fila principal

        const startTime = Math.max(
          this.processorTimes[leastBusyProcessor],
          currentTime
        )

        this.ganttData.push({
          processId: process.id,
          startTime: startTime,
          duration: process.duration,
          endTime: startTime + process.duration,
          processorId: leastBusyProcessor + 1,
        })

        this.logCallback(
          `Processo ${process.id} alocado no processador ${leastBusyProcessor + 1} no tempo ${startTime}.`
        )

        this.processorTimes[leastBusyProcessor] = startTime + process.duration

        // Executa o processo
        promises.push(process.run(this.logCallback, startTime))
      }

      // Aguarda todos os processadores terminarem suas execuções simultâneas
      await Promise.all(promises)

      // Avança o tempo para o menor tempo disponível entre os processadores
      currentTime = Math.min(...this.processorTimes)
    }
  }


  async runRoundRobin() {
    this.logCallback("Executando com Round Robin...")
    const readyQueue = [...this.processQueue] // Copia os processos para a fila de prontos
    this.processQueue = [] // Limpa a fila principal após copiar

    let currentTime = 0

    while (readyQueue.length > 0) {
      const promises = []

      for (let i = 0; i < this.numProcessors && readyQueue.length > 0; i++) {
        const process = readyQueue.shift() // Remove o próximo processo da fila
        const leastBusyProcessor = this.getLeastBusyProcessor()
        const startTime = Math.max(
          this.processorTimes[leastBusyProcessor],
          currentTime
        )
        const executionTime = Math.min(process.remainingTime, this.quantum)

        this.ganttData.push({
          processId: process.id,
          startTime: startTime,
          duration: executionTime,
          endTime: startTime + executionTime,
          processorId: leastBusyProcessor + 1,
        })

        this.logCallback(
          `Processo ${process.id} executando no processador ${leastBusyProcessor + 1} de ${startTime} a ${startTime + executionTime}.`
        )

        // Atualiza o tempo ocupado pelo processador
        this.processorTimes[leastBusyProcessor] = startTime + executionTime

        // Simula a execução do processo
        promises.push(
          new Promise((resolve) => {
            setTimeout(() => {
              process.remainingTime -= executionTime

              if (process.remainingTime > 0) {
                readyQueue.push(process) // Reinsere o processo na fila se não terminou
              } else {
                this.logCallback(`Processo ${process.id} foi concluído.`)
              }
              resolve()
            }, executionTime * 10) // Tempo de simulação ajustado
          })
        )
      }

      await Promise.all(promises) // Aguarda todos os processadores completarem suas execuções
      currentTime = Math.min(...this.processorTimes) // Atualiza o tempo para o próximo evento
    }
  }


  getLeastBusyProcessor() {
    return this.processorTimes.indexOf(Math.min(...this.processorTimes))
  }
}
