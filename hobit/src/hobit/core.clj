(ns hobit.core
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "hey "))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts
    final-body-parts []]
    (if (empty? remaining-asym-parts)
    final-body-parts
    (let [[part & remaining] remaining-asym-parts]
      (recur remaining
        (into final-body-parts
          (set [part (matching-part part)])))))))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
    (into final-body-parts (set [part (matching-part part)])))
    []
    asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
      body-part-size-sum (reduce + (map :size sym-parts))
      target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))


; Ch-3 exercise 1
(defn exercise
  [name]
  (println str "hey it's a happy day " name)   ;str
  ( get [3 2 1 ] 0 ))                            ;vector                            


(defn el   ;parameter list is mandatory in defn
  []
  ( nth '(3 2 1 ) 0 ))  ; list                                   
    
(defn ehm
  []
  (get {:a 0 :b 1} :b)) ;hash-map

(defn ehs
  []
  (conj #{:a :b} :b)) ;hash-set

; Ch-3 exercise 2
(defn add
  [n]
  (+ 100 n))

; Ch-3 ex 3
(defn dec-maker
  [dec-by]
  #(- % dec-by))      ;(* 8 3) == #(* % 3)

;ex 4
; mapset works like map but return set
; (mapset inc [1 1 2 2])
; => #{2 3}
(defn mapset
  [func & args]
  (set (map func (first args)))
  )





;CHAPTER 4
(def vampire-database
  {0 {:makes-blood-puns? false, :has-pulse? true  :name "McFishwich"}
   1 {:makes-blood-puns? false, :has-pulse? true  :name "McMackson"}
   2 {:makes-blood-puns? true,  :has-pulse? false :name "Damon Salvatore"}
   3 {:makes-blood-puns? true,  :has-pulse? true  :name "Mickey Mouse"}})

(defn vampire-related-details
  [social-security-number]
  (Thread/sleep 1000)
  (get vampire-database social-security-number))

(defn vampire?
  [record]
  (and (:makes-blood-puns? record)
       (not (:has-pulse? record))
       record))

(defn identify-vampire
  [social-security-numbers]
  (first (filter vampire?
                 (map vampire-related-details social-security-numbers))))

(defn even-numbers
([] (even-numbers 0))
([n] (cons n (lazy-seq (even-numbers (+ n 2))))))

; (take 10 (even-numbers))