; Input:
; ESI = pointer to the string to convert
; ECX = number of digits in the string (must be > 0)
; Output:
; EAX = integer value
string_to_int:
	xor ebx,ebx    ; clear ebx
.next_digit:
	movzx eax,byte[esi]
	inc esi
	sub al,'0'    ; convert from ASCII to number
	imul ebx,10
	add ebx,eax   ; ebx = ebx*10 + eax
	loop .next_digit  ; while (--ecx)
	mov eax,ebx
	ret

; Input:
; EAX = integer value to convert
; ESI = pointer to buffer to store the string in (must have room for at least 10 bytes)
; Output:
; EAX = pointer to the first character of the generated string
int_to_string:
	add esi,9
	mov byte [esi],STRING_TERMINATOR

	mov ebx,10
.next_digit:
	xor edx,edx         ; Clear edx prior to dividing edx:eax by ebx
	div ebx             ; eax /= 10
	add dl,'0'          ; Convert the remainder to ASCII 
	dec esi             ; store characters in reverse order
	mov [esi],dl
	test eax,eax
	jnz .next_digit ; Repeat until eax==0
	mov eax,esi
	ret
section .text
	global _start
	extern _scanf
	extern _printf
_start:
	mov eax, 3
	mov ebx, 0
	mov ecx, n
	mov edx, 2
	int 80h
	lea esi, [n]
	mov ecx, 2
	call string_to_int
	mov [n], eax
	MOV eax, 1
	ADD eax, [n]
	MOV [n], eax
	MOV eax, [n]
	mov [buffer], eax
	lea esi, [buffer]
	call int_to_string
	mov ecx, eax
	mov eax, 4
	mov ebx, 1
	mov edx, 2
	int 80h

	mov eax,1
	mov ebx, 0
	int 80h
section .data
	n DW 0

	STRING_TERMINATOR equ 0
section .bss
	buffer resb 5
