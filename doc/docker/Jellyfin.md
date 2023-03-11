https://hub.docker.com/r/linuxserver/jellyfin

docker pull linuxserver/jellyfin

docker run -d \
--name=jellyfin \
-e PUID=1000 \
-e PGID=1000 \
-e TZ=Etc/UTC \
-e JELLYFIN_PublishedServerUrl=192.168.0.5 `#optional` \
-p 8096:8096 \
-p 8920:8920 `#optional` \
-p 7359:7359/udp `#optional` \
-p 1900:1900/udp `#optional` \
-v /path/to/library:/config \
-v /path/to/tvseries:/data/tvshows \
-v /path/to/movies:/data/movies \
--restart unless-stopped \
lscr.io/linuxserver/jellyfin:latest



docker run -d --name=jellyfin -e PUID=1000 -e PGID=1000 -e TZ=Etc/UTC -p 8096:8096 -v /D/Downloads/BaiduNetdisk/docker/jellyfin/config:/config -v /D/Downloads/BaiduNetdisk/docker/jellyfin/tvshows:/data/tvshows -v /D/Downloads/BaiduNetdisk/docker/jellyfin/movies:/data/movies --restart unless-stopped linuxserver/jellyfin:latest
