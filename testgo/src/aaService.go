package main
import (
	"encoding/json"
	"io"
	"log"
	"net/http"
)
// hello world, the web server
func HelloServer(w http.ResponseWriter, req *http.Request) {
	json.Marshal()
	io.WriteString(w, selectUser())
}
func main() {
	http.HandleFunc("/hello", HelloServer)
	err := http.ListenAndServe(":9999", nil)
	if err != nil {
		log.Fatal("ListenAndServe: ", err)
	}
}