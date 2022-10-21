package main

import (
	"context"
	"fmt"
	"time"
)

func TestContext() {
	sd := 1 * time.Millisecond
	d := time.Now().Add(sd)
	//创建一个1毫秒结束的定时器
	//定时器结束的时候，主线程会通过Done()函数收到时间结束通知，然后主动滴哦用函数句柄cancelFunc来通知所有子context结束
	//cancel话筒（通知下游），Done听筒（接到上游的通知）
	ctx, cancel := context.WithDeadline(context.Background(), d)
	//延迟执行 类似于java的finally
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
