package factory

import (
	"bookstore/store"
)

var (
	dbs = make(map[string]store.Store)
)

func GetDb(dbName string) (store.Store, error) {
	db, ok := dbs[dbName]
	if ok {
		return db, nil
	}

	return nil, store.ErrNotFound
}

func InitDb(name string, s store.Store) {
	dbs[name] = s
}
