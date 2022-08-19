package main

import (
	"fmt"
	"reflect"
)

func main() {
	//testArray()
	//testArray2()
	//testSlices()
	//testSlices2()
	testPanic()
}

func testArray() {
	var a [5]int
	fmt.Print(a)
	fmt.Printf("array type: %s", reflect.TypeOf(a))

	a[3] = 100
	fmt.Println(a)

	b := [3]int{1, 2, 3}
	fmt.Println(b)

	var two [2][2]int
	fmt.Println(two)
}

func testArray2() {
	a := [3]int{1, 2}           // 未初始化元素值为 0。
	b := [...]int{1, 2, 3, 4}   // 通过初始化值确定数组长度。
	c := [5]int{2: 100, 4: 200} // 使用引号初始化元素。
	d := [...]struct {
		name string
		age  uint8
	}{
		{"user1", 10}, // 可省略元素类型。
		{"user2", 20}, // 别忘了最后一行的逗号。
	}
	fmt.Println(a, b, c, d)
}

func testSlices2() {
	//1.声明切片
	var s1 []int
	if s1 == nil {
		fmt.Println(s1)
		fmt.Println("是空")
		fmt.Println(s1[1])
	} else {
		fmt.Println("不是空")
	}
	// 2.:=
	s2 := []int{}
	// 3.make()
	var s3 []int = make([]int, 0)
	fmt.Println(s1, s2, s3)
	// 4.初始化赋值
	var s4 []int = make([]int, 0, 0)
	fmt.Println(s4)
	s5 := []int{1, 2, 3}
	fmt.Println(s5)
	// 5.从数组切片
	arr := [5]int{1, 2, 3, 4, 5}
	var s6 []int
	// 前包后不包
	s6 = arr[1:4]
	fmt.Println(s6)
}

func testSlices() {
	//1
	var s1 []int
	fmt.Println(s1)
	fmt.Printf("slice type: %s", reflect.TypeOf(s1))
	//2

	s2 := []string{"Winter"}
	fmt.Println(s2)
	//3 make
	s := make([]string, 3)
	fmt.Println(s)
	fmt.Printf("make slice type: %s", reflect.TypeOf(s))
}

func testPanic() {

	defer func() {
		if err := recover(); err != nil {
			println("before panic")
			println(err) // 将 interface{} 转型为具体类型。
			println("print error finished")
		}
	}()

	var s1 []int
	if s1 == nil {
		fmt.Println(s1)
		fmt.Println("是空")
		fmt.Println(s1[1])
	} else {
		fmt.Println("不是空")
	}

	defer func() {
		if err := recover(); err != nil {
			println("after panic")
			println(err.(string)) // 将 interface{} 转型为具体类型。
		}
	}()
	/**
	1、内置函数
	    2、假如函数F中书写了panic语句，会终止其后要执行的代码，在panic所在函数F内如果存在要执行的defer函数列表，按照defer的逆序执行
	    3、返回函数F的调用者G，在G中，调用函数F语句之后的代码不会执行，假如函数G中存在要执行的defer函数列表，按照defer的逆序执行
	    4、直到goroutine整个退出，并报告错误

	recover
	   1、内置函数
	    2、用来控制一个goroutine的panicking行为，捕获panic，从而影响应用的行为
	    3、一般的调用建议
	        a). 在defer函数中，通过recover来终止一个goroutine的panicking过程，从而恢复正常代码的执行
	        b). 可以获取通过panic传递的error

	note:
	1.利用recover处理panic指令，defer 必须放在 panic 之前定义，另外 recover 只有在 defer 调用的函数中才有效。否则当panic时，recover无法捕获到panic，无法防止panic扩散。
	    2.recover 处理异常后，逻辑并不会恢复到 panic 那个点去，函数跑到 defer 之后的那个点。
	    3.多个 defer 会形成 defer 栈，后定义的 defer 语句会被最先调用。
	*/
}
