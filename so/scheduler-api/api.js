import express from 'express'
import { Process } from './Process.js'
import cors from 'cors'

const app = express()
app.use(cors())
app.use(express.json())

let processList = []

app.post('/api/process', (req, res) => {
  try {
    const { id, arrivalOrder, duration, priority } = req.body
    if (!id || !arrivalOrder || !duration || !priority) {
      throw new Error('Todos os campos são obrigatórios.')
    }
    const process = new Process(id, arrivalOrder, duration, priority)
    processList.push(process)
    res.status(201).json(process)
  } catch (error) {
    res.status(400).json({ error: error.message })
  }
})

app.get('/api/process', (req, res) => {
  res.status(200).json(processList)
})

app.delete('/api/process', (req, res) => {
  processList = []
  res.status(200).json({ message: 'Lista de processos resetada com sucesso.' })
})

app.listen(5000, () => {
  console.log('Server is running on http://localhost:5000')
})
