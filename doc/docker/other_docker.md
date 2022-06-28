### 安装开源日记
```shell
docker run --name dailynotes -d  ^
-p 5000:5000 -v /usr/local/docker/dailynotes:/app/config ^
-e DATABASE_URI=mysql://root:doubleTiger@192.144.237.217/dailynotes?charset=utf8mb4 ^
m0ngr31/dailynotes:latest

docker run --name dailynotes -d  -p 5000:5000 -v /usr/local/docker/dailynotes:/app/config  m0ngr31/dailynotes:latest
```