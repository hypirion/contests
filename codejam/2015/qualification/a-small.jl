#!/usr/bin/env julia

T = int(readline(STDIN))

for t in 1:T
    foo = split(readline(STDIN))
    maxIdx = int(foo[1])
    audience = foo[2]
    tot = 0
    need = 0
    for i in 0:maxIdx
        if tot < i
            need += i - tot
            tot = i
        end
        cnt = audience[i+1] - '0'
        tot += cnt
    end
    @printf("Case #%d: %d\n", t, need);
end
