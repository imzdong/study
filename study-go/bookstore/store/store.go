package store

import (
	"database/sql"
	"errors"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
)

var (
	ErrNotFound = errors.New("not found")
	ErrExist    = errors.New("exist")
)

func init() {
	fmt.Println("store.store.go init")
}

type Book struct {
	Id     string
	Name   string
	Author string
	Date   string
}

type Store interface {
	Insert(s *Book) error
	GetById(id string) (Book, error)
}

func GetConnection() {
	open, err := sql.Open("mysql", "root:root@(127.0.0.1:3303)/go-db")
	if err != nil {
		fmt.Printf("connection error:%v\n", err)
		return
	}
	q := "select * from store"
	query, qErr := open.Query(q)
	if qErr != nil {
		fmt.Printf("query error:%v\n", qErr)
		return
	}
	var id int
	var n, a, d string
	for query.Next() {
		err := query.Scan(&id, &n, &a, &d)
		fmt.Printf("query err%v id:%d, n:%s, a:%s, d:%s\n", err, id, n, a, d)
	}
	query.Close()
	open.Close()
}

func InsertStore() {
	fmt.Println("insert before----")
	open, err := sql.Open("mysql", "root:root@(127.0.0.1:3303)/go-db")
	r, err := open.Exec("insert into store(name, author, date)values(?, ?, ?)", "zd", "winter", "2022-09-06")
	if err != nil {
		fmt.Println("exec failed, ", err)
		return
	}
	id, err := r.LastInsertId()
	if err != nil {
		fmt.Println("exec failed, ", err)
		return
	}
	fmt.Println("insert succ:", id)
	open.Close()
	fmt.Println("insert after----")
}
