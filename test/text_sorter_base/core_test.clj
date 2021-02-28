(ns text-sorter-base.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [text-sorter-base.core :refer :all]
            )
  (:import (java.text SimpleDateFormat))
  )

(deftest main-tests
  (testing "Sanity";; Basic test for sanity
    (is (= 1 1)))
  (testing "main functionality" ;; Tests the top level funcitons of the app.
    (is (= 3 (count (-main "csvnames.txt"))))
    (is (= 5 (count (first(-main "csvnames.txt")))))
    (is (= "Jack" (:first-name (first(-main "csvnames.txt")))))
    (is (= 3 (count (file-to-map(slurp (io/resource "csvnames.txt"))))))
    )

  ;; Here's a test map. The reason we don't just use the file-to-map funciton is that if that one funciton breaks all these tests will fail.
  ;; This makes it way easier to narrow down points of failure.
  (testing "sorting functions"
    (let[sorting-name [
                       {:last-name "Smith" :first-name "Adam" :email "as@fake.com" :favorite-color "green" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"2/12/1709")}
                       {:last-name "Lincon" :first-name "Abe" :email "al@fake.com" :favorite-color "brown" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"2/12/1809")}
                       {:last-name "Teach" :first-name "Edward" :email "et@fake.com" :favorite-color "black" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"1/1/1619")}
                       {:last-name "Stede" :first-name "Bonnet" :email "sb@fake.com" :favorite-color "yellow" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"1/1/1629")}
                       ]]
      ;; here we're testing the sorting
      (is (= "Lincon" (:last-name (last (sort-by-last-name sorting-name)))))
      (is (= "Teach" (:last-name (first (sort-by-last-name sorting-name)))))

      (is (= "Abe" (:first-name (first (sort-by-first-name sorting-name)))))
      (is (= "Edward" (:first-name (last (sort-by-first-name sorting-name)))))

      (is (= "al@fake.com" (:email (last (sort-by-email sorting-name)))))
      (is (= "sb@fake.com" (:email (first (sort-by-email sorting-name)))))

      (is (= "black" (:favorite-color (first (sort-by-color sorting-name)))))
      (is (= "yellow" (:favorite-color (last (sort-by-color sorting-name)))))

      (is (= "Edward" (:first-name (first (sort-by-date sorting-name)))))
      (is  (= "Abe" (:first-name (last (sort-by-date sorting-name)))))
      )))