(ns antizer-forms.pages.coffee-test
  (:require [antizer.reagent :as ant]
            [cljs.test :refer-macros [deftest is testing]]
                        
            [goog.dom :as gdom]
            [reagent.core :as reagent]))

;; (ns antizer-forms.pages.coffee-test
;;   (:require [cljs.test :refer-macros [deftest is testing]]
;;             [antizer-forms.pages.coffee :refer :all]
;;             [antizer-forms.state :refer [state]]
;;             [reagent.core :as reagent :refer [atom]]))

;; (deftest increment-then-increments-count
;;   (with-redefs [state (atom {:coffee {:count 0}})]
;;     (do (increment-count))
;;     (is (= 1 (get-in @state [:coffee :count])))))

;; (deftest decrement-then-decrements-count
;;   (with-redefs [state (atom {:coffee {:count 0}})]
;;     (do (decrement-count))
;;     (is (= -1 (get-in @state [:coffee :count])))))


(defn- get-app-test-element []
  (js/console.log "WHITE" (gdom/getElement "app"))
  (gdom/getElement "app")
  )

(defn- mount [component el]
  (reagent/render-component [component] el))

(defn- my-component []
  [:div
   [:h1 "Hello this is a test"]
   [ant/checkbox {:id "my-checkbox"} "This is a checkbox"]])

(defn add-test-div [name]
  (let [doc     js/document
        body    (.-body js/document)
        div     (.createElement doc "div")]
    (.appendChild body div)
    div))

(defn render-test-component [component div f]
  (reagent/render-component component div #(f component div)))

(defn remove-test-div [div]
  (reagent/unmount-component-at-node div)
  (reagent/flush)
  (.removeChild (.-body js/document) div))

(defn- with-mounted-component [component f]
  (let [div (add-test-div "app-test-div")]
    (render-test-component component div f)
    (remove-test-div div)))

(defn found-in [re div]
  (let [res (.-innerHTML div)]
    (if (re-find re res)
      true
      (do (println "Not found: " res)
          false))))

(deftest foo
  (with-mounted-component [my-component]
    (fn [comp div]
      (js/console.log div)
            (js/console.log (.-innerHTML div))
      ;; (js/console.log (.getElementById div "my-checkbox"))
      (is (= 2 (+ 1 1))))))

