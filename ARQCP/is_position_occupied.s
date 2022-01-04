.section .data
    .global matrix
    .global x
    .global y
    .global z
    
.section .text
    .global is_position_occupied

    is_position_occupied:
        movq $0, %rsi # initializes %rsi to 0
        movq $0, %rcx # initializes %rcx to 0
        movq $0, %rdx # initializes %rdx to 0
        movq matrix(%rip), %rdi # copies matrix to %rdi
        movl x(%rip), %esi # copies x value to %esi
        movl y(%rip), %ecx # copies y value to %ecx
        movl z(%rip), %edx # copies x value to %edx

        movq (%rdi, %rsi, 8), %rdi # address of line x
        movq (%rdi, %rcx, 8), %rdi # address of line y
        movl (%rdi, %rdx, 4), %eax # copies matrix[x][y][z] value to %eax

        cmp $0, %eax # compares %eax with 0
        je is_not_occupied # if matrix[x][y][z] is equal to 0, the position is not occupied, jumps to the conditional block is_not_occupied

        movb $1, %al # otherwise, the position is occupied. Copies the value 1 to %al
        jmp end

    is_not_occupied:
        movb $0, %al # copies the value 0 to %al
        jmp end

    end:
        ret