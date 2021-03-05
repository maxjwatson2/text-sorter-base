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

(defn add-record [req];; NOTE make sure that this is being done properly with calls from the actual website.
  ;; ALSO NOTE that the conj function may not be working properly.
  (println "Sdasdasdasd")
  (swap! fake-db conj (parse-string (:body req) true))
  "<h1>Success</h1>"
  ;(swap! fake-db conj req);; Fix this it needs to be the body
  ;"RECORD ADDED"

  ;;"RECORD HAS BEEN ADDED!!" NOTE add some sort of feedback
  )

(defn populate [req] ;; This is to save us trouble with manually adding data.
  (println "POPULATION")
  (let [sorting-name [
                      {:last-name "Smith" :first-name "Adam" :email "ab@fake.com" :favorite-color "green" :birth-date (.parse (SimpleDateFormat. "M/D/YYYY") "2/12/1709")}
                      {:last-name "Smith" :first-name "Bobby" :email "ab@fake.com" :favorite-color "orange" :birth-date (.parse (SimpleDateFormat. "M/D/YYYY") "2/12/1739")}
                      {:last-name "Lincon" :first-name "Abe" :email "al@fake.com" :favorite-color "brown" :birth-date (.parse (SimpleDateFormat. "M/D/YYYY") "2/12/1809")}
                      {:last-name "Teach" :first-name "Edward" :email "et@fake.com" :favorite-color "black" :birth-date (.parse (SimpleDateFormat. "M/D/YYYY") "1/1/1619")}
                      {:last-name "Stede" :first-name "Bonnet" :email "sb@fake.com" :favorite-color "yellow" :birth-date (.parse (SimpleDateFormat. "M/D/YYYY") "1/1/1629")}
                      ]]

    (add-record sorting-name))
  "POPULATE"
  )

(defn rec-by-email [req]
  (sort-by-last-name (first @fake-db))
  (str
    "SORTING BY EMAILS"
   (sort-by-email (first @fake-db))))

(defn rec-by-birth [req]
  (str "SORTING BY DATE"
  (sort-by-date (first @fake-db))))

(defn rec-by-name [req] ;; In the spec it doesn't say if we're sorting by first or last name. I'm doing first for now but in a real project I'd ask the client.
  (str "SORTING BY NAME"(sort-by-first-name (first @fake-db))))


(defn to-json [f req]
  (generate-string (f req)))

(defroutes app-routes
           (GET "/" [] "Lets sort some records of people out!")
           (GET "/records/email" [] #(to-json rec-by-email %))  ;;returns records sorted by email
           (GET "/records/birthdate" [] #(to-json rec-by-birth %)) ;;returns records sorted by birthdate
           (GET "/records/name" [] #(to-json rec-by-name %)) ;;returns records sorted by name
           (GET "/records/populate" [] #(to-json populate %)) ;; For testing purposes populates our temp DB
           (POST "/records/add" [] #(to-json add-record %)) ;;Post a single data line in any of the 3 formats supported by your existing code. This actually might not work with spaces.
           (route/resources "/");; NOTe remove the to-json for add record
           (route/not-found "Not Found"))

#_(def app
  (handler/site app-routes))

(defn start-server [] ;; NOTE we may wanna add a stop server function too.
  (run-jetty app-routes {:port 3000 :join? false}))