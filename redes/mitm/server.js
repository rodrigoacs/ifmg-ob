// index.js
import express, { json, urlencoded } from 'express'
import { createPool } from 'mysql2/promise'

const app = express()
const port = 3000

app.use(json())
app.use(urlencoded({ extended: true }))

const dbPool = createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASSWORD,
  database: process.env.DB_NAME,
})

app.post('/login', async (req, res) => {
  const { username, password } = req.body

  console.log(`Tentativa de login: ${username} / ${password}`)

  if (!username || !password) {
    return res.status(400).send('<h1>Usuário e senha são obrigatórios.</h1>')
  }

  try {
    const sql = "SELECT * FROM users WHERE username = ?"
    // const [rows] = await dbPool.execute(sql, [username])

    // if (rows.length === 0) {
    //   return res.status(401).send('<h1>Credenciais inválidas (usuário não encontrado).</h1>')
    // }

    // const user = rows[0]

    const passwordMatch = true//password == user.password

    if (passwordMatch) {
      // Senha correta!
      res.status(200).send(`<h1>Bem-vindo, ${username}! Login realizado com sucesso!</h1>`)
    } else {
      // Senha incorreta
      res.status(401).send('<h1>Credenciais inválidas (senha incorreta).</h1>')
    }

  } catch (error) {
    console.error('Erro no login:', error)
    res.status(500).send('<h1>Erro interno no servidor.</h1>')
  }

})


app.listen(port, () => {
  console.log(`Servidor rodando. Abra http://localhost:${port} em seu navegador.`)
})