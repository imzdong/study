package main

import (
	"fmt"
	"time"
	"unsafe"
)

func TestEmptyStruct() {
	//空结构体的宽度是0，占用了0字节的内存空间。
	var s struct{}
	fmt.Println(unsafe.Sizeof(s))
	//由于空结构体占用0字节，那么空结构体也不需要填充字节。所以空结构体组成的组合数据类型也不会占用内存空间。
	//通过消息来共享数据是golang的一种设计哲学，channel则是这种哲理的体现。
	c := make(chan struct{})
	//c 它是一个管道chan，内部的数据类型是struct{}。
	//通常struct{}类型channel的用法是使用同步，一般不需要往channel里面写数据，只有读等待，而读等待会在channel被关闭的时候返回。
	go func() {
		c <- struct{}{}
	}()

	fmt.Println(c)
	fmt.Println(<-c)
}

func TestNoCacheChan() {
	//无缓冲区channel
	//
	//用make(chan int) 创建的chan, 是无缓冲区的, send 数据到chan 时，
	//在没有协程取出数据的情况下， 会阻塞当前协程的运行。ch <- 后面的代码就不会再运行，
	//直到channel 的数据被接收，当前协程才会继续往下执行。
	ch := make(chan int) // 创建无缓冲channel
	go func() {
		fmt.Println("time sleep 5 second...")
		time.Sleep(5 * time.Second)
		fmt.Println(<-ch)

	}()
	fmt.Println("即将阻塞...")
	ch <- 1 // 协程将会阻塞，等待数据被读取
	fmt.Println("ch 数据被消费，主协程退出")
}

func TestCacheChan() {
	//有缓冲区channel
	//channel 的缓冲区为1，向channel 发送第一个数据，主协程不会退出。
	//发送第二个时候，缓冲区已经满了， 此时阻塞主协程。。
	ch := make(chan int, 1) // 创建有缓冲channel
	go func() {
		fmt.Println("time sleep 5 second...")
		time.Sleep(5 * time.Second)
		fmt.Println(<-ch)
	}()
	ch <- 1 // 协程不会阻塞，等待数据被读取
	fmt.Println("第二次发送数据到channel， 即将阻塞")
	ch <- 1 // 第二次发送数据到channel, 在数据没有被读取之前，因为缓冲区满了， 所以会阻塞主协程。
	fmt.Println("ch 数据被消费，主协程退出")
}
