IFMG Campus Ouro Branco - Sistemas de Informação - Redes de Computadores 

Rodrigo Augusto Correa Soares


# Análise de Conexões SSH com Wireshark

## Resumo

Este relatório apresenta uma análise de conexões SSH utilizando a ferramenta Wireshark. O estudo foca na identificação e análise dos principais aspectos da comunicação SSH, incluindo estabelecimento de conexão, negociação de criptografia e padrões de tráfego. A análise foi realizada através da captura e análise de pacotes de rede.

## 1. Introdução

O Wireshark é uma ferramenta para análise de tráfego de rede, permitindo capturar e analisar pacotes em tempo real. Neste relatório, será analisada uma conexão SSH, um protocolo criptografado amplamente utilizado para acesso remoto seguro.

O SSH (Secure Shell) é um protocolo de rede que permite comunicação segura entre dois sistemas. Ele utiliza criptografia para proteger os dados transmitidos, sendo amplamente utilizado para acesso remoto a servidores e transferência segura de arquivos.

## 2. Metodologia

Para realizar esta análise, foram seguidos os seguintes passos:

1. Captura de tráfego SSH utilizando o Wireshark
2. Filtragem de pacotes para mostrar apenas o tráfego SSH
3. Análise detalhada dos pacotes capturados

## 3. Análise da Captura

### 3.1 Estabelecimento da Conexão

O processo de conexão SSH inicia com um handshake TCP, seguido pela negociação SSH. Podemos observar:

#### 3.1.1 Handshake TCP

- SYN: Pacote inicial de sincronização
- SYN-ACK: Confirmação de sincronização
- ACK: Confirmação final do estabelecimento

![Handshake TCP](handshake_tcp.png)

*Figura 1: Processo de handshake TCP em três etapas (three-way handshake)*

#### 3.1.2 Negociação SSH

O processo de negociação SSH envolve:

- Troca de versões do protocolo
- Negociação de algoritmos de criptografia
- Estabelecimento de chaves de sessão
- Autenticação do usuário

![Negociação SSH](negociacao_ssh.png)

*Figura 2: Processo de negociação SSH mostrando troca de chaves e estabelecimento da conexão segura*

#### 3.1.3 Algoritmos de Criptografia Utilizados

A análise revelou os seguintes algoritmos de criptografia negociados para a conexão:

##### Algoritmos de Troca de Chaves (KEX)
*Algoritmos responsáveis pela negociação e geração das chaves de sessão entre cliente e servidor.*
- Principal: `sntrup761x25519-sha512@openssh.com`
- Alternativos: `curve25519-sha256`, `ecdh-sha2-nistp256/384/521`

##### Algoritmos de Chave do Host
*Algoritmos utilizados para autenticar a identidade do servidor e garantir a integridade da conexão.*
- `rsa-sha2-512`
- `rsa-sha2-256`
- `ecdsa-sha2-nistp256`
- `ssh-ed25519`

##### Algoritmos de Criptografia
*Algoritmos responsáveis pela criptografia dos dados transmitidos durante a sessão.*
- Principal: `chacha20-poly1305@openssh.com`
- Alternativos: `aes128/192/256-ctr`, `aes128/256-gcm@openssh.com`

##### Algoritmos MAC
*Message Authentication Code - Algoritmos que garantem a integridade e autenticidade das mensagens.*
- Principal: `umac-64-etm@openssh.com`
- Alternativos: `hmac-sha2-256/512-etm@openssh.com`


![Criptografia Utilizada](criptografia_utilizada.png)

*Figura 3: Algoritmos de criptografia negociados durante o estabelecimento da conexão SSH*

### 3.2 Padrões de Comunicação

- O cliente e servidor mantêm uma comunicação bidirecional constante
- Existem pacotes de keep-alive regulares (aproximadamente a cada segundo)
- Os pacotes de keep-alive têm tamanho consistente (102-110 bytes)

![Pacotes Keep-alive](keep_alive.png)

*Figura 4: Padrão de pacotes keep-alive mostrando comunicação bidirecional regular entre cliente e servidor*

## 4. Conclusão

A análise de tráfego SSH com o Wireshark demonstra o uso da criptografia na segurança das comunicações. Foi possível observar os padrões de comunicação e verificar a integridade da conexão.

## 5. Referências

- WIRESHARK. **Documentação Oficial**. Disponível em: <https://www.wireshark.org/docs/>. Acesso em: 29 abr. 2025.

- INTERNET ENGINEERING TASK FORCE (IETF). **RFC 4253 - The Secure Shell (SSH) Transport Layer Protocol**. 2006. Disponível em: <https://tools.ietf.org/html/rfc4253>. Acesso em: 29 abr. 2025.

- WIRESHARK. **SSH Protocol Reference**. Disponível em: <https://www.wireshark.org/docs/dfref/s/ssh.html>. Acesso em: 29 abr. 2025.

- DIGITAL OCEAN. **Understanding the SSH Encryption and Connection Process**. Disponível em: <https://www.digitalocean.com/community/tutorials/understanding-the-ssh-encryption-and-connection-process>. Acesso em: 29 abr. 2025.


- TELEPORT. **Comparing SSH Keys: RSA, DSA, ECDSA, or EdDSA?**. Disponível em: <https://goteleport.com/blog/comparing-ssh-keys/>. Acesso em: 29 abr. 2025.

