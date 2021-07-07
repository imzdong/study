assume cs:code,ds:data,ss:stack

stack segment
    dw 0,0,0
stack ends

data segment
    db 'DEC'
    db 'Ken Olsen'
    dw 137
    dw 40
    db 'PDP'
data ends

code segment

start: mov ax,data
       mov ds,ax
       mov bx,0 ;数据基地址

       mov ax,stack
       mov ss,ax
       mov sp,6

       mov al,'X'
       push ax
       mov al,'A'
       push ax
       mov al,'V'
       push ax

       mov word ptr [bx].0ch,38
       add word ptr [bx].0eh,70

       mov si,0
       mov cx,3

s:     pop ax
       mov byte ptr [bx].10h[si],al
       inc si

       loop s

       mov ax,4c00h
       int 21h

code ends

end start