(ns antizer-forms.page
  (:require [antizer.reagent :as ant]
            [antizer-forms.form-controls :as fc]
            [antizer-forms.state :refer [state update-state!]]
            [reagent.core :as reagent]))

(defn- submit-form-if-valid
  [errors values]
  (js/console.log "Errors:" errors)
  (js/console.log "Values:" (js->clj values :keywordize-keys true))
  (when (nil? errors)
    (ant/notification-open 
     {:message     "Submitting values"
      :description (pr-str (js->clj values))})))

(defn- validate-and-submit [form]
  (js/console.log "Validating form")
  (ant/validate-fields form submit-form-if-valid))

(defn- log-value [x]
  (js/console.log x))

(def checkbox-group-values ["charlie" "william" "two" "george" "harry" "queen"])

(defn basic-form []
  (fn [props]
    (let [form (ant/get-form)]
      [ant/form {:layout "horizontal"}
       (fc/form-input form "name" "Name" :on-change log-value)
       (fc/form-checkbox form "checkbox" "Basic checkbox" :on-change log-value)
       (fc/form-checkbox-group form "checkbox-group-one" checkbox-group-values :on-change log-value)
       (fc/form-checkbox-group form "checkbox-group-two" checkbox-group-values :on-change log-value)
       (fc/form-button form "Submit" validate-and-submit)])))

(defonce form-state (reagent/atom {:checkbox true
                                   :checkbox-group-one []
                                   :checkbox-group-two ["harry"]
                                   :email              "default-email"
                                   :name               ""}))

(defn basic-form-with-state []
  (let [state      form-state
        set-value! (fn [& ks] #(do (swap! state assoc-in ks %) (js/console.log "Updated:" @state)))
        get-value  (fn [& ks] (get-in @state ks))]
    (fn [props]
      (let [form (ant/get-form)]
        [ant/form {:layout "horizontal"}
         (fc/form-input form "name" "Name" :initial-value (get-value :name) :on-change (set-value! :name))
         (fc/form-checkbox form "checkbox" "Basic checkbox")
         (fc/form-checkbox-group form "checkbox-group-one" checkbox-group-values :on-change (set-value! :checkbox-group-one) :initial-value (get-value :checkbox-group-one))
         (fc/form-checkbox-group form "checkbox-group-two" checkbox-group-values :on-change (set-value! :checkbox-group-two) :initial-value (get-value :checkbox-group-two))
         (fc/form-button form "Submit" validate-and-submit)]))))

(defn view [params]
  [:div {:style {:padding "5px"}}
   [:h1 "Antizer form controls"]
   [:div
    [:hr]
    [:p "Basic form logging on change"]
    [ant/create-form (basic-form)]]
   [:div
    [:hr]
    [:p "Basic form with state"]
    [ant/create-form (basic-form-with-state)]]])
