@echo off  
rem 从本行开始关闭回显要执行的命令。一般批处理第一行都是这个
echo start nginx

docker run -p 80:80  ^
              -p 443:443  ^
 --name nginx ^
 -v /D/WorkSpace/docker/local/nginx/html:/usr/share/nginx/html ^
 -v /D/WorkSpace/docker/local/nginx/conf/nginx.conf:/etc/nginx/nginx.conf ^
 -v /D/WorkSpace/docker/local/nginx/conf/conf.d:/etc/nginx/conf.d ^
 -v /D/WorkSpace/docker/local/nginx/logs-0623:/var/log/nginx ^
 -d nginx 
 
 echo end

pause

rem -d # 表示在一直在后台运行容器
rem -p 8081:80 端口绑定 外部端口 ： 容器内端口# 对端口进行映射，将本地8081端口映射到容器内部的80端口
rem --name # 设置创建的容器名称
rem -v # 将本地目录(文件)挂载到容器指定目录；  -v 本地文件夹:容器里的文件夹:读写权限
rem --link answer-server:answerserver 这计划是指需要转向本机docker容器的别名

rem docker run -p 8001:80 --name mynginx-v /home/embedded_310/haoyueming/2017:/usr/share/nginx/html:ro -v /home/embedded_310/haoyueming/dockerfile/default.conf:/etc/nginx/conf.d/default.conf -d --restart=always nginx

rem open() "/var/log/nginx/access.log" failed (1: Operation not permitted) 切换log目录