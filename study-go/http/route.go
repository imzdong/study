package main

import "github.com/imzdong/http/framework"

func registerRouter(core *framework.Core) {
	core.Get("foo", FooControllerHandler)
}
