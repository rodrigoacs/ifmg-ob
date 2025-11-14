import socket
import hashlib

SECRET = "segredo_comp"
TOKEN_ESPERADO = hashlib.sha256(SECRET.encode()).hexdigest()

server = socket.socket()
server.bind(("localhost", 5000))
server.listen(1)

conn, addr = server.accept()
print(f"Conectado por {addr}")

try:
    data_raw = conn.recv(1024).decode()

    if "|" not in data_raw:
        raise ValueError("Formato de mensagem inválido.")

    msg, token_recebido = data_raw.split("|")
    print(f"Mensagem recebida: '{msg}'")
    print(f"Token recebido: {token_recebido[:10]}...")
    print(f"Token esperado: {TOKEN_ESPERADO[:10]}...")

    if token_recebido == TOKEN_ESPERADO:
        print("Status: Autenticação BEM-SUCEDIDA.")
        conn.send("Autenticação bem-sucedida!".encode())
    else:
        print("Status: FALHA na autenticação. Token incorreto.")
        conn.send("Acesso negado.".encode())

except (ValueError, Exception) as e:
    print(f"Erro: {e}")
    conn.send("Erro no processamento.".encode())
finally:
    conn.close()
