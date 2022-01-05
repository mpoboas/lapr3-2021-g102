.section .data
	.global x               # coordenadas a serem utilizadas em is_position_filled()
    .global y
    .global z
	.global positions
		.comm positions, 8
	.global countPositions
	
.section .text
    .global totaloccupied

totaloccupied:
    movq positions(%rip), %rdi
	movl $0, %ecx
	movl $0, %edx
	# loop para correr positions
	 
my_loop: 
		# colocar a coordenada atual (positions[i]) em x, y e z
	movl (%rdi), %eax
	movl %eax, x(%rip)
	addq $4, %rdi
	movl (%rdi), %eax
	movl %eax, y(%rip)
	addq $4, %rdi
	movl (%rdi), %eax
	movl %eax, z(%rip)
	addq $4, %rdi
	incl %ecx
	jmp isOccupied

		
isOccupied:
	pushq %rcx
	pushq %rdi
	pushq %rdx
	movq $0, %rax
	call is_position_occupied
	popq %rdx
	popq %rdi
	popq %rcx
	movb $0, %ah
	cmpb $1, %al
	je incCount
	cmpl countPositions(%rip) ,%ecx
	je end
	jmp my_loop

incCount:
	incl %edx
	cmpl countPositions(%rip) ,%ecx
	je end
	jmp my_loop
	
    
end:
    movl %edx, %eax
    ret
