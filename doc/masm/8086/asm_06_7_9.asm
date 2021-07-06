;将data中每个单词的前四个字母改成大写
assume cs:code,ds:data,ss:stack

stack segment
    dw 0,0,0,0,0,0,0,0
stack ends

data segment
    db '1. display      '
    db '2. brows        '
    db '3. replace      '
    db '4. modify       '
data ends

code segment

start: mov ax,data
       mov ds,ax
       mov ax,stack
       mov ss,ax
       mov sp,16

       mov si,0
       mov cx,4

row:   push cx
       mov cx,4
       mov bx,3

colL:     mov al,[si+bx]
        and al,11011111b
        mov [si+bx],al
        inc bx
        loop colL

       add si,16
       pop cx
       loop row

       mov ax,4c00h
       int 21h

code ends

end start