package main

import "fmt"
import "time"
import "net/http"
import "io/ioutil"
import "encoding/json"

type PeerList []string

func api_request(domain string) []string {

  fmt.Println("Checking " + domain)

  // Setup the http client
  timeout := time.Duration(5 * time.Second)
  client := http.Client {
    Timeout: timeout,
  }

  // Request to X domain
  resp, err := client.Get("https://" + domain + "/api/v1/instance/peers")

  // Make sure there are no errors
  if err != nil {
    // Return nothing
    return []string{}
  }

  defer resp.Body.Close()

  // Make sure a 200 was returned
  if resp.StatusCode != 200 {
    return []string{}
  }

  // Read the body
  body, readErr := ioutil.ReadAll(resp.Body)

  // Make sure there're no read errors
  if readErr != nil {
    return []string{}
  }

  // Parse the json
  var json_arr PeerList
  parseErr := json.Unmarshal(body, &json_arr)

  // Make sure there are not json parse errors
  if parseErr != nil {
    return []string{}
  }

  // Return string array of found domains
  return json_arr

}
