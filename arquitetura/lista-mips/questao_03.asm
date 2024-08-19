.data
prompt: .asciiz "Digite um número: "
result_yes: .asciiz "Sim\n"
result_no: .asciiz "Não\n"

.text
.globl main

main:
    li $v0, 4
    la $a0, prompt
    syscall

    li $v0, 5
    syscall
    move $t0, $v0

    li $t1, 1
    li $t2, 0

verificar_divisores:
    beq $t1, $t0, verificar_perfeito
    div $t0, $t1                     
    mfhi $t3                          
    bne $t3, $zero, proximo_divisor  

    add $t2, $t2, $t1

proximo_divisor:
    addi $t1, $t1, 1    
    j verificar_divisores

verificar_perfeito:
    beq $t0, $t2, numero_perfeito   
    j nao_perfeito

numero_perfeito:
    li $v0, 4
    la $a0, result_yes
    syscall
    j done

nao_perfeito:
    li $v0, 4
    la $a0, result_no
    syscall

done:
    li $v0, 10
    syscall
