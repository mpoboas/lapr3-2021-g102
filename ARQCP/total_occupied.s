.section .data
    # .global matrix
    .global pos_matrix      # matriz com as posições
    .global x               # coordenadas a serem utilizadas em is_position_filled()
    .global y
    .global z
    
.section .text
    .global total_occupied

    total_occupied:
        movq pos_matrix(%rip), %rdi # copies the address of matrix to %rdi
        movl x(%rip), %esi          # copies x value to %rbx
        movl y(%rip), %ecx          # copies y value to %rcx
        movl z(%rip), %edx          # copies x value to %rdx

    # loop para correr pos_matrix
    my_loop: 
    # colocar a coordenada atual (pos_matrix[i]) em x, y e z
    call is_position_filled # chamar is_position_filled
    # ex: if (%rax == 1) contador++; else i++;
    
    end:
        # retornar contador
        ret