import { Socket } from 'net'
import { stringify as stringifyYaml } from 'yaml'
import { stringify as stringifyToml } from '@iarna/toml'
import { stringify as stringifyCsv } from 'csv-stringify/sync'
import { js2xml } from 'xml-js'

const client = new Socket()

const FORMATS = ["JSON", "CSV", "XML", "YAML", "TOML"]
let currentIndex = 0

const data = {
  nome: "Fulano",
  cpf: "10326709722",
  idade: 45,
  mensagem: "segue o comprovante de endereço"
}

function sendMessageByIndex() {
  if (currentIndex >= FORMATS.length) {
    console.log("\nTodos os formatos foram enviados. Fechando conexão.")
    client.destroy()
    return
  }

  const format = FORMATS[currentIndex]
  let serializedData

  switch (format) {
    case 'CSV':
      serializedData = stringifyCsv([Object.values(data)])
      break
    case 'XML':
      serializedData = js2xml({ dados: data }, { compact: true, spaces: 0 })
      break
    case 'YAML':
      serializedData = stringifyYaml(data)
      break
    case 'TOML':
      serializedData = stringifyToml(data)
      break
    case 'JSON':
      serializedData = JSON.stringify(data)
      break
  }

  const messageToSend = `${format}|${serializedData}`
  console.log(`\nEnviando dados em formato ${format}`)
  client.write(messageToSend)
}

client.connect(8080, 'localhost', () => {
  console.log('Conectado ao servidor. Iniciando envio sequencial...')
  sendMessageByIndex()
})

client.on('data', (data) => {
  console.log('Recebido do servidor: ' + data)
  currentIndex++
  setTimeout(sendMessageByIndex, 1000)
})

client.on('close', () => {
  console.log('Conexão fechada pelo servidor.')
})

client.on('error', (err) => {
  console.error('Erro de conexão: ' + err.message)
})

