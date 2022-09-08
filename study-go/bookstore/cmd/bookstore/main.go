package main

import (
	"bookstore/store"
	"encoding/json"
	"fmt"
	"net/http"
)

func main() {
	//1. 创建客户端请求，等待访问
	http.HandleFunc("/book", handleServer) //注册函数
	//http.HandleFunc("/book/{id}", handleServer) //注册函数

	err := http.ListenAndServe(":9191", nil)
	if err != nil {
		return
	}
	//2. 创建book，更新book，查询特定book，查询所有book，删除特定book
	// post /book   put /book get /book/{id} get /book delete /book/{id}

}

func handleServer(w http.ResponseWriter, r *http.Request) {
	// 处理application/x-www-form-urlencoded类型的POST请求
	// 第一种方式
	// username := request.Form["username"][0]
	// password := request.Form["password"][0]
	// 第二种方式
	//username := request.Form.Get("username")
	//password := request.Form.Get("password")

	method := r.Method
	ff := false
	if http.MethodGet == method {
		fmt.Println("get")
		//查询
	} else if http.MethodPost == method {
		fmt.Println("post")
		ff = true
		//新增
	} else if http.MethodPut == method {
		fmt.Println("put")
		ff = true
		//新增
	} else if http.MethodDelete == method {
		fmt.Println("DELETE")
		//删除
	}
	if ff {
		// 处理application/x-www-form-urlencoded类型的POST请求
		header := r.Header
		cType := header.Get("Content-Type")
		fmt.Println(cType)
		//application/x-www-form-urlencoded（表格） 和 multipart/form-data（文件）
		if cType == "application/x-www-form-urlencoded" {
			r.ParseForm()
			name := r.Form.Get("name")
			author := r.Form.Get("author")
			date := r.Form.Get("date")

			s := newStore(name, author, date)
			fmt.Println(s)
			fmt.Println(*s)
		} else if cType == "application/json" {
			// 处理application/json类型的POST请求
			// 根据请求body创建一个json解析器实例
			decoder := json.NewDecoder(r.Body)
			// 用于存放参数key=value数据
			var params map[string]string

			// 解析参数 存入map
			decoder.Decode(&params)
			fmt.Println(params)
		}
	}
	//get&post 参数在url
	query := r.URL.Query()
	fmt.Println(query.Get("name"))
	fmt.Println(query.Get("author"))
	fmt.Println(query.Get("date"))

}

func newStore(n string, a string, d string) *store.Book {
	s := store.Book{Name: n, Author: a, Date: d}
	return &s
}
