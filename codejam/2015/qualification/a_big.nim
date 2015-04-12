import strutils

var T = parseInt(readline(stdin))

for t in countup(1, T):
    let foo = split(readline(stdin))
    let maxIdx = parseInt(foo[0])
    let audience = foo[1]
    var tot = 0
    var need = 0
    for i in countup(0, maxIdx):
        if tot < i:
           need += i - tot
           tot = i
        tot += int(audience[i]) - int('0')
    echo("Case #", $t, ": ", $need)
