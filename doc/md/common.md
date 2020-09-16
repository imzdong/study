#### linux常用网络命令
* 1、nc -l 8080 启动监听端口
* 2、tcpdump -nn -i eth0 port 8080
* 3、nc 127.0.0.1 8080 建立socket通信
* 4、netstat -antp 查看网络连接
* 5、lsof -op 进程号  查看进程打开的文件