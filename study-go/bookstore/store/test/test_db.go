package main

import (
	memory "bookstore/internal/store"
	"bookstore/store"
	"bookstore/store/factory"
	_ "bookstore/store/test2"
	"fmt"
)

func main() {
	s := new(memory.MemoryStore)
	fmt.Println(s)
	db, err := factory.GetDb("memory")
	fmt.Println(db)
	fmt.Println(err)
	if err == nil {
		/**
		1.二者都是用来做内存分配的。
		    2.make只用于slice、map以及channel的初始化，返回的还是这三个引用类型本身；
		    3.而new用于类型的内存分配，并且内存对应的值为类型零值，返回的是指向类型的指针。
		*/
		book := new(store.Book)
		book.Id = "1"
		book.Author = "winter"
		book.Name = "666"
		ierr := db.Insert(book)
		fmt.Println(ierr)

		id, err := db.GetById("1")
		fmt.Println(id)
		fmt.Println(err)
	}
}
