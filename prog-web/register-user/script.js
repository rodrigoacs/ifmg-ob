const form = document.getElementById("form")
const date = document.getElementById("date")
const nameBtn = document.getElementById("nameBtn")
const emailBtn = document.getElementById("emailBtn")
const register = document.getElementById("register")
const clean = document.getElementById("clean")
const resultArea = document.getElementById("resultArea")

const nameForm = document.getElementById("nameForm")
const inputName = document.getElementById("inputName")
const okName = document.getElementById("okName")
const cancelName = document.getElementById("cancelName")
let nameContent = ""

nameBtn.addEventListener("click", () => {
  form.hidden = true
  nameForm.hidden = false
})

okName.addEventListener("click", () => {
  nameContent = inputName.value
  nameForm.hidden = true
  form.hidden = false
  inputName.value = ""
})

cancelName.addEventListener("click", () => {
  nameForm.hidden = true
  form.hidden = false
  inputName.value = ""
})

emailBtn.addEventListener("click", () => {
  form.hidden = true
  emailForm.hidden = false
})

const emailForm = document.getElementById("emailForm")
const inputEmail = document.getElementById("inputEmail")
const okEmail = document.getElementById("okEmail")
const cancelEmail = document.getElementById("cancelEmail")
let emailContent = ""

var emailFormat = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
okEmail.addEventListener("click", () => {
  if (inputEmail.value.match(emailFormat)) {
    emailContent = inputEmail.value
    emailForm.hidden = true
    form.hidden = false
    inputEmail.value = ""
  } else {
    alert('email invalido\nformato aceito: xyz@xyz.xyz')
  }
})

cancelEmail.addEventListener("click", () => {
  emailForm.hidden = true
  form.hidden = false
  inputEmail.value = ""
})

let i = 1
register.addEventListener("click", () => {
  if (!nameContent || !emailContent)
    return

  resultArea.value += `Usuário número: ${i}\nNome: ${nameContent}\nE-mail: ${emailContent}\n\n`
  nameContent = ""
  emailContent = ""
  i++
})

clean.addEventListener("click", () => {
  resultArea.value = ""
  i = 1
})

function loadDate() {
  const today = new Date
  const day = today.getDate()
  const month = today.getMonth() + 1
  const year = today.getFullYear()
  date.innerText += ` ${day}/${month}/${year}`
}