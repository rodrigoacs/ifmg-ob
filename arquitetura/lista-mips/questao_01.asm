.data
    idade_homem1: .word 0
    idade_homem2: .word 0
    idade_mulher1: .word 0
    idade_mulher2: .word 0

    mensagem_homem1: .asciiz "Digite a idade do primeiro homem: "
    mensagem_homem2: .asciiz "Digite a idade do segundo homem: "
    mensagem_mulher1: .asciiz "Digite a idade da primeira mulher: "
    mensagem_mulher2: .asciiz "Digite a idade da segunda mulher: "
    
    resultado_soma: .asciiz "Soma do homem mais velho com a mulher mais nova: "
    resultado_produto: .asciiz "Produto do homem mais novo com a mulher mais velha: "
    newline: .asciiz "\n"

.text
    .globl main

main:
    # dados primeiro homem
    li $v0, 4           
    la $a0, mensagem_homem1
    syscall

    li $v0, 5           
    syscall
    sw $v0, idade_homem1

    # dados segundo homem
    li $v0, 4
    la $a0, mensagem_homem2
    syscall

    li $v0, 5
    syscall
    sw $v0, idade_homem2

    # dados primeira mulher
    li $v0, 4
    la $a0, mensagem_mulher1
    syscall

    li $v0, 5
    syscall
    sw $v0, idade_mulher1

    # dados segunda mulher
    li $v0, 4
    la $a0, mensagem_mulher2
    syscall

    li $v0, 5
    syscall
    sw $v0, idade_mulher2

    # determinar o homem mais velho e mais novo
    lw $t0, idade_homem1
    lw $t1, idade_homem2
    bgt $t0, $t1, homem1_mais_velho

    move $t2, $t0         # $t2 = homem mais novo
    move $t3, $t1         # $t3 = homem mais velho
    j verifica_mulheres

homem1_mais_velho:
    move $t2, $t1
    move $t3, $t0

verifica_mulheres:
    lw $t0, idade_mulher1
    lw $t1, idade_mulher2
    bgt $t0, $t1, mulher1_mais_velha

    move $t4, $t0         # $t4 = mulher mais nova
    move $t5, $t1         # $t5 = mulher mais velha
    j calculos

mulher1_mais_velha:
    move $t4, $t1
    move $t5, $t0

calculos:
    # calcular a soma do homem mais velho com a mulher mais nova
    add $t6, $t3, $t4

    # calcular o produto do homem mais novo com a mulher mais velha
    mul $t7, $t2, $t5

    # exibir resultados
    li $v0, 4
    la $a0, resultado_soma
    syscall

    li $v0, 1
    move $a0, $t6
    syscall

    li $v0, 4
    la $a0, newline
    syscall

    li $v0, 4
    la $a0, resultado_produto
    syscall

    li $v0, 1
    move $a0, $t7
    syscall

    li $v0, 4
    la $a0, newline
    syscall
   