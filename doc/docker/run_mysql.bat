@echo off 
rem run_mysql.bat 3308 2 root 
rem 从本行开始关闭回显要执行的命令。一般批处理第一行都是这个
echo start mysql
set port=%1
set tail_num=%2
set password=%3
rem %1 port
rem %2 num 启动多个mysql 
rem set mysql_home=/D/WorkSpace/docker/local/mysql/%tail_num%

docker run -p %port%:3306 ^
--name mysql%tail_num% ^
-e MYSQL_ROOT_PASSWORD=%password% ^
-d mysql
 
 echo end

pause

rem -d # 表示在一直在后台运行容器
rem -p 8081:80 端口绑定 外部端口 ： 容器内端口# 对端口进行映射，将本地8081端口映射到容器内部的80端口
rem --name # 设置创建的容器名称
rem -v # 将本地目录(文件)挂载到容器指定目录；  -v 本地文件夹:容器里的文件夹:读写权限
rem --link answer-server:answerserver 这计划是指需要转向本机docker容器的别名
rem docker run -d --name mysql -p 3307:3306 mysql

rem docker cp mysql:/etc/mysql/my.cnf $PWD/conf

rem 远程访问
rem docker exec -it mysql1 /bin/bash

rem 解决 1251 - Client does not support authentication protocol requested by server； consider upgrading Mysql
rem #设置mysql远程权限
rem --赋予任何主机访问权限：
rem GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY 'root' WITH GRANT OPTION;
rem alter user 'root'@'localhost' IDENTIFIED with mysql_native_password by 'root'; 不太行
rem alter user 'root'@'%' identified with mysql_native_password by 'root'; 可以
rem #刷新权限
rem flush privileges;