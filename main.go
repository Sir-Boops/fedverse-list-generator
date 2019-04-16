package main

import "os"
import "fmt"

func main() {

  // Make sure we have atleast 2 args
  if len(os.Args) < 2 {
    fmt.Println("Add a starting domain!")
    os.Exit(1)
  }

  // Fetch the initial list
  // and make sure it's not empty
  base_list := api_request(os.Args[1])

  if len(base_list) <= 0 {
    fmt.Println("Bad starting instance")
    os.Exit(1)
  }

  // Create the two lists
  var ToCheck []string
  var Checked []string

  ToCheck = base_list
  Checked = append(Checked, os.Args[1])

  for i := 0; i < len(ToCheck); i++ {
    NewList := api_request(ToCheck[i])
    Checked = append(Checked, ToCheck[i])

    if len(NewList) > 0 {
      for i2 := 0; i2 < len(NewList); i2++ {
        // Make Sure it has not already been checked
        BeenChecked := false
        for i3 := 0; i3 < len(Checked); i3++ {
          if NewList[i2] == Checked[i3] {
            BeenChecked = true
            break
          }
        }

        // Make Sure it's not already going to be checked
        StringToCheck := false
        if !BeenChecked {
          for i3 := 0; i3 < len(ToCheck); i3++ {
            if NewList[i2] == ToCheck[i3] {
              StringToCheck = true
              break
            }
          }
        }

        // If it's not found add it to the to check pile
        if !BeenChecked && !StringToCheck {
          ToCheck = append(ToCheck, NewList[i2])
        }
      }
    }
    fmt.Println("Current Checked instances:", len(Checked))
    fmt.Println("Current Instances to Check:", len(ToCheck))
  }
}
