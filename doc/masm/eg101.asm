.model small
.stack
.data
msg db 'Hello World',13,10,'$'
.code
.startup
mov dx,offset msg
mov ah,9
int 21h
.exit
end