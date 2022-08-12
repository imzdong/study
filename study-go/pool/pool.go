package pool

import "fmt"

type Pool struct {
	capacity int
	length int
	tasks chan Task//队列
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
	if c > maxCapacity{
		c = maxCapacity
	}
	 p := &Pool{

	 }
	fmt.Printf("pool start\n")

	 /**
	 Go语言通过go关键字+函数/方法的方式创建一个goroutine。创建后，新goroutine将拥有独立的代码执行流，
	 并与创建它的goroutine一起被Go运行时调度。

	 go fmt.Println("I am a goroutine")
	 var c = make(chan int)
	 go func(a, b int) {    c <- a + b}(3,4)

	 // $GOROOT/src/net/http/server.goc := srv.newConn(rw)go c.serve(connCtx)

	  */
	 go p.run()

	 return p;
}

//提交任务
func Submit(task *chan){
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
	/**
	while(true){
	if(length>0)
	tasks.for
	task.run
	length--
	}
	*/
}

func close(p *Pool){
	//task.for
}