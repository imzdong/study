### 部署mysql
* docker run -d --name mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=doubleTiger mysql

### 部署bookstack
* docker pull linuxserver/bookstack:latest
* docker run -d \
  --name=bookstack \
  -e PUID=1000 \
  -e PGID=1000 \
  -e APP_URL=http://192.144.237.217:6875 \
  -e DB_HOST=127.0.0.1 \
  -e DB_USER=root \
  -e DB_PASS=doubleTiger \
  -e DB_DATABASE=bookstackapp \
  -p 6875:80 \
  -v /usr/local/docker/bookstack/data:/config \
  --restart unless-stopped \
  linuxserver/bookstack:latest




### 问题
* Got permission denied while trying to connect to the Docker daemon socket at unix:///var/run/docker.sock: Get “http://%2Fvar%2Frun%2Fdocker.sock/v1.24/containers/json?all=1”: dial unix /var/run/docker.sock: connect: permission denied
```
  解决方案：把用户添加到docker用户组
  sudo gpasswd -a $USER docker #将登陆用户加入到docker用户组中
  newgrp docker #更新用户组
  原因：[dong@VM-0-3-centos ~]$ ls -l /var/run/docker.sock
  srw-rw---- 1 root docker 0 Jun 24 18:06 /var/run/docker.sock
```  

* 当在终端执行sudo命令时，系统提示“xx is not in the sudoers file”：
````
其实就是没有权限进行sudo，解决方法如下：
1.切换到超级用户：$ su root
2.打开/etc/sudoers文件：$vim /etc/sudoers
3.修改文件内容：
找到“root ALL=(ALL) ALL”一行，在下面插入新的一行，内容是“xx ALL=(ALL) ALL”，然后在vim键入命令“:wq!”保存并退出。
````
  