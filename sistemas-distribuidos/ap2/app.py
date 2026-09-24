import json
import os
import time

from flask import Flask, jsonify, request, send_file

app = Flask(__name__)
app.json.ensure_ascii = False

DATA_FILE = "data.json"

if os.path.exists(DATA_FILE):
    with open(DATA_FILE, encoding="utf-8") as f:
        data = json.load(f)
else:
    data = {"next_id": 1, "users": [], "tasks": []}


def save_data():
    with open(DATA_FILE, "w", encoding="utf-8") as f:
        json.dump(data, f, ensure_ascii=False, indent=2)


@app.get("/")
def index():
    return send_file("index.html")


@app.get("/data")
def get_data():
    return jsonify(data)


@app.get("/users")
def list_users():
    return jsonify(data["users"])


@app.post("/users")
def create_user():
    body = request.get_json(silent=True) or {}
    name = body.get("name")
    email = body.get("email")

    if not name or "@" not in str(email):
        return jsonify({"error": "name e email sao obrigatorios"}), 400

    if any(u["email"] == email for u in data["users"]):
        return jsonify({"error": "email ja cadastrado"}), 409

    user = {"id": data["next_id"], "name": name, "email": email}
    data["next_id"] += 1
    data["users"].append(user)
    save_data()
    return jsonify(user), 201


@app.get("/users/<int:id>")
def get_user(id):
    user = next((u for u in data["users"] if u["id"] == id), None)
    if not user:
        return jsonify({"error": "usuario nao encontrado"}), 404
    return jsonify(user)


@app.put("/users/<int:id>")
def update_user(id):
    user = next((u for u in data["users"] if u["id"] == id), None)
    if not user:
        return jsonify({"error": "usuario nao encontrado"}), 404

    body = request.get_json(silent=True) or {}
    name = body.get("name")
    email = body.get("email")

    if not name or "@" not in str(email):
        return jsonify({"error": "name e email sao obrigatorios"}), 400

    if any(u["email"] == email and u["id"] != id for u in data["users"]):
        return jsonify({"error": "email ja cadastrado"}), 409

    user["name"] = name
    user["email"] = email
    save_data()
    return jsonify(user)


@app.delete("/users/<int:id>")
def delete_user(id):
    user = next((u for u in data["users"] if u["id"] == id), None)
    if not user:
        return jsonify({"error": "usuario nao encontrado"}), 404

    if any(t["user_id"] == id for t in data["tasks"]):
        return jsonify({"error": "usuario tem tarefas, remova elas antes"}), 409

    data["users"].remove(user)
    save_data()
    return "", 204


@app.get("/users/<int:id>/tasks")
def list_tasks(id):
    if not any(u["id"] == id for u in data["users"]):
        return jsonify({"error": "usuario nao encontrado"}), 404
    return jsonify([t for t in data["tasks"] if t["user_id"] == id])


@app.post("/users/<int:id>/tasks")
def create_task(id):
    if not any(u["id"] == id for u in data["users"]):
        return jsonify({"error": "usuario nao encontrado"}), 404

    body = request.get_json(silent=True) or {}
    title = body.get("title")
    if not title:
        return jsonify({"error": "title obrigatorio"}), 400

    task = {"id": data["next_id"], "title": title, "user_id": id}
    data["next_id"] += 1
    data["tasks"].append(task)
    save_data()
    return jsonify(task), 201


@app.delete("/tasks/<int:id>")
def delete_task(id):
    task = next((t for t in data["tasks"] if t["id"] == id), None)
    if not task:
        return jsonify({"error": "tarefa nao encontrada"}), 404

    data["tasks"].remove(task)
    save_data()
    return "", 204


@app.get("/slow")
def slow():
    time.sleep(5)
    return jsonify({"ok": True})


if __name__ == "__main__":
    app.run(port=5000, threaded=False)
