export class Process {
  constructor(id, arrivalOrder, duration, priority) {
    this.id = id
    this.arrivalOrder = arrivalOrder - 1
    this.duration = duration
    this.priority = priority
    this.remainingTime = duration
    this.completed = false
    this.startTime = null
    this.endTime = null
  }

  run(logCallback, currentTime) {
    // Define o tempo de início se ainda não foi iniciado.
    if (this.startTime === null) this.startTime = currentTime

    logCallback(`Processo ${this.id} começou no tempo ${currentTime}.`)

    return new Promise((resolve) => {
      setTimeout(() => {
        this.remainingTime = 0 // Marca o tempo restante como 0 (processo concluído).
        this.completed = true // Indica que o processo foi concluído.
        this.endTime = currentTime + this.duration // Define o tempo de término.

        // Registra o término do processo.
        logCallback(`Processo ${this.id} terminou no tempo ${this.endTime}.`)
        resolve() // Finaliza a promessa.
      }, this.duration * 1) // Duração simulada em milissegundos.
    })
  }
}
