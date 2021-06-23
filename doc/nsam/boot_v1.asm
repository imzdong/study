org 0x7c00

BaseOfStack         equ     0x7c00
;决定Loader程序的起始物理地址 BaseOfLoader<< 4 + OffsetOfLoader = 0x10000
BaseOfLoader        equ     0x1000
OffsetOfLoader     equ     0x00

;根目录占用的扇区数
;(BPB_RootEntCnt ＊ 32 +BPB_BytesPerSec -1) / BPB_Bytes PerSec = (224×32 + 512-1) / 512 = 14
RootDirSectors   equ     14
;保留扇区数 + FAT表扇区数 * FAT表份数 = 1 + 9 ＊ 2 =19，因为扇区编号的计数值从0开始，故根目录的起始扇区号为19
SectorNumOfRootDirStart equ     19
;FAT1表的起始扇区号
SectorNumOfFAT1Start     equ     1
;用于平衡文件（或者目录）的起始簇号与数据区起始簇号的差值
SectorBalance    equ     17

    jmp     short Label_Start
    nop
    ;制造商名称
    BS_OEMName     db     'MINEboot'
    ;每个扇区的字节
    BPB_BytesPerSec         dw     512
    ;每簇扇区数
    BPB_SecPerClus db     1
    ;保留扇区数量
    BPB_RsvdSecCnt dw     1
    ;FAT表的份数
    BPB_NumFATs    db     2
    ;根目录可容纳的目录项数
    BPB_RootEntCnt dw     224
    ;总扇区数
    BPB_TotSec16   dw     2880
    ;存储介质
    BPB_Media      db     0xf0
    ;FAT表占用的扇区数
    BPB_FATSz16    dw     9
    BPB_SecPerTrk  dw     18
    BPB_NumHeads   dw     2
    BPB_hiddSec    dd     0
    BPB_TotSec32   dd     0
    BS_DrvNum      db     0
    BS_Reserved1   db     0
    BS_BootSig     db     29h
    BS_VolID        dd     0
    ;指定卷标 磁盘名
    BS_VolLab      db     'boot loader'
    ;文件系统类型
    BS_FileSysType db     'FAT12    '

    ;=======         read one sector from floppy
    Func_ReadOneSector:

    push         bp
    mov          bp,     sp
    sub          esp,    2
    mov          byte    [bp -2],     cl
    push         bx
    mov          bl,     [BPB_SecPerTrk]
    div          bl
    inc          ah
    mov          cl,     ah
    mov          dh,     al
    shr          al,     1
    mov          ch,     al
    and          dh,     1
    pop          bx
    mov          dl,     [BS_DrvNum]

    Label_Go_On_Reading:
        mov          ah,     2
        mov          al,     byte     [bp -2]
        int          13h
        jc Label_Go_On_Reading
            add esp,     2
            pop bp
            ret