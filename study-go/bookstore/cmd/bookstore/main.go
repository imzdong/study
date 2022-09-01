package main

import (
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

	// 处理application/json类型的POST请求
	// 根据请求body创建一个json解析器实例
	/*decoder := json.NewDecoder(r.Body)
	fmt.Println(decoder)*/
	method := r.Method
	if http.MethodGet == method {
		fmt.Println("get")
	} else if http.MethodPost == method {
		fmt.Println("post")
	} else if http.MethodDelete == method {
		fmt.Println("DELETE")
	}
	fmt.Println(r)
	fmt.Println("------------------")

	fmt.Printf("get url:%v\n", r.URL)
	fmt.Println("------------------")

	fmt.Printf("get form:%T\n", r.Form)
	fmt.Printf("get body:%T\n", r.Body)
	fmt.Printf("get PostForm:%T\n", r.PostForm)
	fmt.Println("------------------")
	fmt.Printf("get form:%v\n", r.Form)
	fmt.Printf("get body:%v\n", r.Body)
	fmt.Printf("get PostForm:%v\n", r.PostForm)

}
