assume cs:code,ds:data

data segment
    db 'HelLo'
    db 'AsmWOrld'
data ends

code segment
start:   mov ax,data
         mov ds,ax

         mov ax,0
         mov bx,0
         mov cx,5
cgSmall: mov al,[bx]
         and al,11011111b
         mov [bx],al
         inc bx

         loop cgSmall

         mov ax,0
         mov bx,5
         mov cx,8
cgBig:   mov al,[bx]
         or al,00100000b
         mov [bx],al
         inc bx

         loop cgBig

         mov ax,4c00h
         int 21h
code ends

end start