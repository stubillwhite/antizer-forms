(ns antizer-forms.state
  (:require
   [reagent.core :as reagent]))

;; Application state
(defonce state (reagent/atom {}))

(defn update-state! [f & args]
  (swap! state f)
  (js/console.info @state))

