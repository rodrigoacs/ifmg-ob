const API_URL = 'http://127.0.0.1:5000/'
const logDiv = document.getElementById('response-log')

function showToast(message, type = 'success') {
  const container = document.getElementById('toast-container')
  const toast = document.createElement('div')
  toast.className = `toast ${type}`
  toast.textContent = message
  container.appendChild(toast)

  setTimeout(() => {
    toast.style.animation = 'slideOut 0.5s forwards'
    toast.addEventListener('animationend', () => toast.remove())
  }, 3000)
}

function logResponse(data, status) {
  const timestamp = new Date().toLocaleTimeString()
  const statusText = status >= 200 && status < 300 ? 'SUCCESS' : 'ERROR'
  logDiv.textContent =
    `[${timestamp}] - ${statusText} (${status})\n\n` + JSON.stringify(data, null, 2)
}

function setLoading(button, isLoading) {
  if (isLoading) {
    button.disabled = true
    button.dataset.originalHtml = button.innerHTML
    button.innerHTML = '<span class="loader"></span> Carregando...'
  } else {
    button.disabled = false
    button.innerHTML = button.dataset.originalHtml
  }
}

async function handleRequest(url, options, button) {
  setLoading(button, true)
  try {
    const response = await fetch(url, options)
    const data = await response.json()

    logResponse(data, response.status)

    if (!response.ok) {
      throw new Error(data.error || 'Ocorreu um erro na requisição.')
    }

    showToast('Operação realizada com sucesso!', 'success')
    return data
  } catch (error) {
    showToast(error.message, 'error')
    logResponse({ error: error.message }, 500)
  } finally {
    setLoading(button, false)
  }
}

const btnAdicionar = document.getElementById('btnAdicionar')
btnAdicionar.addEventListener('click', () => {
  const name = document.getElementById('userName').value
  const email = document.getElementById('userEmail').value
  if (!name || !email) return showToast('Nome e Email são obrigatórios!', 'error')

  const options = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email }),
  }
  handleRequest(API_URL, options, btnAdicionar)
})

const btnAtualizar = document.getElementById('btnAtualizar')
btnAtualizar.addEventListener('click', () => {
  const name = document.getElementById('userName').value
  const email = document.getElementById('userEmail').value
  if (!name || !email) return showToast('Nome e Email são obrigatórios!', 'error')

  const options = {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name, email }),
  }
  handleRequest(API_URL, options, btnAtualizar)
})

const btnConsultar = document.getElementById('btnConsultar')
btnConsultar.addEventListener('click', () => {
  const name = document.getElementById('queryName').value
  if (!name) return showToast('O nome é obrigatório para consulta!', 'error')
  const options = { method: 'GET' }
  handleRequest(`${API_URL}?name=${encodeURIComponent(name)}`, options, btnConsultar)
})

const btnRemover = document.getElementById('btnRemover')
btnRemover.addEventListener('click', () => {
  const name = document.getElementById('queryName').value
  if (!name) return showToast('O nome é obrigatório para remoção!', 'error')
  const options = {
    method: 'DELETE',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ name }),
  }
  handleRequest(API_URL, options, btnRemover)
})

const btnListarTodos = document.getElementById('btnListarTodos')
const userListBody = document.getElementById('user-list-body')

btnListarTodos.addEventListener('click', async () => {
  setLoading(btnListarTodos, true)
  try {
    const response = await fetch(`${API_URL}users`)
    const users = await response.json()

    userListBody.innerHTML = ''

    if (users.length === 0) {
      const row = `<tr><td colspan="3" style="text-align: center;">Nenhum usuário encontrado.</td></tr>`
      userListBody.innerHTML = row
    } else {
      users.forEach(user => {
        const row = `
                            <tr>
                                <td>${user._id}</td>
                                <td>${user.name}</td>
                                <td>${user.email}</td>
                            </tr>
                        `
        userListBody.innerHTML += row
      })
    }
    showToast('Lista de usuários atualizada!', 'success')
  } catch (error) {
    showToast('Falha ao buscar usuários.', 'error')
    userListBody.innerHTML = `<tr><td colspan="3" style="text-align: center; color: red;">Erro ao carregar dados.</td></tr>`
  } finally {
    setLoading(btnListarTodos, false)
  }
})