import express from 'express'
const app = express()
const port = 3000

const delay = ms => new Promise(resolve => setTimeout(resolve, ms))

app.get('/api/account_details', async (req, res) => {
  console.log('Servidor do Banco: Requisição de detalhes da conta recebida.')

  console.log('Servidor do Banco: Buscando dados no banco de dados...')
  await delay(800)

  const accountData = {
    nome_cliente: 'Maria S.',
    numero_conta: '12345-6',
    saldo_reais: 15789.50,
    ultimo_login: new Date().toISOString()
  }

  console.log('Servidor do Banco: Dados encontrados. Enviando resposta.')
  console.log('Servidor do Banco: Detalhes da conta:', JSON.stringify(accountData, null, 2))

  res.json(accountData)
})

app.listen(port, () => {
  console.log(`Servidor legítimo do banco rodando em http://localhost:${port}`)
})