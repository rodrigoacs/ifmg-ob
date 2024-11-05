import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Agendamento from '../views/Agendamento.vue'
import Produtos from '../views/Produtos.vue'
import Cadastro from '../views/Cadastro.vue'
import Login from '../views/Login.vue'
import Perfil from '../views/Perfil.vue'

const routes = [
  { path: '/', name: 'Home', component: Home },
  { path: '/agendamento', name: 'Agendamento', component: Agendamento },
  { path: '/produtos', name: 'Produtos', component: Produtos },
  { path: '/cadastro', name: 'Cadastro', component: Cadastro },
  { path: '/login', name: 'Login', component: Login },
  { path: '/perfil', name: 'Perfil', component: Perfil }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
