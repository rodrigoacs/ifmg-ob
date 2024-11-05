<template>
  <div class="product-card">
    <img
      :src="product.imageUrl"
      :alt="product.name"
      class="product-image"
    >
    <h2 class="product-name">{{ product.name }}</h2>
    <p
      class="product-old-price"
      v-if="product.originalPrice"
    >
      R$ {{ product.originalPrice?.toFixed(2) }}
    </p>
    <p class="product-price">R$ {{ product.discountedPrice?.toFixed(2) }}</p>
    <p
      class="product-discount"
      v-if="product.discountPercentage"
    >
      -{{ product.discountPercentage.toFixed(2) }}%
    </p>
  </div>
</template>

<script setup>
defineProps({
  product: {
    type: Object,
    required: true
  }
})
</script>

<style scoped>
.product-card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  text-align: center;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 16px;
  margin: 16px;
  max-width: 200px;
  background-color: #fff;
}

.product-image {
  max-width: 100%;
  height: auto;
  max-height: 200px;
  margin-bottom: 10px;
  object-fit: scale-down;
}

.product-name {
  font-size: 1rem;
  color: #914004;
  margin-bottom: 8px;
}

.product-old-price {
  text-decoration: line-through;
  color: #9e9e9e;
  font-size: 0.875rem;
}

.product-price {
  font-size: 1.25rem;
  font-weight: bold;
  color: #914004;
}

.product-discount {
  font-size: 0.875rem;
  color: #70c73e;
  background-color: #e0ffe5;
  padding: 4px 8px;
  border-radius: 4px;
  display: inline-block;
  margin-top: 8px;
}
</style>
