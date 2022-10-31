package framework

import (
	"log"
	"net/http"
)

//框架核心结构

type Core struct {
	router map[string]ControllerHandler
}

//初始化框架核心结构

func NewCore() *Core {
	return &Core{router: map[string]ControllerHandler{}}
}

func (c *Core) Get(url string, handler ControllerHandler) {
	c.router[url] = handler
}

//框架核心结构实现Handler接口
func (c *Core) ServeHTTP(w http.ResponseWriter, r *http.Request) {
	log.Println("core.serveHTTP")
	ctx := NewContext(r, w)
	//简单的路由选择器
	router := c.router["foo"]
	if router == nil {
		return
	}
	log.Println("core.router")
	router(ctx)
}
