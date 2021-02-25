(ns text-sorter-base.core-test
  (:require [clojure.test :refer :all]
            [text-sorter-base.core :refer :all]
            ))

(deftest main-tests
  (testing "Sanity"
    (is (= 1 1)))
  (testing "main functionality"
    (is (= "Walters,Jack,jw@fake.com,grey, DateOfBirth, 11/11/1911" (-main "csvnames.txt")))
    )

  )


