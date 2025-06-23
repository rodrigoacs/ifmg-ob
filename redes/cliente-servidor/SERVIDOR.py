import socket

host = "127.0.0.1"
port = 5000

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_address = (host, port)
server_socket.bind(server_address)
server_socket.listen(0)

print("Servidor aguardando conexao...")

while True:
    connection_socket, client_address = server_socket.accept()
    print(f"[LOG] {client_address} conectado.")

    while True:
        try:
            expression = connection_socket.recv(1024).decode("utf-8")

            if not expression or expression.lower() == "sair":
                print(f"[LOG] {client_address} desconectou.")
                break

            print(
                f"[LOG] Expressao Recebida do Cliente {client_address[0]}: {expression}"
            )

            try:
                result = str(eval(expression))
            except Exception as e:
                result = f"Erro na expressao: {e}"

            connection_socket.send(result.encode("utf-8"))

        except ConnectionResetError:
            print(f"[LOG] Conexao perdida com o cliente {client_address}")
            break

    connection_socket.close()
