<template>
  <div class="calendar-container">
    <FullCalendar :options="calendarOptions" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'

const userId = localStorage.getItem('userId')

const calendarOptions = ref({
  plugins: [dayGridPlugin, timeGridPlugin, interactionPlugin],
  initialView: 'timeGridWeek',
  events: [],
  editable: false,
  droppable: false,
  eventStartEditable: false,
  eventDurationEditable: false,
  selectable: true,
  dateClick: handleDateClick,
  eventClick: handleEventClick,
  weekends: false,
  allDaySlot: false,
  locale: 'pt-br',
  slotLabelFormat: {
    hour: '2-digit',
    minute: '2-digit',
    omitZeroMinute: false,
    hour12: false
  },
  eventTimeFormat: {
    hour: '2-digit',
    minute: '2-digit',
    omitZeroMinute: false,
    hour12: false
  },
  slotMinTime: '08:00:00',
  slotMaxTime: '18:00:00',
  datesSet: applyEventStyles,

})

async function fetchEvents() {
  try {
    const response = await fetch('http://195.200.2.145:5000/api/Events')
    const data = await response.json()

    if (Array.isArray(data)) {
      calendarOptions.value.events = data.map(event => ({
        id: event.id,
        title: event.userId === parseInt(userId) ? event.title : 'Não disponível', // Eventos de outros usuários são exibidos como "Não disponível"
        start: event.startTime,
        end: event.endTime,
        className: event.userId === parseInt(userId) ? 'user-event' : 'booked-event',
        extendedProps: { userId: event.userId }
      }))
    }
  } catch (error) {
    console.error('Erro ao buscar eventos:', error)
  }
}


function isTimeSlotAvailable(startTime, endTime) {
  const start = new Date(startTime)
  const end = new Date(endTime)

  return !calendarOptions.value.events.some(event => {
    const eventStart = new Date(event.start)
    const eventEnd = new Date(event.end)

    return (
      (start < eventEnd && end > eventStart)
    )
  })
}

function handleDateClick(info) {
  const startTime = info.dateStr
  const endTime = new Date(info.date)
  endTime.setMinutes(endTime.getMinutes() + 30) // Defina a duração do evento

  if (isTimeSlotAvailable(startTime, endTime.toISOString())) {
    const title = prompt('Digite o título do evento:')
    if (title) {
      const newEvent = {
        title,
        start: startTime,
        end: endTime.toISOString(),
        userId, // associar ao usuário logado
      }
      createEvent(newEvent)
    }
  } else {
    alert('O horário selecionado já está ocupado.')
  }
}

function handleEventClick(info) {
  const eventUserId = info.event.extendedProps.userId
  console.log(eventUserId, userId)

  if (eventUserId === parseInt(userId)) {
    const confirmed = confirm(`Deseja deletar o evento: ${info.event.title}?`)
    if (confirmed) {
      deleteEvent(info.event.id)
    }
  } else {
    alert('Você não tem permissão para editar este evento.')
  }
}

async function createEvent(event) {
  try {
    event.startTime = new Date(event.start).toISOString()
    event.endTime = new Date(event.end).toISOString()

    const response = await fetch('http://195.200.2.145:5000/api/Events', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(event),
    })

    const createdEvent = await response.json()

    if (response.ok) {
      calendarOptions.value.events.push({
        id: createdEvent.id,
        title: createdEvent.userId === parseInt(userId) ? createdEvent.title : 'Não disponível',
        start: createdEvent.startTime,
        end: createdEvent.endTime,
        editable: createdEvent.userId === parseInt(userId),
        className: createdEvent.userId === parseInt(userId) ? 'user-event' : 'booked-event',
        extendedProps: { userId: createdEvent.userId }
      })

      await fetchEvents()
      applyEventStyles()
    }
  } catch (error) {
    console.error('Erro ao criar evento:', error)
  }
}

async function deleteEvent(eventId) {
  try {
    const response = await fetch(`http://195.200.2.145:5000/api/Events/${eventId}`, {
      method: 'DELETE',
    })

    if (response.ok) {
      await fetchEvents()
    } else {
      console.error('Erro ao deletar evento:', await response.text())
    }
  } catch (error) {
    console.error('Erro ao deletar evento:', error)
  }
}

function applyEventStyles() {
  setTimeout(() => {
    document.querySelectorAll('.fc-event.booked-event').forEach(event => {
      event.style.backgroundColor = '#da0000'
      event.style.borderColor = '#da0000'
      event.style.cursor = 'not-allowed'
      event.style.opacity = 0.5
    })

    document.querySelectorAll('.fc-event.user-event').forEach(event => {
      event.style.backgroundColor = '#00ac00'
      event.style.borderColor = '#00ac00'
    })
  }, 100)
}

onMounted(() => {
  fetchEvents().then(() => applyEventStyles())

  watch(() => calendarOptions.value.events, (newVal, oldVal) => {
    applyEventStyles()
  })
});


</script>

<style scoped>
.calendar-container {
  max-width: 1200px;
  margin: auto;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.fc {
  height: 571px;
  margin: 50px;
}
</style>
