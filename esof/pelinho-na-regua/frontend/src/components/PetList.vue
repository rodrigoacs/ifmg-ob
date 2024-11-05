<template>
  <div class="pet-list">
    <h2>Seus Pets</h2>
    <div class="grid-container">
      <div
        v-for="pet in pets"
        :key="pet.id"
        class="pet-item"
      >
        <div class="pet-info">
          <h3>{{ pet.name }}</h3>
          <p><strong>Espécie:</strong> {{ pet.species }}</p>
          <p><strong>Raça:</strong> {{ pet.breed }}</p>
          <p><strong>Idade:</strong> {{ pet.age }} anos</p>
          <p><strong>Últimas vacinas:</strong></p>
          <p><strong>Antirrábica:</strong> {{ pet.lastRabiesVaccine }}</p>
          <p><strong>Polivalente:</strong> {{ pet.lastPolyvalentVaccine }}</p>
        </div>
        <div class="pet-actions">
          <button
            @click="deletePet(pet.id)"
            class="button delete-button"
          >Deletar</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'

const props = defineProps({
  userId: {
    type: String,
    required: true
  }
})

const pets = ref([])

async function fetchPets() {
  try {
    const response = await fetch(`http://195.200.2.145:5000/api/Pets/user/${props.userId}`)
    const data = await response.json()
    pets.value = data
  } catch (error) {
    console.error('Erro ao buscar pets:', error)
  }
}

async function deletePet(petId) {
  try {
    const response = await fetch(`http://195.200.2.145:5000/api/Pets/${petId}`, {
      method: 'DELETE'
    })
    if (response.ok) {
      alert('Pet deletado com sucesso!')
      fetchPets()
    } else {
      alert('Erro ao deletar pet')
    }
  } catch (error) {
    console.error('Erro ao deletar pet:', error)
  }
}

onMounted(() => {
  fetchPets()
})
</script>

<style scoped>
.pet-list {
  margin-top: 20px;
}

h2 {
  color: #b5651d;
  text-align: center;
  margin-bottom: 20px;
}

.grid-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
}

.pet-item {
  background-color: #fff;
  padding: 15px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.pet-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 12px rgba(0, 0, 0, 0.2);
}

.pet-info h3 {
  color: #b5651d;
  margin-bottom: 10px;
}

.pet-info p {
  margin: 5px 0;
  color: #666;
}

.pet-actions {
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
}

.delete-button {
  background-color: #cc0000;
  color: white;
  border: none;
  padding: 8px 12px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.delete-button:hover {
  background-color: #990000;
}
</style>
