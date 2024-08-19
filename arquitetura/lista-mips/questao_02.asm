.data
    notas_10: .word 2                
    notas_5: .word 10                
    notas_1: .word 10                
    contador_notas: .word 0          
    msg_input: .asciiz "Digite o valor da compra: "
    mensagem_minimo_notas: .asciiz "Número mínimo de notas utilizadas: "

.text
.globl main

main:
    # solicita o valor da compra ao usuário
    li $v0, 4                   
    la $a0, msg_input           
    syscall

    li $v0, 5                   
    syscall
    move $t0, $v0

    lw $t1, notas_10
    lw $t2, notas_5
    lw $t3, notas_1

use_10:
    blez $t0, use_5            
    blez $t1, use_5            
    subi $t0, $t0, 10            
    subi $t1, $t1, 1             
    lw $t4, contador_notas
    addi $t4, $t4, 1
    sw $t4, contador_notas
    j use_10                    

use_5:
    blez $t0, use_1
    blez $t2, use_1
    subi $t0, $t0, 5
    subi $t2, $t2, 1
    lw $t4, contador_notas
    addi $t4, $t4, 1
    sw $t4, contador_notas
    j use_5         


use_1:
    blez $t0, done  
    blez $t3, done  
    subi $t0, $t0, 1
    subi $t3, $t3, 1
    lw $t4, contador_notas
    addi $t4, $t4, 1
    sw $t4, contador_notas
    j use_1                 

done:
    li $v0, 4               
    la $a0, mensagem_minimo_notas 
    syscall
    
    li $v0, 1                   
    lw $a0, contador_notas      
    syscall