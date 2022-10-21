package framework

import (
	"context"
	"net/http"
)

type Context struct {
	request        *http.Request
	responseWriter http.ResponseWriter
	ctx            context.Context
	handler        ControllerHandler
}
