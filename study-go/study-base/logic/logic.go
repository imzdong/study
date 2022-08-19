package main

import (
	"fmt"
)

func main() {
	//baseLoop()
	//complexLoop()
	//conditionLoop()
	//complexIf()
	complexSwitch()
}

func baseLoop() {
	i := 0
	for i < 3 {
		fmt.Println(i)
		i++
	}
}

func complexLoop() {
	for i := 0; i < 3; {
		fmt.Println(i)
		i++
	}
}

func conditionLoop() {
	idx := 1
	for {
		fmt.Println(idx)
		idx++
		if idx/20 == 5 {
			continue
		}
		fmt.Println("idx > 100", idx)
		if idx > 100 {
			break
		}
	}
}

func complexIf() {
	c := 0
	// i b 循环体内可见
	if i := 0; i > 3 {
		fmt.Println(i)
	} else if b := 6; b < -1 {
		fmt.Println(i)
	} else {
		fmt.Print(i, b)
	}
	//compile error
	//fmt.Print(i, b)
	fmt.Printf("c %d", c)
}

func complexSwitch() {
	i := 1
	switch i {
	case 1:
		fmt.Println(1)
		fallthrough
		//继续执行下面的case
	case 2:
		break
		fmt.Println(2)
	case 3:
		fmt.Println(3)
	}
}
