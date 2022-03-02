assume cs:code
data segment
    db 'Welcome to masm!',0
data ends
stack segment
    dw 16 dup (0)
stack ends

code segment
;8行3列 显示字符
start: mov dh,8
       mov dl,3
       mov cl,2
       mov ax,data
       mov ds,ax
       mov si,0

       mov ax,stack
       mov ss,ax
       mov sp,16

       call show_str

       mov ax,4c00h
       int 21h
       ;显存地址区间：B8000H~BFFFFH(32K),
show_str:
;保存子程序使用到的寄存器
          push cx
          push dx
          ;显存段地址
          mov ax,b8000
          mov es,ax
          mov bx,0
          ;计算偏移地址
          ;行 160个字节
          mov al,160
          mul dh
          mov bx,al
          ;列 2个字节
          mov al,2
          mul dl
          ;偏移量=行+列
          add bx,al

          mov si,0
;真正展示
show:     mov dl,[si]
          mov cl,dl
          jxcz ok
          mov es:[bx+si],cl
          mov es:[bx+si+2],dl
          add si,2
          jmp short show


ok:       pop dx
          pop cx
          ret


code ends

end start