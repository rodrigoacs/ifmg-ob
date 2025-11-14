import socket
import hashlib

client = socket.socket()
client.connect(("localhost", 5000))

msg = "Olá servidor!"
SECRET = "segredo_errado"

token = hashlib.sha256(SECRET.encode()).hexdigest()

print(f"Enviando msg: '{msg}'")
print(f"Enviando token: {token[:10]}...")

client.send(f"{msg}|{token}".encode())

data = client.recv(1024).decode()
print("Resposta do servidor:", data)
client.close()
