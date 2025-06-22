const delay = ms => new Promise(resolve => setTimeout(resolve, ms))
const serverUrl = 'http://banco-seguro.com:8081/api/account_details'

async function consultarSaldo() {
  try {
    console.log(`Cliente: Conectando ao internet banking em ${serverUrl}...`)
    const response = await fetch(serverUrl)

    if (!response.ok) {
      throw new Error(`Erro de rede: ${response.statusText}`)
    }

    const data = await response.json()

    console.log('\n--- Detalhes da Conta Recebidos ---')
    console.log(`Bem-vinda, ${data.nome_cliente}`)
    console.log(`Conta: ${data.numero_conta}`)
    console.log(`Saldo: R$ ${data.saldo_reais.toFixed(2).replace('.', ',')}`)
    console.log('------------------------------------')

    if (data.aviso_importante) {
      console.log('\n\x1b[31m%s\x1b[0m', `!!! AVISO DO BANCO !!!`) /
        console.log('\x1b[33m%s\x1b[0m', `${data.aviso_importante.mensagem}`)
      console.log('\x1b[33m%s\x1b[0m', `Acesse: ${data.aviso_importante.link_acao}`)
      console.log('------------------------------------\n')
    }

  } catch (error) {
    console.error('Cliente: Não foi possível acessar sua conta!', error.message)
  }
}

consultarSaldo()