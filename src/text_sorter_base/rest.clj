(ns text-sorter-base.rest
  (:require
    [compojure.core :refer :all]
    [compojure.route :as route]
    [cheshire.core :refer :all]
    [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))
