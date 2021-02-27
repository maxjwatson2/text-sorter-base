(ns text-sorter-base.core
  (require [clojure.java.io :as io]
           [clojure.string :as str]
           )
  (:gen-class))


(defn person-to-map [person-array]
  {:last-name (person-array 0) :first-name (person-array 1) :email (person-array 2) :favorite-color (person-array 3) :birth-day (person-array 4)})

(defn file-to-map [file]
  (let [parsed-blob (clojure.string/split-lines file)]
    (for [b parsed-blob]
      (do (cond
            (str/includes? file ",") (person-to-map (clojure.string/split b #","))
            (str/includes? file "-") (person-to-map (clojure.string/split b #"-"));;NOTE replace this with |
            (str/includes? file " ") (person-to-map (clojure.string/split b #" "))
            :else (println "File is invalid"))))))

(defn -main
  "Currently pulls a file form location and sorts it into a map."
  [file-location]
  (let [mapped-file (file-to-map(slurp (io/resource file-location)))]
    mapped-file
    )
  )