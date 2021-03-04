(ns text-sorter-base.rest
  (require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [text-sorter-base.core :refer :all]
    [cheshire.core :refer :all]
    [ring.adapter.jetty :refer [run-jetty]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def fake-db (atom [])) ;; You don't really want state in a clojure app if you can help it but a proper DB is not in the scope of this project so this is what I'm using.

(defn add-record [req];; NOTE make sure that this is being done properly with calls from the actual website.
  ;; ALSO NOTE that the conj function may not be working properly.
  (swap! fake-db conj req)
  ;;"RECORD HAS BEEN ADDED!!" NOTE add some sort of feedback
  )

(defn rec-by-email [req]
  (sort-map "email" req)
  )

(defn rec-by-birth [req]
  (sort-map "date" req)
  )

(defn rec-by-name [req] ;; In the spec it doesn't say if we're sorting by first or last name. I'm doing first for now but in a real project I'd ask the client.
  (sort-map "first name" req)
  )

(defn to-json [f req]
  (generate-string (f req)))

(defroutes app-routes
           (GET "/" [] "Lets sort some records of people out!")
           (GET "/records/email" [] #(to-json rec-by-email %))  ;;returns records sorted by email
           (GET "/records/birthdate" [] #(to-json rec-by-birth %)) ;;returns records sorted by birthdate
           (GET "/records/name" [] #(to-json rec-by-name %)) ;;returns records sorted by name
           (POST "/records/add" [] #(to-json add-record %)) ;;Post a single data line in any of the 3 formats supported by your existing code. This actually might not work with spaces.
           (route/resources "/");; NOTe remove the to-json for add record
           (route/not-found "Not Found"))

#_(def app
  (handler/site app-routes))

(defn start-server []
  (run-jetty app-routes {:port 3000 :join? false}))