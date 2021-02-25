(ns text-sorter-base.core
  (require [clojure.java.io :as io])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [file-location]
  (slurp (io/resource file-location))
  )
