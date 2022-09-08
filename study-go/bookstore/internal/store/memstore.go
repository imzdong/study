package store

import (
	"bookstore/store"
	"bookstore/store/factory"
	"fmt"
	"sync"
)

func init() {
	fmt.Println("init")
	// 方法设置了* 需要使用&
	factory.InitDb("memory", &MemoryStore{
		books: make(map[string]*store.Book),
	})
}

type MemoryStore struct {
	sync.RWMutex
	//声明map的值存储store的指针类型
	books map[string]*store.Book
}

//给MemoryStore声明方法
func (m *MemoryStore) Insert(s *store.Book) error {
	m.Lock()
	//Go语言的 defer 语句会将其后面跟随的语句进行延迟处理，在 defer 归属的函数即将返回时，将延迟处理的语句按 defer 的逆序进行执行，
	//也就是说，先被 defer 的语句最后被执行，最后被 defer 的语句，最先被执行。
	//关键字 defer 的用法类似于面向对象编程语言 Java 和 C# 的 finally 语句块，它一般用于释放某些已分配的资源，
	//典型的例子就是对一个互斥解锁，或者关闭一个文件。
	defer m.Unlock()

	if _, ok := m.books[s.Id]; ok {
		return store.ErrExist
	}

	m.books[s.Id] = s
	fmt.Println(m)
	return nil
}

func (m *MemoryStore) GetById(id string) (store.Book, error) {

	if s, ok := m.books[id]; ok {
		return *s, nil
	}
	return store.Book{}, store.ErrNotFound
}
