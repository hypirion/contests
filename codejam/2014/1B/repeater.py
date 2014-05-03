#!/usr/bin/env python

def dedupe(word):
    deduped = word[0]
    for c in word:
        if deduped[-1] != c:
            deduped += c
    return deduped

def possible(words):
    deduped = map(dedupe, words)
    return all(x == deduped[0] for x in deduped)

def partition(word):
    group = [0]
    last = word[0]
    cur = 0
    for c in word:
        if c == last:
            group[-1] += 1
        else:
            cur += 1
            group.append(1)
            last = c
    return group

def minimal_movement(groups):
    hi = max(groups)
    lo = min(groups)
    return min([sum([abs(x - y) for x in groups]) for y in range(lo, hi+1)])

T = int(raw_input())

for t in range(1, T+1):
    N = int(raw_input())
    words = [raw_input() for x in range(N)]
    if not possible(words):
        print "Case #{0}: Fegla Won".format(t)
    else:
        groups = zip(*map(partition, words)) # Transposed
        print "Case #{0}: {1}".format(t, sum(map(minimal_movement, groups)))
