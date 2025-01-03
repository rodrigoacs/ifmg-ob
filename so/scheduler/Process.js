export class Process {
  constructor(id, arrivalOrder, duration, priority) {
    if (!id || arrivalOrder < 1 || duration < 1 || priority < 1) {
      throw new Error("Todos os parâmetros devem ser válidos e maiores que zero.")
    }

    this.id = id
    this.arrivalOrder = arrivalOrder - 1 // Ajusta para índice base 0.
    this.duration = duration
    this.priority = priority
    this.remainingTime = duration
    this.completed = false
    this.startTime = null
    this.endTime = null
  }

  async run(logCallback, currentTime) {
    try {
      // Define o tempo de início se ainda não foi iniciado.
      if (this.startTime === null) {
        this.startTime = currentTime
        logCallback(`Processo ${this.id} começou no tempo ${currentTime}.`)
      }

      // Simula execução com duração ajustada.
      await this.simulateExecution(logCallback, currentTime)

      // Finaliza o processo.
      this.remainingTime = 0
      this.completed = true
      this.endTime = currentTime + this.duration

      logCallback(`Processo ${this.id} terminou no tempo ${this.endTime}.`)
    } catch (error) {
      logCallback(`Erro ao executar o processo ${this.id}: ${error.message}`)
    }
  }

  async simulateExecution(logCallback, currentTime) {
    return new Promise((resolve) => {
      setTimeout(() => {
        logCallback(`Processo ${this.id} está sendo executado...`)
        resolve()
      }, this.duration * 10) // Multiplicador ajustado para simulação mais realista.
    })
  }
}
