#### 目的
* 构建常量池，重复利用资源，提高效率。
#### 思路
* 构建和销毁常量池
* 添加提交任务
* 任务调度

#### 实现
* 常量池结构体pool
* pool支持任务提交
* pool支持任务调度
* pool支持任务销毁

#### 伪代码
```shell
type Pool struct {
	capacity int
	length int
	//tasks chan //队列
}

func NewPool(c int) Pool {
	pool := Pool{
		c
	}
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

func run(p *Pool) {
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
```