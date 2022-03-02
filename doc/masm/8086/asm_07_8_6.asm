assume cs:code,ds:data

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
       mov bx,12
       mov si,3

       mov word ptr [bx].0,38
       mov ax,[bx].2
       add ax,70
       mov [bx].2,ax

       mov byte ptr [bx].[si].0,'V'
       mov byte ptr [bx].[si].1,'A'
       mov byte ptr [bx].[si].2,'X'

       mov ax,4c00h
       int 21h

code ends

end start