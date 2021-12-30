.section .data
    .global matrix
    .global x
    .global y
    .global z
    
.section .text
    .global is_position_filled

    is_position_filled:
        leaq matrix(%rip), %rdi # copies the address of matrix to %rdi
        movl x(%rip), %esi # copies x value to %rbx
        movl y(%rip), %ecx # copies y value to %rcx
        movl z(%rip), %edx # copies x value to %rdx

        leaq (%rdi, %rsi, 8), %rdi # address of line x
        leaq (%rdi, %rcx, 8), %rdi # address of line y
        movl (%rdi, %rdx, 4), %eax # matrix[x][y][z] value

        cmp $0, %eax # compares %eax with 0
        je is_not_filled # if %eax is equal to 0, the container position is not filled

        movb $1, %al # copies the value 1 to %al
        jmp end

    is_not_filled:
        movb $0, %al # copies the value 0 to %al
        jmp end

    end:
        ret