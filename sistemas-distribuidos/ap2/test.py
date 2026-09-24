import time

import requests

API = "http://127.0.0.1:5000"
TIMEOUT = 5

results = []


def run_test(scenario, method, path, expected, json=None, timeout=TIMEOUT, base=API):
    try:
        response = requests.request(method, base + path, json=json, timeout=timeout)
        obtained = response.status_code
        body = response.json() if response.content else None
    except requests.exceptions.Timeout:
        obtained, body = "TIMEOUT", None
    except requests.exceptions.ConnectionError:
        obtained, body = "SEM CONEXAO", None

    ok = obtained == expected
    print(
        scenario,
        "| esperado:",
        expected,
        "| obtido:",
        obtained,
        "| ok" if ok else "| ERRO",
    )
    results.append((scenario, f"{method} {path}", expected, obtained, ok))
    print("body:", body)
    return body


email = f"teste{int(time.time())}@teste.com"

user = run_test(
    "Criar usuario", "POST", "/users", 201, json={"name": "rodrigo", "email": email}
)
user_id = user["id"]
run_test("Listar usuarios", "GET", "/users", 200)
run_test("Buscar usuario", "GET", f"/users/{user_id}", 200)
run_test("Criar usuario sem email", "POST", "/users", 400, json={"name": "Joao"})
run_test(
    "Criar usuario com email repetido",
    "POST",
    "/users",
    409,
    json={"name": "maria", "email": email},
)
run_test("Buscar usuario que nao existe", "GET", "/users/9999", 404)

new_data = {"name": "rodrigo saores", "email": email}
run_test("Atualizar usuario", "PUT", f"/users/{user_id}", 200, json=new_data)
run_test("Repetir o mesmo PUT", "PUT", f"/users/{user_id}", 200, json=new_data)

task = run_test(
    "Criar tarefa",
    "POST",
    f"/users/{user_id}/tasks",
    201,
    json={"title": "Estudar REST"},
)
task_id = task["id"]
run_test("Criar tarefa sem titulo", "POST", f"/users/{user_id}/tasks", 400, json={})
run_test(
    "Criar tarefa pra usuario que nao existe",
    "POST",
    "/users/9999/tasks",
    404,
    json={"title": "X"},
)
run_test("Listar tarefas do usuario", "GET", f"/users/{user_id}/tasks", 200)
run_test("Remover usuario que tem tarefa", "DELETE", f"/users/{user_id}", 409)

run_test("Remover tarefa", "DELETE", f"/tasks/{task_id}", 204)
run_test("Remover a mesma tarefa de novo", "DELETE", f"/tasks/{task_id}", 404)
run_test("Remover usuario", "DELETE", f"/users/{user_id}", 204)

run_test("Timeout", "GET", "/slow", "TIMEOUT", timeout=2)
run_test(
    "Servidor fora do ar", "GET", "/users", "SEM CONEXAO", base="http://127.0.0.1:5999"
)

with open("results.md", "w", encoding="utf-8") as f:
    f.write("| Cenario | Requisicao | Esperado | Obtido | Passou |\n")
    f.write("|---|---|---|---|---|\n")
    for scenario, req, expected, obtained, ok in results:
        f.write(
            f"| {scenario} | {req} | {expected} | {obtained} | {'sim' if ok else 'nao'} |\n"
        )

passed = len([r for r in results if r[4]])
print(f"\n{passed}/{len(results)} passaram")
