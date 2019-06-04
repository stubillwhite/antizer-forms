(ns antizer-forms.integration.server
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.defaults :refer [wrap-defaults]]
            [ring.util.response :refer [not-found redirect]]))

;; In production, this would spin up a server with a back end and a hosted index.html and the production Javascript.
;; This is just a simple example, so we just spin up Jetty with just the Javascript hosted.

(defroutes app-routes
  (GET "/" [] (redirect "index.html"))
  (route/resources "/cljs-out" {:root "/dist"})
  (route/resources "/"         {:root "/public"})
  (route/not-found (not-found "Not found")))

(def default-config
  {:params {:urlencoded true
            :keywordize true
            :nested     true
            :multipart  true}

   :responses {:not-modified-responses true
               :absolute-redirects     true
               :content-types          true}})

(def app
  (-> app-routes
    (wrap-defaults default-config)
    (wrap-content-type)))

(defn with-server [f]
  (let [server (jetty/run-jetty app {:port 9500 :join? false})]
    (f)
    (.stop server)))
