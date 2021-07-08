assume cs:codesg
codesg segment
        mov ax,4c00h
        int 21h
start:  mov ax,0
    s:  nop
        nop

        mov di,offset s
        mov si,offset s2
        mov ax,cs:[si]
        mov cs:[di],ax

    s0: jmp short s

    s1: mov ax,0
        int 21h
        mov ax,0

    s2: jmp short s1
        nop
codesg ends
end start
MacPro:code admin$ cat asm01.asm

assume cs:codesg
codesg segment
        mov ax,4c00h
        int 21h
start:  mov ax,0
    s:  nop
        nop

        mov di,offset s
        mov si,offset s2
        mov ax,cs:[si]
        mov cs:[di],ax

    s0: jmp short s

    s1: mov ax,0
        int 21h
        mov ax,0

    s2: jmp short s1
        nop
codesg ends
end start


; 根据我们之前的分析, 指令是用相对偏移来表示的
    ; 因此执行的操作并不是真的跳转到 s1 这个标号,
    ; 而是跳转编译时确定的 该指令到 s1 标号的偏移量。
    ; 所以我们要分析接下来程序的流程的话 , 就必须先编译程序 ,
    ; 通过查看这条指令的机器代码，才知道偏移量是多少。
    ; 然后再根据这个偏移量确定程序下一步应该执行哪里的指令。
    ; 根据下图的编译结果 , 可以发现 ,
    ; jmp short s1 在编译后得到的指令是 : EB F6
    ; 由上可知，偏移量是 :F6
    ; 偏移量是由 补码 来表示的，由书中 附注二 ，
    ; 我们可以算出 F6对应的有符号十进制数为 -10。
    ; 从这里，我们可以知道，这条指令是将 ip 的值加上 -10。
    ; 那么，我们再看看 ip - 10 指向的地址是哪里呢 ?
    ; 由下图的编译结果，我们可以知道，
    ; 它指向的刚好就是 code segment 开始的位置.