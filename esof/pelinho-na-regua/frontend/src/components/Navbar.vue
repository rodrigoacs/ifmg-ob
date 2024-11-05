<template>
  <div class="navbar">
    <img
      src="@/assets/logo.png"
      alt="Logo"
      class="logo"
    >
    <router-link
      to="/"
      class="nav-link"
    >
      HOME
    </router-link>
    <router-link
      to="/agendamento"
      class="nav-link"
    >
      AGENDAMENTO
    </router-link>
    <router-link
      to="/produtos"
      class="nav-link"
    >
      PRODUTOS
    </router-link>
    <router-link
      to="/cadastro"
      class="nav-link"
    >
      CADASTRO
    </router-link>
    <router-link
      to="/login"
      class="nav-link"
    >
      LOGIN
    </router-link>
    <router-link
      to="/perfil"
      class="nav-link"
    >
      PERFIL
    </router-link>

  </div>
</template>

<script setup>
</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  padding: 10px;
}

.navbar img.logo {
  height: 60px;
}

.nav-link {
  background-color: #AA8163;
  color: #ffffff;
  text-decoration: none;
  width: 160px;
  height: 40px;
  border-radius: 15px;
  text-align: center;
  line-height: 40px;
  font-weight: 700;
}

.nav-link:hover {
  background-color: #c19a7f;
}
</style>
