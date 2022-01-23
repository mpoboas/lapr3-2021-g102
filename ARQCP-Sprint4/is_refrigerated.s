.section .data
    
    # Constants
	.equ FIRST_PARAMETER_OFFSET, 8
	.equ SECOND_PARAMETER_OFFSET, 9
	.equ THIRD_PARAMETER_OFFSET, 10
    .equ FOURTH_PARAMETER_OFFSET, 11

	.equ CONTAINER_ID_OFFSET, 0 		# int containerID; - 4 bytes
	.equ CONTAINER_ISO_OFFSET, 4		# char isoCode[5]; - 5 x 1 byte
    .equ CONTAINER_POS_X_OFFSET, 9      # char x; - 1 byte
    .equ CONTAINER_POS_Y_OFFSET, 10     # char y; - 1 byte
    .equ CONTAINER_POS_Z_OFFSET, 11     # char z; - 1 byte
    .equ STRUCT_SIZE, 100

.section .text
    
        # int is_refrigerated(container* containers, char x, char y, char z);
        .global is_refrigerated

    is_refrigerated:
        # prologue
        pushq %rbp # save the original value of RBP
        movq %rsp, %rbp # copy the current stack pointer to RBP
        
        # movq %rdi, %rsi     # pointer do containers struct para %esi
        # movb %sil, %al      # 2º param para %al
        # movb %dl, %r10b      # 3º param para %r10b
        # movb %cl, %r9b      # 4º param para %r9b
        movq $2, %r10       # index
        movq $0, %r11       # counter
        
        # movb CONTAINER_POS_X_OFFSET(%rdi), %al          # x em %cl
        # movb CONTAINER_POS_Y_OFFSET(%rdi), %bl          # y em %dl
        # movb CONTAINER_POS_Z_OFFSET(%rdi), %r9b         # z em %bl
        
        jmp find_container

    find_container:
        # movl CONTAINER_POS_X_OFFSET(%esi, %edi, 1), %ecx
        # movl CONTAINER_POS_Y_OFFSET(%esi, %edi, 2), %edx
        # movl CONTAINER_POS_Z_OFFSET(%esi, %edi, 3), %ebx

        cmpb CONTAINER_POS_X_OFFSET(%rdi), %sil         # containers[i].x == %cl? 
        je goto_y
        jmp increment
    
    goto_y:
        cmpb CONTAINER_POS_Y_OFFSET(%rdi), %dl          # containers[i].y == %dl?
        je goto_z
        jmp increment

    goto_z:
        cmpb CONTAINER_POS_Z_OFFSET(%rdi), %cl         # containers[i].z == %bl?
        je check_iso
        jmp increment

    check_iso:
        # movl CONTAINER_ISO_OFFSET(%esi, %edi, 1), %ecx
        movq $0, %r9
        movb CONTAINER_ISO_OFFSET(%rdi, %r10, 1), %r9b   # %r10 = containers + CONTAINER_ISO_OFFSET + index
        # incb %r10b                                        # [2]0R5 -> 20[R]5
        # incb %r10b
        # movb $2, %r9b
        cmpb $82, %r9b                                 # compara %r10b (3º caracter do ISO code) com 'R'
        je refrigerated
        movl $0, %eax
        jmp end

    refrigerated:
        movl $1, %eax
        jmp end
    
    increment:
        # add 100
        cmpl %r8d, %r11d
        je not_found
        incq %r11
        addq $100, %rdi
        jmp find_container

    not_found:
        movl $-1, %eax
        jmp end
        
    end:
        # epilogue
        movq %rbp , % rsp # retrieve the original RSP value
        popq % rbp # restore the original RBP value
        ret