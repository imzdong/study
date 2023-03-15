### Docker安装
docker pull adguard/adguardhome

docker run --name adguardhome\
--restart unless-stopped\
-v /my/own/workdir:/opt/adguardhome/work\
-v /my/own/confdir:/opt/adguardhome/conf\
-p 53:53/tcp -p 53:53/udp\
-p 67:67/udp -p 68:68/udp\
-p 80:80/tcp -p 443:443/tcp -p 443:443/udp -p 3000:3000/tcp\
-p 853:853/tcp\
-p 784:784/udp -p 853:853/udp -p 8853:8853/udp\
-p 5443:5443/tcp -p 5443:5443/udp\
-d adguard/adguardhome

docker run --name adguardhome ^
--restart unless-stopped ^
-v /D/WorkSpace/docker/local/guard/work:/opt/adguardhome/work ^
-v /D/WorkSpace/docker/local/guard/conf:/opt/adguardhome/conf ^
-p 53:53/tcp -p 53:53/udp ^
-p 67:67/udp -p 68:68/udp ^
-p 80:80/tcp -p 443:443/tcp -p 443:443/udp -p 3000:3000/tcp ^
-p 853:853/tcp ^
-p 784:784/udp -p 853:853/udp -p 8853:8853/udp ^
-p 5443:5443/tcp -p 5443:5443/udp ^
-d adguard/adguardhome

`53`：DNS 端口。即其他设备访问 AdGuard Home 进行 DNS 解析的默认端口。因为部分系统不支持自定义 DNS 端口，所以不建议自定义。部署前务必要查看是否有其它程序占用。
`67, 68`： DHCP 端口。除非想代替你路由上的 DHCP 服务器，否则用不到。
`80`: 管理页面默认 HTTP 端口。可忽略，在初始化页面设置管理端口为 `3000` 端口即可。
`443`：HTTPS 和 DoH 端口。本地内网环境不需要。
`853`：DoT 端口。不使用相关功能可忽略。
`3000`：初始化设置端口。除非通过配置文件去设置，否则必须开启。

[官网](https://adguard.com/kb/zh-CN/)
[网友配置参考](https://isedu.top/index.php/archives/23/)
[网友二](https://sspai.com/post/63088)

### DNS 封锁清单
halflife
https://raw.githubusercontent.com/o0HalfLife0o/list/master/ad.txt

anti-AD
https://anti-ad.net/easylist.txt

neoHosts
https://cdn.jsdelivr.net/gh/neoFelhz/neohosts@gh-pages/full/hosts.txt

大圣净化 - 针对国内视频网站
https://raw.githubusercontent.com/jdlingyu/ad-wars/master/hosts

adgk手机去广告规则
https://raw.githubusercontent.com/banbendalao/ADgk/master/ADgk.txt

广告终结者
http://sub.adtchrome.com/adt-chinalist-easylist.txt

Adbyby
https://raw.githubusercontent.com/adbyby/xwhyc-rules/master/lazy.txt

EasyList China+EasyList
https://easylist-downloads.adblockplus.org/easylistchina+easylist.txt

EasyPrivacy
https://easylist-downloads.adblockplus.org/easyprivacy.txt

### DNS允许清单

anti-ad白名单
https://raw.githubusercontent.com/privacy-protection-tools/dead-horse/master/anti-ad-white-list.txt

filter_whitelist
https://raw.githubusercontent.com/hl2guide/Filterlist-for-AdGuard/master/filter_whitelist.txt

LWJ's white list
https://raw.githubusercontent.com/liwenjie119/adg-rules/master/white.txt

DNS允许白名单
https://raw.githubusercontent.com/ChengJi-e/AFDNS/master/QD.txt