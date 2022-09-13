package main

import (
	"fmt"
)

func main() {
	//testPointer()
	//testEmptyPointer()
	//testPointerPanic()
	//testNew()
	testMake()
}

func testPointer() {
	/**
	  Go语言中的函数传参都是值拷贝，当我们想要修改某个变量的时候，我们可以创建一个指向该变量地址的指针变量。
	  传递数据使用指针，而无须拷贝数据。类型指针不能进行偏移和运算。
	  Go语言中的指针操作非常简单，只需要记住两个符号：&（取地址）和*（根据地址取值）。
	  
	  & 是取地址符号 , 即取得某个变量的地址 , 如 ; &a
	  *是指针运算符 , 可以表示一个变量是指针类型 , 也可以表示一个指针变量所指向的存储单元 , 也就是这个地址所存储的值 .
	*/

	/**
	每个变量在运行时都拥有一个地址，这个地址代表变量在内存中的位置。
	Go语言中使用&字符放在变量前面对变量进行“取地址”操作。
	Go语言中的值类型（int、float、bool、string、array、struct）都有对应的指针类型，如：*int、*int64、*string等。
	*/
	a := 1
	b := &a
	c := a
	/*fmt.Println(a)
	fmt.Println(b)
	fmt.Println(*b)

	fmt.Println(reflect.TypeOf(a))
	fmt.Println(reflect.TypeOf(b))*/

	fmt.Printf("a:%d ptr:%p\n", a, &a) // a:10 ptr:0xc00001a078
	fmt.Printf("b:%p type:%T\n", b, b) // b:0xc00001a078 type:*int
	fmt.Println(&b)                    // 0xc00000e018
	fmt.Printf("c:%d\n", c)
	fmt.Printf("b:%d\n", *b)
	a = 2
	fmt.Printf("a:%d\n", a)
	fmt.Printf("b:%d\n", *b)
	fmt.Printf("c:%d\n", c)

	/**
	1.对变量进行取地址（&）操作，可以获得这个变量的指针变量。
	   2.指针变量的值是指针地址。
	   3.对指针变量进行取值（*）操作，可以获得指针变量指向的原变量的值。
	*/
}

func testEmptyPointer() {
	var p *string
	fmt.Println(p)
	fmt.Printf("p的值是%v\n", p)
	if p != nil {
		fmt.Println("非空")
	} else {
		fmt.Println("空值")
	}
}

func testPointerPanic() {
	/**
	在Go语言中对于引用类型的变量，我们在使用的时候不仅要声明它，还要为它分配内存空间，否则我们的值就没办法存储。
	而对于值类型的声明不需要分配内存空间，是因为它们在声明的时候已经默认分配好了内存空间。要分配内存，就引出来今天的new和make。
	Go语言中new和make是内建的两个函数，主要用来分配内存
	*/
	var a *int
	*a = 100
	fmt.Println(*a)

	/*var b map[string]int
	b["测试"] = 100
	fmt.Println(b)*/
}

func testNew() {
	// func new(Type) *Type
	//1.Type表示类型，new函数只接受一个参数，这个参数是一个类型
	//    2.*Type表示类型指针，new函数返回一个指向该类型内存地址的指针。
	a := new(int)
	b := new(bool)
	fmt.Printf("%T\n", a) // *int
	fmt.Printf("%T\n", b) // *bool
	fmt.Println(*a)       // 0
	fmt.Println(*b)       // false

	var c *int
	c = new(int)
	*c = 100
	fmt.Println(*c)
}

func testMake() {
	/**
	make也是用于内存分配的，区别于new，它只用于slice、map以及chan的内存创建，
	而且它返回的类型就是这三个类型本身，而不是他们的指针类型，
	因为这三种类型就是引用类型，所以就没有必要返回他们的指针了。make函数的函数签名如下：
	func make(t Type, size ...IntegerType) Type

	make函数是无可替代的，我们在使用slice、map以及channel的时候，
	都需要使用make进行初始化，然后才可以对它们进行操作。
	*/
	s := make([]string, 1)
	s = append(s, "Winter")
	fmt.Println(s)

	var b map[string]int
	b = make(map[string]int, 10)
	b["测试"] = 100
	fmt.Println(b)

}

/**
1.二者都是用来做内存分配的。
    2.make只用于slice、map以及channel的初始化，返回的还是这三个引用类型本身；
    3.而new用于类型的内存分配，并且内存对应的值为类型零值，返回的是指向类型的指针。
*/
