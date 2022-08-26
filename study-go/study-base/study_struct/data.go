package main

import (
	"fmt"
	"reflect"
)

func main() {
	//testCustomType()
	testStruct()
}

func testCustomType() {
	////将MyInt定义为int类型
	//    type MyInt int
	type myInt int

	var a1 myInt

	fmt.Println(reflect.TypeOf(a1))
	//类型别名
	//type TypeAlias = Type

	//类型定义
	type NewInt int

	//类型别名
	type MyInt = int

	var a NewInt
	var b MyInt

	fmt.Printf("type of a:%T\n", a) //type of a:main.NewInt
	fmt.Printf("type of b:%T\n", b) //type of b:int
}

func testStruct() {
	/**
	  type 类型名 struct {
	        字段名 字段类型
	        字段名 字段类型
	        …
	    }
	*/
	type test1 struct {
		name string
		age  int
	}

	//实例化 var 结构体实例 结构体类型
	xm := test1{
		name: "Winter",
		age:  13,
	}

	fmt.Println(xm)
	fmt.Println(xm.age)

	var p test1
	p.name = "xl"
	p.age = 16
	fmt.Println(p)

	var p2 struct {
		sex string
		age int
	}

	p2.sex = "man"
	p2.age = 13

	fmt.Println(p2)
}
