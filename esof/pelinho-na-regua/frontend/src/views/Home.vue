<template>
  <div class="home-container">
    <h1>Bem-vindo ao nosso PetShop online!</h1>
    <p>Aqui você tem um parceiro nos cuidados e bem-estar dos seus animais de estimação</p>

    <section class="about-section">
      <h1>Quem Somos</h1>
      <p>
        Somos apaixonados por animais e comprometidos em oferecer tudo o que seu pet precisa para uma vida saudável e
        feliz. Nossa loja é dedicada a fornecer produtos de alta qualidade para o pelo do seu animal, acessórios e
        serviços especializados, sempre com um atendimento excepcional.
      </p>
    </section>

    <section class="mission-section">
      <h1>Nossa Missão</h1>
      <p>
        Nossa missão é proporcionar aos nossos clientes e seus animais de estimação uma experiência de compra
        excepcional, oferecendo produtos e serviços que promovem a saúde, o bem-estar e a felicidade dos pets. Queremos
        ser a primeira escolha dos donos de animais quando se trata de cuidar de seus companheiros.
      </p>
    </section>

    <section class="vision-section">
      <h1>Nossa Visão</h1>
      <p>
        Ser reconhecido como o melhor e mais confiável pet shop online, inovando continuamente para atender às
        necessidades dos nossos clientes e contribuindo para o bem-estar dos animais de estimação em todo o país.
      </p>
    </section>

    <section class="values-section">
      <h1>Nossos Valores</h1>
      <ul>
        <li><strong>Amor pelos Animais:</strong> Nosso compromisso é tratar cada pet com o carinho e respeito que eles
          merecem.</li>
        <li><strong>Qualidade:</strong> Selecionamos rigorosamente os produtos que oferecemos para garantir a melhor
          experiência para você e seu animal.</li>
        <li><strong>Inovação:</strong> Buscamos constantemente novas soluções e produtos para melhorar o bem-estar dos
          pets.</li>
        <li><strong>Sustentabilidade:</strong> Priorizamos práticas e produtos que respeitam o meio ambiente, garantindo
          um futuro melhor para todos.</li>
      </ul>
    </section>

    <section class="services-section">
      <h2>ATENDEMOS</h2>
      <div class="services-images">
        <div class="service">
          <p>Gatos de todas as raças e pelagens</p>
          <img
            src="@/assets/cats.png"
            alt="Gatos de todas as raças e pelagens"
          >
        </div>
        <div class="service">
          <p>Cães de todas as raças e pelagens (até 30kg)</p>
          <img
            src="@/assets/dogs.png"
            alt="Cães de todas as raças e pelagens (até 30kg)"
          >
        </div>
      </div>
    </section>

    <section class="call-to-action">
      <div>
        <div>
          <h1>Quer saber mais sobre nossos produtos e serviços?</h1>
          <router-link
            to="/produtos"
            class="cta-link"
          >
            Conheça nossos produtos
          </router-link>
        </div>

        <div>
          <h1>Quer criar uma conta e ficar por dentro de tudo?</h1>
          <router-link
            to="/cadastro"
            class="cta-link"
          >
            Crie sua conta
          </router-link>
        </div>
      </div>

      <div>
        <div>
          <h1>Quer agendar um horário para o seu pet?</h1>
          <router-link
            to="/agendamento"
            class="cta-link"
          >
            Agende agora
          </router-link>
        </div>

        <div>
          <h1>Já tem cadastro? Faça login</h1>
          <router-link
            to="/login"
            class="cta-link"
          >
            Faça login
          </router-link>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
</script>

<style scoped>
.home-container {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  max-width: 800px;
  margin: auto;
  padding: 20px;
  background-color: #f5f5f5;
  border-radius: 10px;
  text-align: justify;
}

h1 {
  color: #b5651d;
}

p {
  color: #7d7d7d;
}

section {
  margin-bottom: 20px;
}

h2 {
  color: #ffffff;
  margin-bottom: 10px;
}

ul {
  text-align: left;
  list-style-type: none;
  padding: 0;
}

ul li {
  margin-bottom: 10px;
  text-align: justify;
}

ul li strong {
  color: #b5651d;
}

.services-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #b5651d;
  color: white;
  padding: 20px;
  border-radius: 10px;
}

.services-images {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: flex-start;
  margin-top: 20px;
}

.service {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.service img {
  max-width: 300px;
  height: 200px;
  object-fit: contain;
}

.service p {
  margin-top: 10px;
  color: white;
}

.call-to-action {
  display: flex;
  flex-direction: row;
  gap: 40px;
  background-color: #f5f5f5;
  border-radius: 10px;
  text-align: center;
}

.call-to-action div {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 20px;
  width: 100%;
}

.cta-link {
  display: block;
  margin: 10px 0;
  padding: 10px;
  background-color: #b5651d;
  color: white;
  text-decoration: none;
  border-radius: 5px;
}

.cta-link:hover {
  background-color: #94410b;
}

@media screen and (min-width: 768px) {
  .services-images {
    flex-direction: row;
  }

  .service {
    margin: 0 10px;
  }
}
</style>
