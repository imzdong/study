package main

import "fmt"

func main() {
	p1 := NewPerson("测试", 25)
	p1.Dream()
	p1.SetAge(12)
	fmt.Println(p1)
	p1.SetAge2(18)
	fmt.Println(&p1)
	/**
	什么时候应该使用指针类型接收者
	1.需要修改接收者中的值
	   2.接收者是拷贝代价比较大的大对象
	   3.保证一致性，如果有某个方法使用了指针接收者，那么其他的方法也应该使用指针接收者。
	*/
}

/**
func (接收者变量 接收者类型) 方法名(参数列表) (返回参数) {
       函数体
   }

1.接收者变量：接收者中的参数变量名在命名时，官方建议使用接收者类型名的第一个小写字母，而不是self、this之类的命名。
例如，Person类型的接收者变量应该命名为 p，Connector类型的接收者变量应该命名为c等。
    2.接收者类型：接收者类型和参数类似，可以是指针类型和非指针类型。
    3.方法名、参数列表、返回参数：具体格式与函数定义相同。
*/

//Person 结构体
type Person struct {
	name string
	age  int8
}

//NewPerson 构造函数
func NewPerson(name string, age int8) *Person {
	return &Person{
		name: name,
		age:  age,
	}
}

//Dream Person做梦的方法
func (p Person) Dream() {
	fmt.Printf("%s的梦想是学好Go语言！\n", p.name)
}

// SetAge 设置p的年龄
// 使用指针接收者
func (p *Person) SetAge(newAge int8) {
	p.age = newAge
}

// SetAge2 设置p的年龄
// 使用值接收者
func (p Person) SetAge2(newAge int8) {
	p.age = newAge
	fmt.Println(&p)
}
