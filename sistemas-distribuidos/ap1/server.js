import { createServer } from 'net'
import { parseString } from 'xml2js'
import { parse as parseYaml } from 'yaml'
import { parse as parseToml } from '@iarna/toml'
import { parse as parseCsv } from 'csv-parse/sync'

const PORT = 8080

const parseMessage = (rawMessage) => {
  const separatorIndex = rawMessage.indexOf('|')
  if (separatorIndex === -1) {
    throw new Error('Formato inválido. Mensagem não contém "|" como separador.')
  }
  return {
    format: rawMessage.substring(0, separatorIndex),
    content: rawMessage.substring(separatorIndex + 1)
  }
}

const parsers = {
  JSON: (content) => JSON.parse(content),
  CSV: (content) => {
    const [record] = parseCsv(content.trim())
    return {
      nome: record[0],
      cpf: record[1],
      idade: parseInt(record[2]),
      mensagem: record[3]
    }
  },
  XML: (content) => {
    return new Promise((resolve, reject) => {
      parseString(content, { explicitArray: false, trim: true }, (err, result) => {
        if (err || !result?.dados) reject(new Error('XML malformado'))
        resolve({
          nome: result.dados.nome,
          cpf: result.dados.cpf,
          idade: parseInt(result.dados.idade),
          mensagem: result.dados.mensagem
        })
      })
    })
  },
  YAML: (content) => parseYaml(content),
  TOML: (content) => parseToml(content)
}

const handleConnection = (socket) => {
  console.log('Cliente conectado')

  socket.on('data', async (data) => {
    try {
      const { format, content } = parseMessage(data.toString())
      const parser = parsers[format]

      if (!parser) {
        throw new Error(`Formato desconhecido: ${format}`)
      }

      const receivedData = await parser(content)
      console.log('Dados desserializados com sucesso:', receivedData)
      socket.write(`Dados em ${format} recebidos com sucesso!`)

    } catch (error) {
      console.error('Erro ao processar dados:', error.message)
      socket.write(`Erro: ${error.message}`)
    }
  })

  socket.on('end', () => console.log('Cliente desconectado'))
  socket.on('error', (err) => console.error('Erro no socket:', err.message))
}

const server = createServer(handleConnection)
server.listen(PORT, () => console.log(`Servidor ouvindo na porta ${PORT}`))
