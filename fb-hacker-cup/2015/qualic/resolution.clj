#!/usr/bin/env clojure
(require '[clojure.string :as s])

(defn ic [n c j]
  (if (> j n)
    nil
    (let [c (assoc c j (dec (c j)))]
      (if (< (c j) j)
        [c (inc j)]
        (loop [c c, j j]
          (if (= j 1)
            [c j]
            (recur (assoc c (dec j) (dec (c j))) (dec j))))))))

(defn step [n c j]
  (cons (rseq (subvec c 1 (inc n)))
        (lazy-seq
         (let [next-step (ic n c j)]
           (when next-step
             (step n (next-step 0) (next-step 1)))))))

(defn subsets
  ;; urgh
  ([xs n]
   (let [v-xs (vec (reverse xs))]
     (if (zero? n) (list ())
         (let [cnt (count xs)]
           (cond (> n cnt) nil
                 (= n cnt) (list (seq xs))
                 :else
                 (map #(map v-xs %)
                      (lazy-seq
                       (let [c (vec (cons nil (for [j (range 1 (inc n))]
                                                (+ j cnt (- (inc n))))))]
                         (step n c 1)))))))))
  ([xs]
   (mapcat (fn [n] (subsets xs n))
           (range (inc (count xs))))))

(defn solve [[goal foods]]
  (let [c (map (fn [subset] (reduce #(mapv + %1 %2) [0 0 0] subset))
               (subsets foods))]
    (boolean (some #(= goal %) c))))

(def read3 (juxt read read read))
(defn read-input []
  (let [goal (read3)
        food-count (read)
        foods (vec (repeatedly food-count read3))]
    [goal foods]))

(defn pprint-result [idx x]
  (format "Case #%d: %s" (inc idx) (if x "yes" "no")))

(defn main []
  (let [T (read)
        inputs (doall (repeatedly T read-input))
        results (map solve inputs)]
    (->> (map-indexed pprint-result results)
         (s/join "\n")
         (println))
    (flush)
    (System/exit 0)))

(main)
