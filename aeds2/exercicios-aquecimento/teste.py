import os

# Primeiro, compila o programa Java uma única vez.
os.system('javac q1.java')

for i in range(10, 110, 10):
    # Executa o programa compilado com 'java NomeDaClasse', passando 'i' como argumento.
    # Supondo que a classe principal se chame 'q1'.
    # Redireciona apenas a saída de 'time' para o arquivo, mas isso incluirá mais do que apenas o tempo de execução.
    os.system(f'/usr/bin/time -o q1.txt -a -f "%e" java q1 {i}')
    print(f'Executado para i = {i}')

print('Fim')
