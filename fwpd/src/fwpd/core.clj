(ns fwpd.core)

(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions 
  {:name identity
  :glitter-index str->int})

(defn convert 
  [vamp-key value]
  ((get conversions vamp-key) value))


; The parse function takes a string and first splits it on the newline character to create a seq of strings. Next, it maps over the seq of strings, splitting each one on the comma character. Try running parse on your CSV:
(defn parse
  "convert csv into row and column"
  [string]
  (map 
    #(clojure.string/split % #",")
    (clojure.string/split string #"\r\n")))

(defn mapify
  "return seq of maps like {:name :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
          (reduce (fn [row-map [vamp-key value]]
                    (assoc row-map vamp-key (convert vamp-key value)))
                  {}
                  (map vector vamp-keys unmapped-row)))
          rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))