#!/usr/bin/env julia

M = 1_000_000_007

T = int(readline(STDIN))

function perms(i, d, last, cars)
    if i == 0
        return 1
    end
    shift = 0
    count = 0
    while i >> shift != 0
        if (i >> shift) & 1 == 1
            car = cars[shift+1]
            dcpy = copy(d)
            usable = true
            nlast = last
            if get(dcpy, nlast, 0) == 0 || nlast == car[1]
                for j in 1:endof(car)
                    cur = car[j]
                    if cur != nlast && get(dcpy, nlast, 0) != 0
                        usable = false
                    end
                    dcpy[cur] -= 1
                    nlast = cur
                end
            else
                usable = false
            end
            if usable
                count += perms(i & (~(1 << shift)), dcpy,
                               car[end], cars)
                count %= M
            end
        end
        shift += 1
    end
    count
end

for t in 1:T
    N = int(readline(STDIN))
    cars = split(readline(STDIN))
    mask = (1 << N) - 1
    d = Dict()
    for i in 1:N
        for j in 1:endof(cars[i])
            d[cars[i][j]] = get(d, cars[i][j], 0) + 1
        end
    end
    @printf("Case #%d: %d\n", t, perms(mask, d, nothing, cars));
end
