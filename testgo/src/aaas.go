package main

import (
	"database/sql"
	"fmt"
	_ "github.com/go-sql-driver/mysql"
	"log"
)

func  main(){
	selectUser()
}
//校验函数
func  checkErr(err error){
	if  err  != nil{
		log.Println(err)
	}
}

func selectUser()  {
	db,err :=sql.Open("mysql","root:@tcp(127.0.0.1:3306)/test?charset=utf8") //连接数据库
	checkErr(err)
	rows,err :=db.Query("select  id,username,name,password,salt,email,mobile  from  sys_user")
	checkErr(err)
	for rows.Next(){
		var id    int
		var username    string
		var name  string
		var  password string
		var  salt  string
		var  email   string
		var mobile string
		err = rows.Scan(&id,&username,&name,&password,&salt,&email,&mobile)
		checkErr(err)
		fmt.Println(id,username,name,password,salt,email,mobile)
	}

	defer db.Close()
}
