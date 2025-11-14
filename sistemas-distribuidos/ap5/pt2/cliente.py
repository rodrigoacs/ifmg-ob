import socket
import time

client = socket.socket()
client.settimeout(3)

conectado = False
MAX_TENTATIVAS = 3

for i in range(MAX_TENTATIVAS):
    try:
        print(f"Tentando conectar... ({i+1}/{MAX_TENTATIVAS})")
        client.connect(("localhost", 5000))
        conectado = True
        print("Conectado!")
        break
    except (socket.timeout, socket.error) as e:
        print(f"Falha na conexão: {e}")
        time.sleep(2)

if not conectado:
    print("Não foi possível conectar ao servidor. Abortando.")
    client.close()

else:
    try:
        client.send("Olá servidor!".encode())
        print("Mensagem enviada. Aguardando resposta...")

        data = client.recv(1024).decode()
        print("Resposta do servidor:", data)

    except socket.timeout:
        print("Timeout: O servidor não respondeu a tempo.")
    except socket.error as e:
        print(f"Erro de socket durante a comunicação: {e}")
    finally:
        client.close()
        print("Conexão fechada.")
