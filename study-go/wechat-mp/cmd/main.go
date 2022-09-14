package main

import (
	"crypto/sha1"
	"encoding/hex"
	"github.com/gin-gonic/gin"
	"log"
	"sort"
	"strings"
)

const (
	//微信公众平台获取
	Token = "ZP48P12W04Bew3y829Eg0Y8y1O9Z2r91"
)

func main() {
	/**
	https://juejin.cn/post/6844904114707496973
	*/
	router := gin.Default()

	router.GET("/wx", WXCheckSignature)

	log.Fatalln(router.Run(":80"))
}

// WXCheckSignature 微信接入校验
func WXCheckSignature(c *gin.Context) {
	/**
	signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
	timestamp	时间戳
	nonce	随机数
	echostr	随机字符串
	*/
	signature := c.Query("signature")
	timestamp := c.Query("timestamp")
	nonce := c.Query("nonce")
	echostr := c.Query("echostr")

	ok := checkSignature(signature, timestamp, nonce, Token)
	if !ok {
		log.Println("微信公众号接入校验失败!")
		return
	}

	log.Println("微信公众号接入校验成功!")
	_, _ = c.Writer.WriteString(echostr)
}

func checkSignature(signature, timestamp, nonce, token string) bool {
	/**
	  对 token、timestamp、nonce 三个参数进行字典序排序；
	      将排序后的 token、timestamp、nonce 三个参数按顺序拼接成一个字符串，并对该字符串进行 sha1 加密；
	      使用加密后的字符串与 signature 参数进行比较，如果字符串值相同，则表示校验通过，将 echostr 参数原样返回即可。
	*/
	wa := []string{
		signature, timestamp, nonce,
	}
	//字典排序
	sort.Strings(wa)
	var b strings.Builder
	b.Grow(len(signature) + len(token) + len(nonce))
	for _, s := range wa {
		b.WriteString(s)
	}
	return Sha1(b.String()) == token
}

// 进行Sha1编码
func Sha1(str string) string {
	h := sha1.New()
	h.Write([]byte(str))
	return hex.EncodeToString(h.Sum(nil))
}
