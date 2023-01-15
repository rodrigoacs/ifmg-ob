const help = document.getElementById("help")
const email = document.getElementById("email")
const password = document.getElementById("password")
const send = document.getElementById("send")

help.addEventListener("click", () => {
    help.value = "email: email@email\nsenha: senha123"
})

function sucessLogin() {
    document.getElementById("error").hidden = true;
    document.getElementById("success").hidden = false;
}

function errorLogin() {
    document.getElementById("success").hidden = true;
    document.getElementById("error").hidden = false;
}

send.addEventListener("click", () => {
    if (email.value == "email@email" && password.value == "senha123") {
        sucessLogin()
    } else {
        errorLogin()
    }
})