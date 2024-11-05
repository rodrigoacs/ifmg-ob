<template>
  <div class="product-list">
    <ProductCard
      v-for="product in products"
      :key="product.id"
      :product="product"
    />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ProductCard from '../components/ProductCard.vue'

const products = ref([])

const fetchProducts = async () => {
  try {
    const response = await fetch('http://195.200.2.145:5000/api/Products')
    const data = await response.json()

    products.value = data
  } catch (error) {
    console.error('Erro ao carregar os produtos:', error)
  }
}

onMounted(() => {
  fetchProducts()
})
</script>

<style scoped>
.product-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
</style>
