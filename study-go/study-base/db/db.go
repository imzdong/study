package main

import (
	"database/sql"
	"fmt"
	"time"

	_ "github.com/mattn/go-sqlite3"
)

func main() {
	db, err := sql.Open("sqlite3", "./foo.db")
	checkErr(err)
	stmt, err := db.Prepare("insert into userinfo(username, department, created) values (?,?,?)")
	checkErr(err)
	exec, err := stmt.Exec("dong", "dev", "2023-02-10")
	checkErr(err)
	id, err := exec.LastInsertId()
	checkErr(err)
	fmt.Println(id)

	rows, err := db.Query("select * from userinfo")
	checkErr(err)
	for rows.Next() {
		var uid int
		var username string
		var department string
		var created time.Time

		err := rows.Scan(&uid, &username, &department, &created)
		checkErr(err)

		fmt.Println(uid)
		fmt.Println(username)
		fmt.Println(department)
		fmt.Println(created)
	}
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
