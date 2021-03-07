(ns text-sorter-base.rest-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [cheshire.core :refer :all]
            [text-sorter-base.core :refer :all]
            [text-sorter-base.rest :refer :all]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (parse-string (slurp body) true))

(deftest main-tests
  (testing "Sanity";; Basic test for sanity. the same as in core tests
    (is (= 1 1)))

  (testing "Web pages"
    (let [returned-recs (app-routes {:request-method :post :uri "/records/add" :query-string (slurp (io/resource "csvnames.txt"))})
          main-page (app-routes (-> (mock/request :get "http://localhost:3000/")))
          email-page (app-routes (-> (mock/request :get "http://localhost:3000/records/email")))
          birthdate-page (app-routes (-> (mock/request :get "http://localhost:3000/records/birthdate")))
          names-page (app-routes (-> (mock/request :get "http://localhost:3000/records/name")))]

      (is (= 200 (:status returned-recs)))
      (is (= 200 (:status main-page)))
      (is (= "Lets sort some records of people out!" (:body main-page)))

      (is (= 200 (:status email-page)))
      (is (= 503 (count (:body email-page)))) ;; This test is pretty bad, but it shows that we're returning the data back. I could make more but I'm tired so this is how it is for now.

      (is (= 200 (:status birthdate-page)))
      (is (= 616 (count (:body birthdate-page))))

      (is (= 200 (:status names-page)))
      (is (= 616 (count (:body names-page)))))))