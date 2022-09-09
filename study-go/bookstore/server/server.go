package server

import (
	_ "bookstore/internal/store"
	"bookstore/store"
	"encoding/json"
	"net/http"
)

type BookStoreServer struct {
	S store.Store
}

func (bs *BookStoreServer) CreateBookHandler(w http.ResponseWriter, r *http.Request) {
	header := r.Header
	cType := header.Get("Content-Type")
	if cType == "application/json" {
		decoder := json.NewDecoder(r.Body)
		var book store.Book
		decoder.Decode(&book)
		err := bs.S.Insert(&book)
		if err != nil {
			http.Error(w, err.Error(), http.StatusBadRequest)
			return
		}
	}
}

func (bs *BookStoreServer) GetBookHandler(w http.ResponseWriter, r *http.Request) {
	query := r.URL.Query()
	id := query.Get("id")
	byId, err := bs.S.GetById(id)
	if err != nil {
		http.Error(w, err.Error(), http.StatusBadRequest)
		return
	}
	response(w, byId)
}

func response(w http.ResponseWriter, v interface{}) {
	data, err := json.Marshal(v)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}
	w.Header().Set("Content-Type", "application/json")
	w.Write(data)
}
