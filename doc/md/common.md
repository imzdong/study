#### linux常用网络命令
* 1、nc -l 8080 启动监听端口
* 2、tcpdump -nn -i eth0 port 8080 
> 说当tcpdump遇到协议号或端口号时，不要将这些号码转换成对应的协议名称或端口名称。比如，众所周知21端口是FTP端口，我们希望显示21，而非tcpdump自作聪明的将它显示成FTP。
> 是interface的含义，是指我们有义务告诉tcpdump希望他去监听哪一个网卡。这在我们一台服务器有多块网卡时很有必要。
* 3、nc 127.0.0.1 8080 建立socket通信
* 4、netstat -antp 查看网络连接
* 5、lsof -op 进程号  查看进程打开的文件
* 6、strace -ff -o out 命令 追踪系统调用