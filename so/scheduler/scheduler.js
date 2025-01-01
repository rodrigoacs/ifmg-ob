const EXECUTION_TIME = 1000

class Process {
  constructor(id, arrivalOrder, duration, priority) {
    this.id = id
    this.arrivalOrder = arrivalOrder
    this.duration = duration
    this.priority = priority
    this.remainingTime = duration
    this.completed = false
    this.startTime = null
    this.endTime = null
  }

  run(logCallback, currentTime) {
    if (this.startTime === null) this.startTime = currentTime
    logCallback(`Processo ${this.id} começou no tempo ${currentTime}.`)
    return new Promise((resolve) => {
      setTimeout(() => {
        this.remainingTime = 0
        this.completed = true
        this.endTime = currentTime + this.duration
        logCallback(`Processo ${this.id} terminou no tempo ${this.endTime}.`)
        resolve()
      }, this.duration * EXECUTION_TIME) // Tempo de execução simulado
    })
  }
}

class Scheduler {
  constructor(numProcessors, logCallback, algorithm = "priority", quantum = 2) {
    this.numProcessors = numProcessors
    this.logCallback = logCallback
    this.processQueue = []
    this.ganttData = []
    this.processorTimes = Array.from({ length: numProcessors }, () => 0) // Tempos de ocupação de cada processador
    this.currentTime = 0
    this.algorithm = algorithm // Define o algoritmo: "priority" ou "round_robin"
    this.quantum = quantum // Tempo de quantum para Round Robin
  }

  addProcess(process) {
    this.processQueue.push(process)

    // Ordenar por prioridade somente para o algoritmo de prioridade
    if (this.algorithm === "priority") {
      this.processQueue.sort((a, b) => a.priority - b.priority)
    }
  }

  async run() {
    this.logCallback(`Iniciando o escalonador (${this.algorithm})...`)

    if (this.algorithm === "priority") {
      await this.runPriority()
    } else if (this.algorithm === "round_robin") {
      await this.runRoundRobin()
    }

    this.logCallback("Escalonador finalizado.")
    this.displayGanttChart()
  }

  async runPriority() {
    while (this.processQueue.length > 0) {
      const promises = []

      // Iterar sobre os processadores disponíveis
      for (let i = 0; i < this.numProcessors; i++) {
        if (this.processQueue.length > 0) {
          const process = this.processQueue.shift()
          const startTime = Math.max(this.processorTimes[i], this.getCurrentTime())

          this.ganttData.push({
            processId: process.id,
            startTime: startTime,
            duration: process.duration,
            endTime: startTime + process.duration,
            processorId: i + 1,
          })

          this.logCallback(
            `Processo ${process.id} alocado no processador ${i + 1} no tempo ${startTime}.`
          )

          this.processorTimes[i] = startTime + process.duration

          promises.push(process.run(this.logCallback, startTime))
        }
      }

      // Aguarda todos os processadores concluírem suas tarefas atuais
      await Promise.all(promises)
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
        const startTime = Math.max(this.processorTimes[leastBusyProcessor], this.getCurrentTime())
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
            }, executionTime * EXECUTION_TIME)
          })
        )
      }

      await Promise.all(promises)
    }
  }

  getCurrentTime() {
    return Math.min(...this.processorTimes) // O tempo global é o menor entre os tempos dos processadores
  }

  getLeastBusyProcessor() {
    return this.processorTimes.indexOf(Math.min(...this.processorTimes)) // Retorna o índice do processador menos ocupado
  }

  displayGanttChart() {
    const ganttContainer = document.getElementById("ganttContainer")
    const scaleContainer = document.getElementById("scaleContainer")
    ganttContainer.innerHTML = ""
    scaleContainer.innerHTML = ""

    // Determina o tempo total (máximo)
    const maxTime = Math.max(...this.ganttData.map((entry) => entry.endTime))
    const scaleFactor = 50 // Multiplicador para ajustar a escala no Gantt

    // Adiciona a escala (números de tempo)
    for (let t = 0; t <= maxTime; t++) {
      const timeMark = document.createElement("div")
      timeMark.textContent = t
      timeMark.style.position = "absolute"
      timeMark.style.left = `${t * scaleFactor}px`
      timeMark.style.transform = "translateX(-50%)"
      timeMark.style.fontSize = "12px"
      scaleContainer.appendChild(timeMark)
    }

    // Configura os blocos do Gantt
    const rowHeight = 50
    const barHeight = 30

    this.ganttData.forEach((entry) => {
      // Bloco do Gantt
      const ganttBlock = document.createElement("div")
      ganttBlock.style.left = `${entry.startTime * scaleFactor}px`
      ganttBlock.style.top = `${(entry.processorId - 1) * rowHeight}px` // Linha do processador
      ganttBlock.style.width = `${entry.duration * scaleFactor}px`
      ganttBlock.style.height = `${barHeight}px`
      ganttBlock.className = "gantt-block"
      ganttBlock.textContent = `${entry.processId}`
      ganttContainer.appendChild(ganttBlock)

      // Nome do Processador ao lado
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

const numProcessorsInput = document.getElementById("numProcessors")
const numProcessesInput = document.getElementById("numProcesses")
const quantumInput = document.getElementById("quantum")
const algorithmSelect = document.getElementById("algorithm")
const generateProcessesButton = document.getElementById("generateProcesses")
const processTableBody = document.querySelector("#processTable tbody")
const runSchedulerButton = document.getElementById("runScheduler")
const executionLogElement = document.getElementById("executionLog")

let scheduler

generateProcessesButton.addEventListener("click", () => {
  processTableBody.innerHTML = ""
  const numProcesses = parseInt(numProcessesInput.value)
  const processes = []

  for (let i = 0; i < numProcesses; i++) {
    const duration = Math.floor(Math.random() * 5) + 1
    const priority = Math.floor(Math.random() * 10) + 1
    processes.push(new Process(`P${i + 1}`, i + 1, duration, priority))
  }

  scheduler = new Scheduler(
    parseInt(numProcessorsInput.value),
    logMessage,
    algorithmSelect.value,
    parseInt(quantumInput.value)
  )

  processes.forEach((process) => {
    scheduler.addProcess(process)
    const row = document.createElement("tr")
    row.innerHTML = `
      <td>${process.id}</td>
      <td>${process.arrivalOrder}</td>
      <td>${process.duration}</td>
      <td>${process.priority}</td>
    `
    processTableBody.appendChild(row)
  })
})

runSchedulerButton.addEventListener("click", async () => {
  executionLogElement.innerHTML = ""
  await scheduler.run()
})

function logMessage(message) {
  const logEntry = document.createElement("div")
  logEntry.textContent = message
  executionLogElement.appendChild(logEntry)
  executionLogElement.scrollTop = executionLogElement.scrollHeight
}
