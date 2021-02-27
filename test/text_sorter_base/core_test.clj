(ns text-sorter-base.core-test
  (:require [clojure.test :refer :all]
            [text-sorter-base.core :refer :all]
            ))

(deftest main-tests
  (testing "Sanity"
    (is (= 1 1)))
  (testing "main functionality"
    (println (-main "csvnames.txt"))
    (is (= 2 (count (-main "csvnames.txt"))))
    (is (= 5 (count (first(-main "csvnames.txt")))))
    (is (= "Jack" (:first-name (first(-main "csvnames.txt")))))

    )

  )


