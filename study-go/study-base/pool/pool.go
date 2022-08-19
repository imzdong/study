package pool

import (
	"errors"
	"fmt"
	"sync"
)

var (
	ErrNoIdleWorkerInPool = errors.New("no idle worker in study-base") // workerpool中任务已满，没有空闲goroutine用于处理新任务
	ErrWorkerPoolFreed    = errors.New("workerpool freed")             // workerpool已终止运行
)

type Pool struct {
	capacity int
	length   int

	active chan struct{}
	tasks  chan Task //队列

	quit chan struct{}
	wg   sync.WaitGroup
}

type Task func()

const (
	defaultCapacity = 100
	maxCapacity     = 10000
)

func NewPool(c int) *Pool {
	if c <= 0 {
		c = defaultCapacity
	}
	if c > maxCapacity {
		c = maxCapacity
	}
	p := &Pool{
		capacity: c,
		tasks:    make(chan Task),
		quit:     make(chan struct{}),
	}
	fmt.Printf("study-base start\n")

	/**
	Go语言通过go关键字+函数/方法的方式创建一个goroutine。创建后，新goroutine将拥有独立的代码执行流，
	并与创建它的goroutine一起被Go运行时调度。

	go fmt.Println("I am a goroutine")
	var c = make(chan int)
	go func(a, b int) {    c <- a + b}(3,4)

	// $GOROOT/src/net/http/server.go
	c := srv.newConn(rw)
	go c.serve(connCtx)

	*/
	go p.run()

	return p
}

//提交任务
func (p *Pool) Submit(t Task) error {

	select {
	case <-p.quit:
		return ErrWorkerPoolFreed
	case p.tasks <- t:
		return nil
	}

	/**
	if(length >= cap){
		return
	}
	tasks.add(task)
	length++
	*/
	//tasks.add
}

/**
func (t *T或T) MethodName(参数列表) (返回值列表) {    // 方法体}
*/

func (p *Pool) run() {
	idx := 0
	for {
		select {
		case <-p.quit:
			return
		case p.active <- struct{}{}:
			idx++
			p.newWorker(idx)
		}
	}
}

func (p *Pool) newWorker(i int) {
	p.wg.Add(1)
	go func() {
		defer func() {
			if err := recover(); err != nil {
				fmt.Printf("worker[#{i}]: recover panic[#{err}] and exit\n")
				<-p.active
			}
			p.wg.Done()
		}()

		fmt.Printf("worker[%03d]: start\n", i)

		for {
			select {
			case <-p.quit:
				fmt.Printf("worker[%03d]: exit\n", i)
				<-p.active
				return
			case t := <-p.tasks:
				fmt.Printf("worker[%03d]: receive a task\n", i)
				t()
			}
		}

	}()
}

func close(p *Pool) {
	//task.for
}
