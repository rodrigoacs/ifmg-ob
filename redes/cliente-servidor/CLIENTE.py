import socket

host = "127.0.0.1"
port = 5000

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_address = (host, port)
client_socket.connect(server_address)
print("Conectado ao servidor! Digite 'q' para encerrar.")

while True:
    expression = input("Digite a expressao: ")

    if expression.lower() == "sair":
        client_socket.send(expression.encode("utf-8"))
        break

    client_socket.send(expression.encode("utf-8"))

    result = client_socket.recv(1024)
    print("Resultado:", result.decode("utf-8"))

print("Conexao encerrada.")
client_socket.close()
