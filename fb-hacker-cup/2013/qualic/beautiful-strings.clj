#! /usr/bin/env clojure

(defn best-solution []
  (->> (read-line)
       .toLowerCase
       (filter #(Character/isLetter %))
       frequencies
       vals
       (sort >)
       (map * (iterate dec 26))
       (reduce +)))

(defn pprint-result [pos res]
  (println (format "Case #%d: %d" (inc pos) res)))

(defn main []
  (let [T (read)]
    (read-line) ;; Finish off the first line.
    (dotimes [t T]
      (->> (best-solution)
           (pprint-result t)))))
(main)
