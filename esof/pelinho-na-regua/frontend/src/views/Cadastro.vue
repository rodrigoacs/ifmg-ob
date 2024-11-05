<template>
  <div class="register-container">
    <div class="content">
      <div class="register-form">
        <h2>Crie sua conta grátis</h2>
        <p>Preencha seus dados</p>
        <form @submit.prevent="handleRegister">
          <div class="input-group">
            <i class="fa fa-user"></i>
            <input
              type="text"
              placeholder="usuário"
              v-model="username"
            >
          </div>
          <div class="input-group">
            <i class="fa fa-lock"></i>
            <input
              placeholder="senha"
              v-model="password"
            >
          </div>
          <div class="input-group">
            <i class="fa fa-lock"></i>
            <input
              placeholder="confirme a senha"
              v-model="passwordCheck"
            >
          </div>
          <button type="submit">CRIAR CONTA</button>
        </form>
        <div class="footer">
          <router-link to="/login">Já tem conta? Faça login.</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const username = ref('')
const password = ref('')
const passwordCheck = ref('')
const router = useRouter()


const handleRegister = () => {
  if (password.value !== passwordCheck.value) {
    alert('Senhas não conferem')
    return
  }

  fetch('http://195.200.2.145:5000/api/Users/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      username: username.value,
      password: password.value
    })
  })
    .then(response => response.json())
    .then(data => {
      if (data.error) {
        alert(data.error)
        return
      }

      alert('Usuário criado com sucesso')
      router.push('/login')
    })
}

</script>

<style scoped>
.register-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-image: url(@/assets/background.jpg);
  background-size: contain;
  height: 100vh;
  padding: 20px;
}

.logo {
  height: 60px;
}

.content {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  flex-grow: 1;
}

.register-form {
  background-color: #D9D9D9;
  padding: 20px;
  border-radius: 10px;
  color: #914004;
  max-width: 400px;
  width: 100%;
}

.register-form h2 {
  margin: 0 0 10px;
}

.register-form p {
  margin: 0 0 20px;
}

.input-group {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.input-group i {
  margin-right: 10px;
}

.input-group input {
  width: 100%;
  padding: 10px;
  border: none;
  border-radius: 5px;
}

.register-form button {
  width: 100%;
  padding: 10px;
  background-color: #854204;
  color: #ffffff;
  border: none;
  border-radius: 5px;
}

.footer {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.footer a {
  color: #914004;
  text-decoration: none;
}
</style>
