<template>
  <div
    v-if="showModal"
    class="modal"
  >
    <div class="modal-content">
      <h1>Cadastre seu Pet</h1>
      <p>Preencha os dados do seu melhor amigo</p>
      <form @submit.prevent="handleSubmit">
        <div class="form-group">
          <label for="petName">*Nome</label>
          <input
            type="text"
            id="petName"
            v-model="newPet.name"
            required
          />
        </div>
        <div class="address-group">
          <div class="form-group">
            <label for="lastRabiesVaccine">Última vacina Antirrábica</label>
            <input
              type="date"
              id="lastRabiesVaccine"
              v-model="newPet.lastRabiesVaccine"
              required
            />
          </div>
          <div class="form-group">
            <label for="lastPolyvalentVaccine">Última vacina Polivalente (V4, V5, V8 ou V10)</label>
            <input
              type="date"
              id="lastPolyvalentVaccine"
              v-model="newPet.lastPolyvalentVaccine"
              required
            />
          </div>
        </div>
        <div class="address-group">
          <div class="form-group">
            <label for="age">Idade</label>
            <input
              type="number"
              id="age"
              v-model="newPet.age"
            />
          </div>
          <div class="form-group">
            <label for="species">Espécie (Gato ou Cachorro)</label>
            <select
              id="species"
              v-model="newPet.species"
            >
              <option value="Gato">Gato</option>
              <option value="Cachorro">Cachorro</option>
            </select>
          </div>
          <div class="form-group">
            <label for="breed">Raça</label>
            <input
              type="text"
              id="breed"
              v-model="newPet.breed"
            />
          </div>
        </div>
        <button
          type="submit"
          class="button"
        >CADASTRAR</button>
      </form>
      <button
        @click="emit('close')"
        class="button cancel-button"
      >Cancelar</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const props = defineProps({
  showModal: Boolean,
  userId: String,
})

const emit = defineEmits(['close', 'petAdded'])

const newPet = ref({
  name: '',
  lastRabiesVaccine: '',
  lastPolyvalentVaccine: '',
  age: '',
  species: '',
  breed: ''
})

const handleSubmit = async () => {
  try {
    const response = await fetch('http://195.200.2.145:5000/api/Pets', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        userId: props.userId,
        ...newPet.value
      })
    })

    if (response.ok) {
      alert('Pet cadastrado com sucesso!')
      emit('petAdded')  // Emitir o evento petAdded
      emit('close')     // Fechar o modal
    } else {
      alert('Erro ao cadastrar pet')
    }
  } catch (error) {
    console.error('Erro ao cadastrar pet:', error)
  }
}
</script>

<style scoped>
/* Estilos do modal (podem ser ajustados conforme necessário) */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: #f5f5f5;
  padding: 20px;
  border-radius: 10px;
  max-width: 800px;
  width: 100%;
}

h1 {
  color: #8b4513;
  text-align: center;
}

p {
  color: #8b4513;
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  color: #8b4513;
  margin-bottom: 5px;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 8px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

.address-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.address-group .form-group {
  flex: 1;
  min-width: calc(33% - 10px);
}

.button {
  background-color: #b5651d;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  width: 100%;
  margin-top: 20px;
}

.button:hover {
  background-color: #94410b;
}

.cancel-button {
  background-color: #ccc;
  color: #333;
  border: none;
  padding: 10px;
  border-radius: 5px;
  cursor: pointer;
  margin-top: 10px;
}

.cancel-button:hover {
  background-color: #aaa;
}
</style>
