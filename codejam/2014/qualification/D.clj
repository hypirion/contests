(require '[clojure.string :as s])

(defn higher [sc n]
  (first (subseq sc > n)))

(defn deceitful
  ([m] (deceitful m 0))
  ([{:keys [naomi ken]} wins-so-far]
     (if-not (seq naomi)
       wins-so-far
       (let [k (first ken)
             n (higher naomi k)]
         (if-not n
           wins-so-far
           (recur {:naomi (disj naomi n)
                   :ken (disj ken k)}
                  (inc wins-so-far)))))))

(defn sincere
  ([m] (sincere m 0))
  ([{:keys [naomi ken]} wins-so-far]
     (if-not (seq naomi)
       wins-so-far
       (let [n (first naomi)
             k (or (higher ken n)
                   (first ken))]
         (recur {:naomi (disj naomi n)
                 :ken (disj ken k)}
                (if (> n k)
                  (inc wins-so-far)
                  wins-so-far))))))

(def solve (juxt deceitful sincere))

(defn read-input []
  (let [n (read)
        naomi (apply sorted-set (repeatedly n read))
        ken (apply sorted-set (repeatedly n read))]
    {:n n :naomi naomi :ken ken}))

(defn pprint-result [idx [d s]]
  (format "Case #%d: %d %d" (inc idx) d s))

(defn main []
  (let [T (read)
        inputs (doall (repeatedly T read-input))
        results (pmap solve inputs)]
    (->> (map-indexed pprint-result results)
         (s/join "\n")
         (println))
    (flush)
    (System/exit 0)))

(main)
