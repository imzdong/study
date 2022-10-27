package framework

import (
	"bytes"
	"context"
	"encoding/json"
	"errors"
	"io/ioutil"
	"net/http"
	"strconv"
	"sync"
	"time"
)

type Context struct {
	request        *http.Request
	responseWriter http.ResponseWriter
	ctx            context.Context
	handler        ControllerHandler

	// 是否超时标记位
	hasTimeout bool
	// 写保护机制
	writerMux *sync.Mutex
}

func NewContext(r *http.Request, w http.ResponseWriter) *Context {
	return &Context{
		request:        r,
		responseWriter: w,
		ctx:            r.Context(),
		writerMux:      &sync.Mutex{},
	}
}

// base function
func (ctx *Context) WriterMux() *sync.Mutex {
	return ctx.writerMux
}

func (ctx *Context) GetRequest() *http.Request {
	return ctx.request
}

func (ctx *Context) GetResponse() http.ResponseWriter {
	return ctx.responseWriter
}

func (ctx *Context) SetHasTimeOut() {
	ctx.hasTimeout = true
}

func (ctx *Context) HasTimeOut() bool {
	return ctx.hasTimeout
}

// end base function

func (ctx *Context) BaseContext() context.Context {
	return ctx.request.Context()
}

// impl context.Context

func (ctx *Context) Deadline() (deadline time.Time, ok bool) {
	return ctx.BaseContext().Deadline()
}

func (ctx *Context) Done() <-chan struct{} {
	return ctx.BaseContext().Done()
}

func (ctx *Context) Err() error {
	return ctx.BaseContext().Err()
}

func (ctx *Context) Value(key interface{}) interface{} {
	return ctx.BaseContext().Value(key)
}

// end
// start query impl request

func (ctx *Context) QueryAll() map[string][]string {
	if ctx.request != nil {
		return map[string][]string(ctx.request.URL.Query())
	}
	return map[string][]string{}
}

func (ctx *Context) QueryArray(key string, def []string) []string {
	all := ctx.QueryAll()
	if vs, ok := all[key]; ok {
		return vs
	}
	return def
}

func (ctx *Context) QueryString(key string, def string) string {
	all := ctx.QueryAll()
	if vs, ok := all[key]; ok {
		if l := len(vs); l > 0 {
			return vs[l-1]
		}
	}
	return def
}

func (ctx *Context) QueryInt(key string, def int) int {
	all := ctx.QueryAll()
	if vs, ok := all[key]; ok {
		if l := len(vs); l > 0 {
			i, err := strconv.Atoi(vs[l-1])
			if err != nil {
				return def
			}
			return i
		}
	}
	return def
}

// query end

// start post

func (ctx *Context) FormAll() map[string][]string {
	if ctx.request != nil {
		return map[string][]string(ctx.request.PostForm)
	}
	return map[string][]string{}
}

func (ctx *Context) FormArray(key string, def []string) []string {
	all := ctx.FormAll()
	if vs, ok := all[key]; ok {
		return vs
	}
	return def
}

func (ctx *Context) FormString(key string, def string) string {
	all := ctx.FormAll()
	if vs, ok := all[key]; ok {
		if l := len(vs); l > 0 {
			return vs[l-1]
		}
	}
	return def
}

func (ctx *Context) FormInt(key string, def int) int {
	all := ctx.FormAll()
	if vs, ok := all[key]; ok {
		if l := len(vs); l > 0 {
			i, err := strconv.Atoi(vs[l-1])
			if err != nil {
				return def
			}
			return i
		}
	}
	return def
}

func (ctx *Context) BindJson(obj interface{}) error {
	if ctx.request == nil {
		return errors.New("cxt.request empty")
	}
	all, err := ioutil.ReadAll(ctx.request.Body)
	if err != nil {
		return err
	}
	ctx.request.Body = ioutil.NopCloser(bytes.NewBuffer(all))
	err = json.Unmarshal(all, obj)
	if err != nil {
		return err
	}
	return nil
}

func (ctx *Context) Json(status int, obj interface{}) error {
	if ctx.HasTimeOut() {
		return nil
	}
	ctx.responseWriter.Header().Set("Content-Type", "application/json")
	ctx.responseWriter.WriteHeader(status)
	marshal, err := json.Marshal(obj)
	if err != nil {
		ctx.responseWriter.WriteHeader(500)
		return err
	}
	ctx.responseWriter.Write(marshal)
	return nil
}
