package main

import (
	"context"
	"fmt"
	"github.com/imzdong/http/framework"
	"time"
)

/**
如何使用自定义 Context 设置超时呢？结合前面分析的标准库思路，我们三步走完成：继承 request 的 Context，
创建出一个设置超时时间的 Context；创建一个新的 Goroutine 来处理具体的业务逻辑；设计事件处理顺序，
当前 Goroutine 监听超时时间 Contex 的 Done()事件，和具体的业务处理结束事件，哪个先到就先处理哪个。
*/

func FooControllerHandler(ctx *framework.Context) error {
	//第一步生成一个超时的 Context：
	dCtx, cancelFunc := context.WithTimeout(ctx.BaseContext(), time.Duration(1*time.Second))
	//当所有事情处理完调用cancelFunc，告知dCtx后续的context结束
	defer cancelFunc()
	//第二步创建一个新的 Goroutine 来处理业务逻辑：
	finish := make(chan struct{}, 1)
	panicChan := make(chan interface{}, 1)

	go func() {
		//增加异常处理
		defer func() {
			if p := recover(); p != nil {
				panicChan <- p
			}
		}()
		//业务处理
		time.Sleep(10 * time.Second)
		ctx.Json(200, "ok")
		//新的goroutine结束通过finish通道告知父goroutine
		finish <- struct{}{}
	}()

	//我们继续写第三步监听。使用 select 关键字来监听三个事件：异常事件、结束事件、超时事件。
	select {
	//监听异常事件
	case <-panicChan:
		ctx.Json(500, "panic")
	case <-finish:
		fmt.Println("ok")
	case <-dCtx.Done():
		ctx.Json(500, "time out")
	}

	return nil
}
