.section .data
    .global matrix
    .global shipCapacityX
    .global shipCapacityY
    .global shipCapacityZ

.section .text
    .global number_occupied_free

    number_occupied_free:
        pushq %rbx
        movq matrix(%rip), %rdi # places matrix in rdi
        movq $0, %rax # puts 0 in rax (number of free spaces)
        movq $0, %rsi # puts 0 in rsi (number of occupied spaces)
        movq $0, %rbx # index of x

    x_loop:
        cmpl %ebx, shipCapacityX(%rip)
        jle end
        movq (%rdi, %rbx, 8), %r8 # address of line shipCapacityX
        movq $0, %rdx

        y_loop:
            cmpl shipCapacityY(%rip), %edx
            jge endY
            movq (%r8, %rdx, 8), %r9 # address of line shipCapacityY
            movq $0, %rcx

            z_loop:
                cmpl %ecx, shipCapacityZ(%rip) # compares the index with the number of elements
                je endZ # if the index and the number of elements are equal, the function ends 
                cmpl $0, (%r9,%rcx,4) # Compares the value pointed with zero
                je freeSpace # if it is zero jump to free space
                incq %rsi # if its different, rsi is incremented by 1
                jmp next 
    
            freeSpace:
                incq %rax # rax is incremented by 1

            next:
                incq %rcx # the index is incremented by 1
                jmp z_loop # returns to the loop
            
            endZ:
                incq %rdx
                jmp y_loop # returns to the loop
                
        endY:
            incq %rbx
            jmp x_loop # returns to the loop
        
    end:
        shlq $32, %rax # moves the value of the 4 least significant bytes to the 4 most significant
        addq %rsi, %rax # adds rsi to rax
        popq %rbx
        ret
