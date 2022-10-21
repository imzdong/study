package main

import (
	"context"
	"encoding/json"
	"fmt"
	"net/http"
	"strconv"
	"time"
)

func TestContext() {
	sd := 1 * time.Millisecond
	d := time.Now().Add(sd)
	//创建一个1毫秒结束的定时器
	//定时器结束的时候，主线程会通过Done()函数收到时间结束通知，然后主动滴哦用函数句柄cancelFunc来通知所有子context结束
	//cancel话筒（通知下游），Done听筒（接到上游的通知）
	ctx, cancel := context.WithDeadline(context.Background(), d)
	//它们的本质就是“通过定时器来自动触发终结通知”，WithTimeout 设置若干秒后通知触发终结，WithDeadline 设置未来某个时间点触发终结。

	//defer 延迟执行 类似于java的finally
	defer cancel()

	//channel是可以让一个goroutine发送特定值到另一个goroutine的通信机制。
	//Go 语言中的通道（channel）是一种特殊的类型。通道像一个传送带或者队列，
	//总是遵循先入先出（First In First Out）的规则，保证收发数据的顺序
	select {
	case <-time.After(1 * time.Second):
		//通道有发送（send）、接收(receive）和关闭（close）三种操作。
		//发送和接收都使用<-符号。
		fmt.Println("overslept")
	case <-ctx.Done():
		fmt.Println("1 毫秒 Done")
		fmt.Println("执行cancel")
		fmt.Println(ctx.Err())

	}
}

func Fool(r http.Request, w http.ResponseWriter) {
	obj := map[string]interface{}{
		"data": nil,
	}

	w.Header().Set("Content-Type", "application/json")
	//请求体获取参数
	foo := r.PostFormValue("foo")
	if foo == "" {
		foo = "10"
	}
	fooInt, err := strconv.Atoi(foo)
	if err != nil {
		w.WriteHeader(500)
		return
	}

	//构建返回结构体
	obj["data"] = fooInt
	byt, err := json.Marshal(obj)
	if err != nil {
		w.WriteHeader(500)
		return
	}
	w.WriteHeader(200)
	w.Write(byt)
	return
}

/*
func Foo2(ctx *framework.Context) error {
	obj := map[string]interface{}{
		"data": nil,
	}
	//从请求体获取参数
	footInt := ctx.FormInt("foo", 10)

	obj["data"] = footInt
	return ctx.Json(http.StatusOK, obj)
}
*/
