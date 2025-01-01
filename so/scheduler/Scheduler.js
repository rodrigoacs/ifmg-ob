export class Scheduler {
  constructor(numProcessors, logCallback, algorithm = "priority", quantum = 2) {
    this.numProcessors = numProcessors
    this.logCallback = logCallback
    this.processQueue = []
    this.ganttData = []
    this.processorTimes = Array(numProcessors).fill(0)
    this.algorithm = algorithm
    this.quantum = quantum
  }

  addProcess(process) {
    // Adiciona um processo à fila.
    this.processQueue.push(process)

    // Ordena a fila se o algoritmo for "priority".
    if (this.algorithm === "priority") {
      this.processQueue.sort((a, b) => {
        // Ordena por prioridade, desempate pela ordem de chegada.
        if (a.priority === b.priority) {
          return a.arrivalOrder - b.arrivalOrder
        }
        return a.priority - b.priority
      })
    }
  }


  async run() {
    this.logCallback(`Iniciando o escalonador (${this.algorithm})...`)
    await (this.algorithm === "priority" ? this.runPriority() : this.runRoundRobin())
    this.logCallback("Escalonador finalizado.")
    this.displayGanttChart()
  }

  async runPriority() {
    let currentTime = 0 // Marca o tempo atual.

    while (this.processQueue.length > 0) {
      const promises = []

      // Filtra os processos disponíveis com base no tempo atual.
      const availableProcesses = this.processQueue.filter(process => process.arrivalOrder <= currentTime)

      if (availableProcesses.length === 0) {
        // Avança o tempo se nenhum processo está disponível.
        currentTime++
        continue
      }

      // Ordena os processos disponíveis por prioridade e desempata pela ordem de chegada.
      availableProcesses.sort((a, b) => {
        if (a.priority === b.priority) {
          return a.arrivalOrder - b.arrivalOrder
        }
        return a.priority - b.priority
      })

      // Aloca processos disponíveis para os processadores.
      for (let i = 0; i < this.numProcessors && availableProcesses.length > 0; i++) {
        const leastBusyProcessor = this.getLeastBusyProcessor() // Obtém o processador menos ocupado.
        const process = availableProcesses.shift() // Remove o processo de maior prioridade.
        this.processQueue = this.processQueue.filter(p => p !== process) // Remove da fila principal.
        const startTime = Math.max(this.processorTimes[leastBusyProcessor], currentTime) // Calcula o tempo de início no processador.

        // Adiciona os dados ao Gantt.
        this.ganttData.push({
          processId: process.id,
          startTime: startTime,
          duration: process.duration,
          endTime: startTime + process.duration,
          processorId: leastBusyProcessor + 1, // Identifica o processador.
        })

        this.logCallback(`Processo ${process.id} alocado no processador ${leastBusyProcessor + 1} no tempo ${startTime}.`)
        this.processorTimes[leastBusyProcessor] = startTime + process.duration // Atualiza o tempo do processador.

        // Executa o processo.
        promises.push(process.run(this.logCallback, startTime))
      }

      await Promise.all(promises) // Aguarda os processos alocados finalizarem.
      currentTime = Math.min(...this.processorTimes) // Atualiza o tempo atual para o próximo processador disponível.
    }
  }

  async runRoundRobin() {
    // Implementa o escalonamento por Round Robin.
    const readyQueue = [...this.processQueue] // Copia a fila de processos para a fila pronta.
    this.processQueue = [] // Limpa a fila principal.

    while (readyQueue.length > 0) {
      const promises = []
      for (let i = 0; i < this.numProcessors && readyQueue.length > 0; i++) {
        const process = readyQueue.shift() // Remove o próximo processo da fila.
        const leastBusyProcessor = this.getLeastBusyProcessor() // Obtém o processador menos ocupado.
        const startTime = Math.max(this.processorTimes[leastBusyProcessor], this.getCurrentTime()) // Calcula o tempo de início.
        const executionTime = Math.min(process.remainingTime, this.quantum) // Calcula o tempo de execução.

        // Armazena os dados no Gantt.
        this.ganttData.push({
          processId: process.id,
          startTime: startTime,
          duration: executionTime,
          endTime: startTime + executionTime,
          processorId: leastBusyProcessor + 1,
        })

        this.logCallback(`Processo ${process.id} executando no processador ${leastBusyProcessor + 1} de ${startTime} a ${startTime + executionTime}.`)
        this.processorTimes[leastBusyProcessor] = startTime + executionTime // Atualiza o tempo do processador.

        promises.push(new Promise((resolve) => {
          setTimeout(() => {
            process.remainingTime -= executionTime // Atualiza o tempo restante do processo.
            if (process.remainingTime > 0) {
              readyQueue.push(process) // Reinsere o processo na fila se não foi concluído.
            } else {
              this.logCallback(`Processo ${process.id} foi concluído.`)
            }
            resolve()
          }, executionTime * 1) // Simula o tempo de execução.
        }))
      }
      await Promise.all(promises) // Aguarda todos os processos terminarem.
    }
  }

  getCurrentTime() {
    return Math.min(...this.processorTimes)
  }

  getLeastBusyProcessor() {
    return this.processorTimes.indexOf(Math.min(...this.processorTimes))
  }

  displayGanttChart() {
    // Renderiza o diagrama de Gantt.
    const ganttContainer = document.getElementById("ganttContainer")
    const scaleContainer = document.getElementById("scaleContainer")
    ganttContainer.innerHTML = ""
    scaleContainer.innerHTML = ""

    const maxTime = Math.max(...this.ganttData.map((entry) => entry.endTime)) // Calcula o tempo total.
    const scaleFactor = 50 // Define o tamanho das unidades de tempo.

    // Renderiza a escala de tempo.
    for (let t = 0; t <= maxTime; t++) {
      const timeMark = document.createElement("div")
      timeMark.textContent = t
      timeMark.style.position = "absolute"
      timeMark.style.left = `${t * scaleFactor}px`
      timeMark.style.transform = "translateX(-50%)"
      timeMark.style.fontSize = "12px"
      scaleContainer.appendChild(timeMark)
    }

    const rowHeight = 50 // Altura das linhas no Gantt.
    const barHeight = 30 // Altura dos blocos no Gantt.

    this.ganttData.forEach((entry) => {
      // Renderiza os blocos de processos.
      const ganttBlock = document.createElement("div")
      ganttBlock.style.left = `${entry.startTime * scaleFactor}px`
      ganttBlock.style.top = `${(entry.processorId - 1) * rowHeight}px`
      ganttBlock.style.width = `${entry.duration * scaleFactor}px`
      ganttBlock.style.height = `${barHeight}px`
      ganttBlock.className = "gantt-block"
      ganttBlock.textContent = `${entry.processId}`
      ganttContainer.appendChild(ganttBlock)

      // Adiciona rótulos de processadores.
      const processLabel = document.createElement("div")
      processLabel.textContent = `CPU ${entry.processorId}`
      processLabel.style.position = "absolute"
      processLabel.style.top = `${(entry.processorId - 1) * rowHeight}px`
      processLabel.style.left = "-120px"
      processLabel.style.width = "100px"
      processLabel.style.textAlign = "right"
      processLabel.style.fontSize = "14px"
      ganttContainer.appendChild(processLabel)
    })
  }
}
