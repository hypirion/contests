#!/usr/bin/env bash

read T

for (( t=1; t <= T; t++)); do
    read A B K
    c=0
    for (( a=0; a < A; a++ )); do
        for (( b=0; b < B; b++ )); do
            if [ $(( a & b )) -lt $K ]; then c=$(( c + 1 )); fi
        done
    done
    echo "Case #$t: $c"
done
