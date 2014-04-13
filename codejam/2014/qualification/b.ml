(* OCaml. Compile with `ocamlopt -o b b.ml` *)

let read_int () : int = 
  Scanf.bscanf Scanf.Scanning.stdin " %d " (fun x -> x)

let read_float () : float =
  Scanf.bscanf Scanf.Scanning.stdin " %f " (fun x -> x)

let rec optimal farm_price farm_rate rate goal : float =
  let no_farm_time = goal /. rate in
  let time_to_farm = ( farm_price /. rate ) in
  let one_farm_time = (goal /. ( rate +. farm_rate ) ) +. time_to_farm in
  if one_farm_time < no_farm_time then
    (optimal farm_price farm_rate ( rate +. farm_rate ) goal) +. time_to_farm
  else
    no_farm_time

let initial_rate = 2.0
      
let () =
  for case = 1 to read_int() do
    let farm_price = read_float () in
    let farm_rate = read_float () in
    let goal = read_float () in
    let solution = optimal farm_price farm_rate initial_rate goal in
    (Printf.printf "Case #%d: %.7f\n" case solution)
  done
