assume cs:code,ds:data

data segment
    db 'copy me,man'
    db 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
data ends

code segment

start: mov ax,data
       mov ds,ax

       mov si,0
       mov di,11
       mov bx,0
       mov cx,6

copyL: mov ax,[si+bx]
       mov [di+bx],ax
       add bx,2

       loop copyL

       mov ax,4c00h
       int 21h

code ends

end start