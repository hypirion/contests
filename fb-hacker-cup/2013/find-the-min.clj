#! /usr/bin/env clojure

(defn lc-generator [a b c r]
  (iterate #(mod (+ (* b %) c) r) a))

(defn last-position-table [lc-seq k]
  (->> (take k lc-seq)
       (map-indexed (fn [a b] [b a]))
       (into {})))

(defn candidate-set [lc-seq k]
  (let [k+1-set (into (sorted-set) (range (inc k)))]
    (reduce disj k+1-set
            (take k lc-seq))))

(defn k+1 [lc-seq k]
  (let [hm (last-position-table lc-seq k)
        ss (candidate-set lc-seq k)]
    (letfn [(generate [i [m_i & r] ss]
              (if (= i (inc k))
                nil
                (lazy-seq
                 (let [ss* (if (<= (hm m_i 0) i)
                             (conj ss m_i)
                             ss)
                       lowest (first ss)]
                   (cons
                    lowest
                    (generate (inc i) r
                              (disj ss* lowest)))))))]
      (generate 0 lc-seq ss))))

(defn find-last []
  (let [[n k a b c r] (doall (repeatedly 6 read))
        lc-seq (lc-generator a b c r)]
    (let [next-k+1 (k+1 lc-seq k)]
      (nth next-k+1 (mod (- n k 1) (inc k))))))

(defn pprint-result [pos res]
  (println (format "Case #%d: %d" (inc pos) res)))

(defn main []
  (let [T (read)]
    (read-line) ;; Finish off the first line.
    (dotimes [t T]
      (->> (find-last)
           (pprint-result t)))))
(main)
