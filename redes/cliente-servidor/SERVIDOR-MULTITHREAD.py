import socket
import threading

host = "127.0.0.1"
port = 5000

server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server_address = (host, port)
server_socket.bind(server_address)
server_socket.listen()

print(f"[LOG] Servidor esta aguardando conexoes em {host}:{port}")


def handle_client(connection_socket, client_address):

    print(f"[LOG] {client_address} conectado.")

    while True:
        try:
            expression = connection_socket.recv(1024).decode("utf-8")

            if not expression or expression.lower() == "q":
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

        except (ConnectionResetError, ConnectionAbortedError):
            print(f"[LOG] Conexao perdida com o cliente {client_address}")
            break

    connection_socket.close()
    print(f"[LOG] Conexao com {client_address} foi fechada.")


while True:
    connection_socket, client_address = server_socket.accept()

    client_thread = threading.Thread(
        target=handle_client, args=(connection_socket, client_address)
    )
    client_thread.start()

    print(f"[LOG] Conexoes ativas: {threading.active_count() - 1}")
