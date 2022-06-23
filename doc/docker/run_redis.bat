@echo off  
rem 从本行开始关闭回显要执行的命令。一般批处理第一行都是这个
echo redis start

docker run --name redis -p 6379:6379 -v /D/WorkSpace/docker/local/redis/config/redis_6379.conf:/etc/redis/redis_6379.conf -v /D/WorkSpace/docker/local/redis/data:/data/ -d redis:latest redis-server /etc/redis/redis_6379.conf --appendonly yes

echo redis end

pause