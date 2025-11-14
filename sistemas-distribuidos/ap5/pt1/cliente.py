import socket
import time

client = socket.socket()

client.connect(("localhost", 5000))

client.send("Olá servidor!".encode())

print("mensagem enviada em: ", time.strftime("%H:%M:%S"))

data = client.recv(1024).decode()

print("Resposta:", data)
print("mensagem recebida em: ", time.strftime("%H:%M:%S"))

client.close()
