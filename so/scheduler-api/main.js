import { Scheduler } from "./Scheduler.js"
import { Process } from "./Process.js"

const apiEndpoint = "http://localhost:5000/api/process"
const processTableBody = document.querySelector("#processTable tbody")
const runSchedulerButton = document.getElementById("runScheduler")
const resetProcessesButton = document.getElementById("resetProcesses")
const loadProcessesButton = document.getElementById("loadProcesses")
const executionLogElement = document.getElementById("executionLog")

let scheduler = null

function createScheduler() {
  const algorithm = document.getElementById("algorithm").value // Obtém o valor do seletor
  const quantum = parseInt(document.getElementById("quantum").value || 2)
  const numProcessors = parseInt(document.getElementById("numProcessors").value || 1)

  if (algorithm === "round_robin" && quantum <= 0) {
    alert("O Quantum deve ser maior que 0 para Round Robin.")
    return null
  }

  return new Scheduler(numProcessors, logMessage, algorithm, quantum)
}

async function loadProcesses() {
  try {
    const response = await fetch(apiEndpoint)
    if (!response.ok) {
      throw new Error(`Erro ao carregar processos: ${response.statusText}`)
    }

    const processesData = await response.json()
    const processes = processesData.map(
      (data) => new Process(data.id, data.arrivalOrder, data.duration, data.priority)
    )

    renderProcesses(processes)

    scheduler = createScheduler()
    if (!scheduler) {
      throw new Error("Configurações inválidas para o escalonador.")
    }

    processes.forEach((process) => {
      scheduler.addProcess(process)
    })

    console.log("Processos carregados:", scheduler.processQueue) // Debug log
  } catch (error) {
    console.error(error)
    alert(`Falha ao carregar os processos: ${error.message}`)
  }
}

async function resetProcesses() {
  try {
    const response = await fetch(apiEndpoint, { method: "DELETE" })
    if (!response.ok) {
      throw new Error(`Erro ao resetar processos: ${response.statusText}`)
    }
    processTableBody.innerHTML = ""
    executionLogElement.innerHTML = ""
    document.getElementById("ganttContainer").innerHTML = "" // Clear Gantt Chart
    scheduler = null
    alert("Processos resetados com sucesso!")
  } catch (error) {
    console.error(error)
    alert("Falha ao resetar os processos.")
  }
}

async function runScheduler() {
  if (!scheduler || scheduler.processQueue.length === 0) {
    console.error("Scheduler ou fila de processos está vazia:", scheduler)
    alert("Nenhum processo carregado para execução.")
    return
  }

  try {
    runSchedulerButton.disabled = true
    executionLogElement.innerHTML = ""
    document.getElementById("ganttContainer").innerHTML = "" // Clear Gantt Chart

    await scheduler.run()
    renderGanttChart()
  } catch (error) {
    console.error(error)
    alert(`Erro durante a execução do escalonador: ${error.message}`)
  } finally {
    runSchedulerButton.disabled = false
  }
}

function renderGanttChart() {
  const ganttContainer = document.getElementById("ganttContainer")
  const scaleContainer = document.getElementById("scaleContainer")
  const scaleFactor = 50 // pixels por unidade de tempo
  const rowHeight = 40 // altura de cada linha no gráfico

  // Limpa o conteúdo anterior
  ganttContainer.innerHTML = ""
  scaleContainer.innerHTML = ""

  // Renderiza a escala de tempo
  const maxTime = Math.max(...scheduler.ganttData.map((entry) => entry.endTime))
  for (let t = 0; t <= maxTime; t++) {
    const scaleMark = document.createElement("div")
    scaleMark.textContent = t
    scaleMark.style.position = "absolute"
    scaleMark.style.left = `${t * scaleFactor}px`
    scaleMark.style.transform = "translateX(-50%)"
    scaleMark.style.fontSize = "14px"
    scaleMark.style.color = "#fff" // Cor branca para contraste
    scaleContainer.appendChild(scaleMark)
  }

  // Renderiza os blocos do Gantt
  scheduler.ganttData.forEach((entry, index) => {
    const ganttBlock = document.createElement("div")
    ganttBlock.style.position = "absolute"
    ganttBlock.style.left = `${entry.startTime * scaleFactor}px`
    ganttBlock.style.top = `${(entry.processorId - 1) * rowHeight}px` // Posiciona com base no processador
    ganttBlock.style.width = `${entry.duration * scaleFactor}px`
    ganttBlock.style.height = "30px"
    ganttBlock.style.backgroundColor = "#1e88e5"
    ganttBlock.style.color = "#fff"
    ganttBlock.style.textAlign = "center"
    ganttBlock.style.lineHeight = "30px"
    ganttBlock.style.borderRadius = "5px"
    ganttBlock.textContent = `${entry.processId}`

    ganttContainer.appendChild(ganttBlock)
  })

  // Ajusta o tamanho do container do Gantt dinamicamente
  ganttContainer.style.position = "relative"
  ganttContainer.style.height = `${scheduler.numProcessors * rowHeight}px`
}


function renderProcesses(processes) {
  processTableBody.innerHTML = ""
  processes.forEach((process) => {
    const row = document.createElement("tr")
    row.innerHTML = `
      <td>${process.id}</td>
      <td>${process.arrivalOrder}</td>
      <td>${process.duration}</td>
      <td>${process.priority}</td>
    `
    processTableBody.appendChild(row)
  })
}

function logMessage(message) {
  const logEntry = document.createElement("div")
  logEntry.textContent = message
  executionLogElement.appendChild(logEntry)
  executionLogElement.scrollTop = executionLogElement.scrollHeight
}

loadProcessesButton.addEventListener("click", loadProcesses)
resetProcessesButton.addEventListener("click", resetProcesses)
runSchedulerButton.addEventListener("click", runScheduler)
