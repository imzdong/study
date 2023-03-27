docker run -d --name chatgpt \
-e my_api_key="替换成API" \
-e USERNAME="替换成用户名" \
-e PASSWORD="替换成密码" \
-v ~/chatGPThistory:/app/history \
-p 7860:7860 \
tuchuanhuhuhu/chuanhuchatgpt:latest