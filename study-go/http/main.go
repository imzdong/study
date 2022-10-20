package main

import (
	"github.com/imzdong/http/framework"
	"net/http"
)

func main() {

	server := &http.Server{
		//自定义的请求核心处理函数
		Handler: framework.NewCore(),
		//请求监听地址
		Addr: ":8686",
	}

	server.ListenAndServe()

}
