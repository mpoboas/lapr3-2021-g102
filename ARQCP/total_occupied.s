.section .data
	.global x               # coordenadas a serem utilizadas em is_position_filled()
    .global y
    .global z
	.global positions
	.global countPositions
	
    
.section .text
    .global total_occupied

    total_occupied:
        movq positions(%rip), %rdi
		movl $0, %ecx
	# loop para correr positions
	 
	my_loop: 
		# colocar a coordenada atual (positions[i]) em x, y e z
		movl (%rdi), %eax
		movl %eax, x(%rip)
		addq $4, %rdi
		incl %ecx
		movl (%rdi), %eax
		movl %eax, y(%rip)
		addq $4, %rdi
		incl %ecx
		movl (%rdi), %eax
		movl %eax, z(%rip)
		addq $4, %rdi
		incl %ecx
		jmp isOccupied
		cmpl countPositions(%rip) ,%ecx
		je end
		
	isOccupied:
		pushq %rdx
		call is_position_occupied
		popq %rdx
		addl %eax, %edx
    
    end:
        movl %edx, %eax
        ret
