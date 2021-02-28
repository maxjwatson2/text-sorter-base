(ns text-sorter-base.core
  (require [clojure.java.io :as io]
           [clojure.string :as str]

           )
  (:gen-class)
  (:import (java.text SimpleDateFormat)))

;; I think its wise to convert the date string to a proper date here because that's what we want to return. In a real situation it would depend on use case.
;; It should be noted that these dates are down the the milisecond, which could be either a good thing or a bad thing depending on what kind of app we're making.
(defn person-to-map [person-array]
  {:last-name (person-array 0) :first-name (person-array 1) :email (person-array 2) :favorite-color (person-array 3) :birth-date (.parse (SimpleDateFormat. "M/D/YYYY") (person-array 4))}) ;; NOTE this may break things so maybe dont

;; A simple function to split the individual up by the delimiter(NOTE check if right word). Could be a problem is the delimiter is in the person's name but that's outside the scope of this assignment.
(defn file-to-map [file]
  (let [parsed-blob (clojure.string/split-lines file)]
    (for [b parsed-blob]
      (do (cond
            (str/includes? file ",") (person-to-map (clojure.string/split b #","))
            (str/includes? file "-") (person-to-map (clojure.string/split b #""))
            (str/includes? file " ") (person-to-map (clojure.string/split b #" "))
            :else (println "File is invalid"))))))

;; We only need email descending, birthdate ascending, and last name decending but I'm adding in a few more because it's simple, good for tests and would be usefull in a real business situation.
(defn sort-by-last-name [person-map]
  (reverse (sort-by :last-name person-map)))

(defn sort-by-first-name [person-map]
  (sort-by :first-name person-map))

(defn sort-by-email [person-map]
  (reverse (sort-by :email person-map)))

(defn sort-by-color [person-map]
  (sort-by :favorite-color person-map))

(defn sort-by-date [person-map]
  (->> person-map (sort-by last #(compare %1 %2))))

(defn sort-map [person-map sort-method] ;; NOTE make sure that the right things are ascending/decending before finalization.
  (case sort-method
    "last name" (sort-by-last-name person-map)
    "first name" (sort-by-first-name person-map)
    "email" (sort-by-email person-map)
    "color" (sort-by-color person-map)
    "date" (sort-by-date person-map)
    :else (println "Sort method invalid")))

(defn -main
  "Currently pulls a file form location and sorts it into a map."
  [file-location]
  (let [mapped-file (file-to-map(slurp (io/resource file-location)))]
    (sort-map mapped-file "last name")
    mapped-file
    ))