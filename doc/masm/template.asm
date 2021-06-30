;example.asm in DOS
.model small  ;定义程序的存储模型（small表示小型模型）
.stack        ;定义堆栈段（默认是1KB空间）
.data        ;定义数据段
……         ;数据定义（数据待填）
.code        ;定义代码段
.startup      ;程序执行起始，同时设置数据段寄存器DS指向程序的数据段
……         ;主程序（指令待填）
.exit         ;程序执行结束，返回DOS
……         ;子程序（指令待填）
end         ;汇编结束