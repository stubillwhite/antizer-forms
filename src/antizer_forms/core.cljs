(ns ^:figwheel-hooks antizer-forms.core
  (:require [antizer-forms.page :as page]
            [goog.dom :as gdom]
            [reagent.core :as reagent]))

;; Application view
;; --------------------------------------------------

(defn- app-view
  "The root view of the application."
  []
  [page/view {}])

;; Entry point
;; --------------------------------------------------

(defn- get-app-element []
  (gdom/getElement "app"))

(defn- mount [el]
  (reagent/render-component [app-view] el))

(defn mount-app-element
  "Conditionally load the application if an 'app' element is present,
  configuring hooks appropriately depending on whether this is a
  hot-reload or a full reload."
  [is-reload]
  (when-let [el (get-app-element)]
    (do
      (mount el))))

(defn ^:after-load on-reload
  "Hook called on a hot-reload."
  []
  (mount-app-element true)
  ;; Optionally touch your state to force rerendering depending on your application
  ;; (swap! state update-in [:__figwheel_counter] inc)
)

(mount-app-element false)
