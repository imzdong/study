package main

import (
	"github.com/imzdong/http/framework"
	"net/http"
)

func main() {

	core := framework.NewCore()
	registerRouter(core)

	server := &http.Server{
		//自定义的请求核心处理函数
		Handler: core,
		//请求监听地址
		Addr: ":8686",
	}

	server.ListenAndServe()

}
