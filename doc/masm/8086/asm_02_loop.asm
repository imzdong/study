assume cs:sLoop

;loop (cx)=(cx)-1
;if cx !=0 goto loop
;if cx == 0 next
sLoop segment

	mov ax,2
	mov cx,11

	xh: add ax,ax
	loop xh

	mov ax,4c00H
	int 21H

sLoop ends

end