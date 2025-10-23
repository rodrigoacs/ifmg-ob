import json
from flask import Flask, request, jsonify
from pymongo import MongoClient
from flask_cors import CORS

app = Flask(__name__)
CORS(app)

client = MongoClient("mongodb://localhost:27017/")
db = client["user_database"]
collection = db["users"]

@app.route("/users", methods=["GET"])
def list_users():
    try:
        users_cursor = collection.find({})

        users_list = []
        for user in users_cursor:
            user["_id"] = str(user["_id"])
            users_list.append(user)

        return jsonify(users_list), 200
    except Exception as e:
        return jsonify({"error": str(e)}), 500


@app.route("/", methods=["GET"])
def query_records():
    name = request.args.get("name")

    record = collection.find_one({"name": name})

    if record:
        record["_id"] = str(record["_id"])
        return jsonify(record)
    else:
        return jsonify({"error": "data not found"}), 404


@app.route("/", methods=["POST"])
def create_record():
    record = request.get_json()

    if collection.find_one({"name": record["name"]}):
        return (
            jsonify({"error": f"Usuário '{record['name']}' já existe."}),
            409,
        )

    collection.insert_one(record)

    record["_id"] = str(record["_id"])
    return jsonify(record), 201


@app.route("/", methods=["PUT"])
def update_record():
    record = request.get_json()
    query = {"name": record["name"]}
    new_values = {"$set": {"email": record["email"]}}
    result = collection.update_one(query, new_values)

    if result.matched_count > 0:
        return jsonify(record)
    else:
        return jsonify({"error": "Usuário não encontrado para atualização."}), 404


@app.route("/", methods=["DELETE"])
def delete_record():
    record = request.get_json()
    result = collection.delete_one({"name": record["name"]})

    if result.deleted_count > 0:
        return jsonify(record)
    else:
        return jsonify({"error": "Usuário não encontrado para remoção."}), 404


if __name__ == "__main__":
    app.run(debug=True)
