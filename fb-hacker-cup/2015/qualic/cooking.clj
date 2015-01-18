#!/usr/bin/env clojure
(require '[clojure.string :as s])

(defn num->digits
  "Converts the number n into a list of digits in radix r. r defaults to 10."
  ([n] (num->digits n 10))
  ([n r]
   (loop [m n
          acc ()]
     (if (zero? m)
       acc
       (recur (quot m r)
              (conj acc (rem m r)))))))

(defn digits->num
  "Converts a sequence of digits in radix r to an integer. r defaults to 10."
  ([coll] (digits->num coll 10))
  ([coll r]
     (reduce
      (fn [a b] (+ (* r a) b))
      0
      coll)))

(defn replace-last
  "Replaces last occurence of original with replacement in coll."
  [coll original replacement]
  (let [rec (fn rfn [[f & r :as lst]]
              (if-not (seq lst)
                [false lst]
                (let [[done r'] (rfn r)]
                  (cond done [done (cons f r')]
                        (= f original) [true (cons replacement r)]
                        :otherwise [false lst]))))]
    (second (rec coll))))

(defn find-highest [[f & r :as digs]]
  (if (<= (count digs) 1)
    digs
    (let [high (reduce max digs)]
      (if (= high f) ; changing first digit won't do, recurse
        (cons f (find-highest r))
        (cons high (replace-last r high f))))))

(defn find-lowest' [[f & r :as digs]]
  ;; can switch around on zeroes inside the number, just not first
  (if (<= (count digs) 1)
    digs
    (let [low (reduce min digs)]
      (if (= low f)
        (cons f (find-lowest' r))
        (cons low (replace-last r low f))))))

(defn find-lowest [[f & r :as digs]]
  (if (<= (count digs) 1)
    digs
    (let [low (reduce min (remove zero? digs))]
      (if (= low f)
        (cons f (find-lowest' r))
        (cons low (replace-last r low f))))))

(def solve (comp #(map digits->num %)
                 (juxt find-lowest find-highest)
                 num->digits))

(defn pprint-result [idx [lowest highest]]
  (format "Case #%d: %d %d" (inc idx) lowest highest))

(defn main []
  (let [T (read)
        inputs (doall (repeatedly T read))
        results (map solve inputs)]
    (->> (map-indexed pprint-result results)
         (s/join "\n")
         (println))
    (flush)
    (System/exit 0)))

(main)
