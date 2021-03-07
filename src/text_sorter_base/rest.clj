(ns text-sorter-base.rest
  (require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [text-sorter-base.core :refer :all]
    [cheshire.core :refer :all]
    [ring.adapter.jetty :refer [run-jetty]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:import (java.text SimpleDateFormat)))


(def fake-db (atom [])) ;; You don't really want state in a clojure app if you can help it but a proper DB is not in the scope of this project so this is what I'm using.

(defn add-record [req]
  (let [ftm (file-to-map (:query-string req))]
  (swap! fake-db concat ftm)
  )) ;; Some feedback would probably be a good idea.


;; This is a function to just add some data without having to worry about extra steps. Good for testing purposes but in a real situation you probably wouldn't want this in a release branch
(defn populate [req]
  (let [sorting-name [
                      {:last-name "Smith" :first-name "Adam" :email "ab@fake.com" :favorite-color "green" :birth-date "2/12/1709"}
                      {:last-name "Smith" :first-name "Bobby" :email "ab@fake.com" :favorite-color "orange" :birth-date "2/12/1739"}
                      {:last-name "Lincon" :first-name "Abe" :email "al@fake.com" :favorite-color "brown" :birth-date "2/12/1809"}
                      {:last-name "Teach" :first-name "Edward" :email "et@fake.com" :favorite-color "black" :birth-date "1/1/1619"}
                      {:last-name "Stede" :first-name "Bonnet" :email "sb@fake.com" :favorite-color "yellow" :birth-date "1/1/1629"}
                      ]]

    (swap! fake-db concat sorting-name))
  "POPULATED"
  )

(defn rec-by-email [req]
   (sort-by-email @fake-db))

(defn rec-by-birth [req]
  (sort-by-date @fake-db))

(defn rec-by-name [req] ;; In the spec it doesn't say if we're sorting by first or last name. I'm doing first for now but in a real project I'd ask the client.
  (sort-by-first-name @fake-db))


(defn to-json [f req]
  (generate-string (f req)))

(defroutes app-routes
           (GET "/" [] "Lets sort some records of people out!")
           (GET "/records/email" [] #(to-json rec-by-email %))  ;;returns records sorted by email
           (GET "/records/birthdate" [] #(to-json rec-by-birth %)) ;;returns records sorted by birthdate
           (GET "/records/name" [] #(to-json rec-by-name %)) ;;returns records sorted by name
           (GET "/records/populate" [] #(to-json populate %)) ;; For testing purposes populates our temp DB
           (POST "/records/add" [] add-record) ;;Post a single data line in any of the 3 formats supported by your existing code. This actually might not work with spaces.
           (route/resources "/")
           (route/not-found "Not Found"))

(defn start-server [] ;; We may want to add a stop server function too.
  (run-jetty app-routes {:port 3000 :join? false}))