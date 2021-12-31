.section .data
    .global matrix
    .global x
    .global y
    .global z

.section .text
    .global number_occupied_free

    number_occupied_free:
        leaq matrix(%rip), %rdi # places matrix in rdi
        movq $0, %rdx
        movq $0, %rcx
        movq $0, %rsi
        movl x(%rip), %eax # place x in rax
        mull y(%rip) # multiply x by y
        mull z(%rip) # multiply xy by z
        movl %eax, %edx # place the number of elements in edx
        movq $0, %rax

    my_loop:
        cmpq %rcx, %rdx # compares the index with the number of elements
        je end # if the index and the number of elements are equal, the function ends 
        cmpl $0, (%rdi,%rcx,4) # Compares the value pointed with zero
        je freeSpace # if it is zero jump to free space
        incq %rsi # if its different, rsi is incremented by 1
        jmp next 
    
    freeSpace:
        incq %rax # rax is incremented by 1

    next:
        incq %rcx # the index is incremented by 1
        jmp my_loop # returns to the loop

    end:
        shlq $32, %rax # moves the value of the 4 least significant bytes to the 4 most significant
        addq %rsi, %rax # adds rsi to rax
        ret

