import { Process } from "./Process.js"
import { Scheduler } from "./Scheduler.js"

const numProcessorsInput = document.getElementById("numProcessors")
const numProcessesInput = document.getElementById("numProcesses")
const quantumInput = document.getElementById("quantum")
const algorithmSelect = document.getElementById("algorithm")
const processTableBody = document.querySelector("#processTable tbody")
const runSchedulerButton = document.getElementById("runScheduler")
const executionLogElement = document.getElementById("executionLog")
const resetButton = document.getElementById("resetSimulation")
const generateProcessesButton = document.getElementById("generateProcesses")
const manualIdInput = document.getElementById("manualId")
const manualArrivalOrderInput = document.getElementById("manualArrivalOrder")
const manualDurationInput = document.getElementById("manualDuration")
const manualPriorityInput = document.getElementById("manualPriority")
const addManualProcessButton = document.getElementById("addManualProcess")
const ganttContainer = document.getElementById("ganttContainer")

let scheduler = createScheduler()

function createScheduler() {
  return new Scheduler(
    parseInt(numProcessorsInput.value || 1),
    logMessage,
    algorithmSelect.value || "priority",
    parseInt(quantumInput.value || 2)
  )
}

function validateInputs(inputs) {
  return inputs.every(({ value, min, fieldName }) => {
    const numValue = parseInt(value)
    if (isNaN(numValue) || numValue < min) {
      alert(`O campo '${fieldName}' deve ter um valor mínimo de ${min}.`)
      return false
    }
    return true
  })
}

// Geração de processos aleatórios
generateProcessesButton.addEventListener("click", () => {
  processTableBody.innerHTML = ""

  const inputsValid = validateInputs([
    { value: numProcessorsInput.value, min: 1, fieldName: "Número de Processadores" },
    { value: numProcessesInput.value, min: 1, fieldName: "Quantidade de Processos" },
  ])
  if (!inputsValid) return

  const numProcesses = parseInt(numProcessesInput.value)
  const processes = Array.from({ length: numProcesses }, (_, i) => {
    const id = `P${i + 1}`
    return new Process(
      id,
      i + 1,
      Math.floor(Math.random() * 5) + 1,
      Math.floor(Math.random() * 10) + 1
    )
  })

  scheduler = createScheduler()
  processes.forEach((process) => {
    scheduler.addProcess(process)
    addProcessToTable(process)
  })

  alert(`${numProcesses} processos gerados aleatoriamente!`)
})

// Adicionar processo manualmente
addManualProcessButton.addEventListener("click", () => {
  const inputsValid = validateInputs([
    { value: manualIdInput.value.trim(), min: 1, fieldName: "ID" },
    { value: manualArrivalOrderInput.value, min: 1, fieldName: "Ordem de Chegada" },
    { value: manualDurationInput.value, min: 1, fieldName: "Tempo de Execução" },
    { value: manualPriorityInput.value, min: 1, fieldName: "Prioridade" },
  ])
  if (!inputsValid) return

  const id = manualIdInput.value.trim()
  const arrivalOrder = parseInt(manualArrivalOrderInput.value)
  const duration = parseInt(manualDurationInput.value)
  const priority = parseInt(manualPriorityInput.value)

  if (Array.from(processTableBody.querySelectorAll("tr")).some((row) => row.cells[0].textContent === id)) {
    alert("O ID do processo já existe. Escolha outro.")
    return
  }

  const newProcess = new Process(id, arrivalOrder, duration, priority)
  scheduler.addProcess(newProcess)
  addProcessToTable(newProcess);

  [manualIdInput, manualArrivalOrderInput, manualDurationInput, manualPriorityInput].forEach((input) => (input.value = ""))
  alert(`Processo ${id} adicionado com sucesso!`)
})

// Executar escalonador
runSchedulerButton.addEventListener("click", async () => {
  if (!scheduler || scheduler.processQueue.length === 0) {
    alert("Por favor, adicione pelo menos um processo antes de executar o escalonador.")
    return
  }

  runSchedulerButton.disabled = true
  executionLogElement.innerHTML = ""
  await scheduler.run()
  alert("Escalonador finalizado com sucesso!")
  runSchedulerButton.disabled = false
})

// Resetar simulador
resetButton.addEventListener("click", () => {
  scheduler = createScheduler()
  processTableBody.innerHTML = ""
  executionLogElement.innerHTML = ""
  ganttContainer.innerHTML = ""
  runSchedulerButton.disabled = false
  alert("Simulação reiniciada!")
})

// Funções utilitárias
function logMessage(message) {
  const logEntry = document.createElement("div")
  logEntry.textContent = message
  executionLogElement.appendChild(logEntry)
  executionLogElement.scrollTop = executionLogElement.scrollHeight
}

function addProcessToTable(process) {
  const row = document.createElement("tr")
  row.innerHTML = `
    <td>${process.id}</td>
    <td>${process.arrivalOrder}</td>
    <td>${process.duration}</td>
    <td>${process.priority}</td>
  `
  processTableBody.appendChild(row)
}
