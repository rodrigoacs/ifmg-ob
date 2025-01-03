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
      throw new Error("Processo inválido.")
    }

    this.processQueue.push(process)

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
    try {
      if (this.algorithm === "priority") {
        await this.runPriority()
      } else if (this.algorithm === "round_robin") {
        await this.runRoundRobin()
      } else {
        throw new Error(`Algoritmo não suportado: ${this.algorithm}`)
      }
      this.logCallback("Escalonador finalizado.")
      this.displayGanttChart()
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

      if (availableProcesses.length === 0) {
        currentTime++
        continue
      }

      availableProcesses.sort((a, b) => {
        if (a.priority === b.priority) {
          return a.arrivalOrder - b.arrivalOrder
        }
        return a.priority - b.priority
      })

      for (let i = 0; i < this.numProcessors && availableProcesses.length > 0; i++) {
        const leastBusyProcessor = this.getLeastBusyProcessor()
        const process = availableProcesses.shift()
        this.processQueue = this.processQueue.filter((p) => p !== process)
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

        promises.push(process.run(this.logCallback, startTime))
      }

      await Promise.all(promises)
      currentTime = Math.min(...this.processorTimes)
    }
  }

  async runRoundRobin() {
    const readyQueue = [...this.processQueue]
    this.processQueue = []

    while (readyQueue.length > 0) {
      const promises = []

      for (let i = 0; i < this.numProcessors && readyQueue.length > 0; i++) {
        const process = readyQueue.shift()
        const leastBusyProcessor = this.getLeastBusyProcessor()
        const startTime = Math.max(
          this.processorTimes[leastBusyProcessor],
          this.getCurrentTime()
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
        this.processorTimes[leastBusyProcessor] = startTime + executionTime

        promises.push(
          new Promise((resolve) => {
            setTimeout(() => {
              process.remainingTime -= executionTime
              if (process.remainingTime > 0) {
                readyQueue.push(process)
              } else {
                this.logCallback(`Processo ${process.id} foi concluído.`)
              }
              resolve()
            }, executionTime * 10)
          })
        )
      }

      await Promise.all(promises)
    }
  }

  getCurrentTime() {
    return Math.min(...this.processorTimes)
  }

  getLeastBusyProcessor() {
    return this.processorTimes.indexOf(Math.min(...this.processorTimes))
  }

  displayGanttChart() {
    const ganttContainer = document.getElementById("ganttContainer")
    const scaleContainer = document.getElementById("scaleContainer")
    ganttContainer.innerHTML = ""
    scaleContainer.innerHTML = ""

    const maxTime = Math.max(...this.ganttData.map((entry) => entry.endTime))
    const scaleFactor = 50

    for (let t = 0; t <= maxTime; t++) {
      const timeMark = document.createElement("div")
      timeMark.textContent = t
      timeMark.style.position = "absolute"
      timeMark.style.left = `${t * scaleFactor}px`
      timeMark.style.transform = "translateX(-50%)"
      timeMark.style.fontSize = "12px"
      scaleContainer.appendChild(timeMark)
    }

    const rowHeight = 50
    const barHeight = 30

    this.ganttData.forEach((entry) => {
      const ganttBlock = document.createElement("div")
      ganttBlock.style.left = `${entry.startTime * scaleFactor}px`
      ganttBlock.style.top = `${(entry.processorId - 1) * rowHeight}px`
      ganttBlock.style.width = `${entry.duration * scaleFactor}px`
      ganttBlock.style.height = `${barHeight}px`
      ganttBlock.className = "gantt-block"
      ganttBlock.textContent = `${entry.processId}`
      ganttContainer.appendChild(ganttBlock)

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
