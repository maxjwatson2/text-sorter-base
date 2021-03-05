(ns text-sorter-base.rest-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [cheshire.core :refer :all]
            [text-sorter-base.core :refer :all]
            [text-sorter-base.rest :refer :all]
            [ring.mock.request :as mock])
  (:import (java.text SimpleDateFormat)))

(defn parse-body [body]
  (parse-string (slurp body) true))

(deftest main-tests
  (testing "Sanity";; Basic test for sanity. the same as in core tests
    (is (= 1 1)))
  (let[sorting-name [
                     {:last-name "Smith" :first-name "Adam" :email "ab@fake.com" :favorite-color "green" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"2/12/1709")}
                     {:last-name "Smith" :first-name "Bobby" :email "ab@fake.com" :favorite-color "orange" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"2/12/1739")}
                     {:last-name "Lincon" :first-name "Abe" :email "al@fake.com" :favorite-color "brown" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"2/12/1809")}
                     {:last-name "Teach" :first-name "Edward" :email "et@fake.com" :favorite-color "black" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"1/1/1619")}
                     {:last-name "Stede" :first-name "Bonnet" :email "sb@fake.com" :favorite-color "yellow" :birth-date  (.parse (SimpleDateFormat. "M/D/YYYY")"1/1/1629")}
                     ]]
    (is (= 0 (count @fake-db)))
    (app-routes (-> (mock/request :get "http://localhost:3000/records/populate/")));; Adding code to reset the atom doesn't seem to be necessary BUT we may wanna add a reset function anyway to be thorough.
    (is (= 1 (count @fake-db)))

    (let [main-page (app-routes (-> (mock/request :get "http://localhost:3000/")))
          email-page (app-routes (-> (mock/request :get "http://localhost:3000/records/email/")))
          birthdate-page (app-routes (-> (mock/request :get "http://localhost:3000/records/birthdate/")))
          names-page (app-routes (-> (mock/request :get "http://localhost:3000/records/name/")))
        ;;This doesn't work  post-page (app-routes (-> (mock/request :get "http://localhost:3000/records/add/")))

          ;body (parse-body (:body response))
          ]


      ;;(add-record sorting-name)

      (testing "Web pages"
      (is (= 200 (:status main-page)))
      (is (= "Lets sort some records of people out!" (:body main-page)))

      ))))

