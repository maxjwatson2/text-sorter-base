(ns text-sorter-base.rest
  (require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [cheshire.core :refer :all]
    [ring.adapter.jetty :refer [run-jetty]]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]])
  (:gen-class))

(def fake-db (atom [])) ;; You don't really want state in a clojure app if you can help it but a proper DB is not in the scope of this project so this is what I'm using.

(defn add-record [var]

  )

(defn rec-by-email [var])

(defn rec-by-birth [var])

(defn rec-by-name [var])

(defn to-json [f req]
  (generate-string (f req)))

(defroutes app-routes
           (GET "/" [] "Hello World")
           (GET "/records/email" [] #(to-json rec-by-email %))  ;;returns records sorted by email
           (GET "/records/birthdate" [] #(to-json rec-by-birth %)) ;;returns records sorted by birthdate
           (GET "/records/name" [] #(to-json rec-by-name %)) ;;returns records sorted by name
           (POST "/records" [] #(to-json add-record %)) ;;Post a single data line in any of the 3 formats supported by your existing code
           (route/resources "/")
           (route/not-found "Not Found"))

#_(def app
  (handler/site app-routes))

(defn start-server []
  (run-jetty app-routes {:port 3000 :join? false}))