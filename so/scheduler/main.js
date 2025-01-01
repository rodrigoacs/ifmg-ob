import { Process } from "./Process.js"
import { Scheduler } from "./Scheduler.js"

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
