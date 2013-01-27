#! /usr/bin/env clojure

(defmulti update-state (fn [state type] type))

(defmethod update-state "(" [state _]
  (set (map inc state)))

(defmethod update-state ")" [state _]
  (->> (map dec state)
       (remove neg?)
       set))

(defmethod update-state :default [state smiley]
  (into state (update-state state
                            (subs smiley 1))))

(defn remove-irrelevant [string]
  (re-seq #"\(|\)|:\(|:\)" string))

(defn possibly-balanced? []
  (let [relevant (remove-irrelevant (read-line))]
    (-> (reduce update-state #{0} relevant)
        (contains? 0))))

(defn pprint-result [pos res]
  (println (format "Case #%d: %s" (inc pos)
                   (if res "YES" "NO"))))

(defn main []
  (let [T (read)]
    (read-line) ;; Finish off the first line.
    (dotimes [t T]
      (->> (possibly-balanced?)
           (pprint-result t)))))
(main)
