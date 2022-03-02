;设置程序起始地址；未设置，编译器会把地址0x0000作为程序的起始地址
org 0x7c00
;表示符号
BaseOfStack equ 0x7c00

Label_Start:
    ;将CS寄存器的段基地址设置到DS、ES、SS等寄存器中
    mov ax, cs
    mov ds, ax
    mov es, ax
    mov ss, ax
    ;设置栈基址
    mov sp, BaseOfStack

    ;======clear screen
    ;INT 10h, AH=06h功能：按指定范围滚动窗口。
    ;AL=滚动的列数，若为0则实现清空屏幕功能；
    ;BH=滚动后空出位置放入的属性 CH=滚动范围的左上角坐标列号 CL=滚动范围的左上角坐标行号
    ;DH=滚动范围的右下角坐标列号 DL=滚动范围的右下角坐标行号 BH=颜色属性。
    mov ax, 0600h
    mov bx, 0700h
    mov cx, 0
    mov dx, 0184fh
    ;INT 10h中断服务程序要求在调用时，必须向AH寄存器传入服务程序的主功能编号，再向其他寄存器传入参数
    int 10h

    ;set focus
    ;将屏幕的光标位置设置在屏幕的左上角(0,0)处
    ;DH=游标的列数 DL=游标的行数 BH=页码
    mov ax, 0200h
    mov bx, 0000h
    mov dx, 0000h
    int 10h

    ;INT 10h, AH=13h功能：显示一行字符串
    ;display on screen:Start Booting...
    mov ax, 1301h
    mov bx, 000fh
    mov dx, 0000h
    mov cx, 10
    push ax
    mov ax, ds
    mov es, ax
    pop ax
    mov bp, StartBootMessage
    int 10h

    ;INT 13h, AH=00h功能：重置磁盘驱动器，为下一次读写软盘做准备。
    ;rest floppy
    xor ah, ah
    xor dl, dl
    int 13h

    jmp $

    StartBootMessage: db "Hello Dong"
    ;fill zero until whole sector
    ;将当前行被编译后的地址（机器码地址）减去本节（Section）程序的起始地址
    times 510-($ - $$) db 0
    dw 0xaa55