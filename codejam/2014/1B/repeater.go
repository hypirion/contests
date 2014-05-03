package main

import (
	"fmt"
)

func main() {
	var T int
	fmt.Scan(&T)
	for t := 1; t <= T; t++ {
		var N int
		fmt.Scan(&N)
		words := make([]string, N)
		for i := 0; i < N; i++ {
			fmt.Scan(&words[i])
		}
		// dedupe check
		if !possible(words) {
			fmt.Printf("Case #%d: Fegla Won\n", t)
		} else {
			sum := 0
			groups := make([][]int, N)
			for i := 0; i < N; i++ {
				groups[i] = partition(words[i])
			}
			// only works for n = 2
			for i := 0; i < len(groups[0]); i++ {
				if groups[0][i] > groups[1][i] {
					sum += groups[0][i] - groups[1][i]
				} else if groups[1][i] > groups[0][i] {
					sum += groups[1][i] - groups[0][i]
				}
			}

			fmt.Printf("Case #%d: %d\n", t, sum)
		}
	}
}

func possible(words []string) bool {
	deduped := make([]string, len(words))
	for i := 0; i < len(words); i++ {
		deduped[i] = dedupe(words[i])
	}
	for i := 0; i < len(words)-1; i++ {
		if deduped[i] != deduped[i+1] {
			return false
		}
	}
	return true
}

func dedupe(word string) string {
	deduped := make([]rune, 0, len(word))
	deduped = append(deduped, []rune(word)[0])
	for _, c := range word {
		if deduped[len(deduped)-1] != c {
			deduped = append(deduped, c)
		}
	}
	return string(deduped)
}

func partition(word string) []int {
	grouping := make([]int, 1)
	grouping[0] = 0
	last := []rune(word)[0]
	cur := 0
	for _, c := range word {
		if c == last {
			grouping[cur]++
		} else {
			cur++
			grouping = append(grouping, 1)
			last = c
		}
	}
	return grouping
}
