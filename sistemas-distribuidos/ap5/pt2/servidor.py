import socket
import time

server = socket.socket()
server.bind(('localhost', 5000))
server.listen(1)

conn, addr = server.accept()
print(f"Conectado por {addr}")
data = conn.recv(1024).decode()
print(f"Mensagem recebida: {data}")

time.sleep(10) 
conn.send("Confirmação recebida".encode())
conn.close()