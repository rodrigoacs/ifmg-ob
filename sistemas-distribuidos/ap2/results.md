| Cenario                                 | Requisicao             | Esperado    | Obtido      | Passou |
| --------------------------------------- | ---------------------- | ----------- | ----------- | ------ |
| Criar usuario                           | POST /users            | 201         | 201         | sim    |
| Listar usuarios                         | GET /users             | 200         | 200         | sim    |
| Buscar usuario                          | GET /users/5           | 200         | 200         | sim    |
| Criar usuario sem email                 | POST /users            | 400         | 400         | sim    |
| Criar usuario com email repetido        | POST /users            | 409         | 409         | sim    |
| Buscar usuario que nao existe           | GET /users/9999        | 404         | 404         | sim    |
| Atualizar usuario                       | PUT /users/5           | 200         | 200         | sim    |
| Repetir o mesmo PUT                     | PUT /users/5           | 200         | 200         | sim    |
| Criar tarefa                            | POST /users/5/tasks    | 201         | 201         | sim    |
| Criar tarefa sem titulo                 | POST /users/5/tasks    | 400         | 400         | sim    |
| Criar tarefa pra usuario que nao existe | POST /users/9999/tasks | 404         | 404         | sim    |
| Listar tarefas do usuario               | GET /users/5/tasks     | 200         | 200         | sim    |
| Remover usuario que tem tarefa          | DELETE /users/5        | 409         | 409         | sim    |
| Remover tarefa                          | DELETE /tasks/6        | 204         | 204         | sim    |
| Remover a mesma tarefa de novo          | DELETE /tasks/6        | 404         | 404         | sim    |
| Remover usuario                         | DELETE /users/5        | 204         | 204         | sim    |
| Timeout                                 | GET /slow              | TIMEOUT     | TIMEOUT     | sim    |
| Servidor fora do ar                     | GET /users             | SEM CONEXAO | SEM CONEXAO | sim    |
