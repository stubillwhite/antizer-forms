(ns antizer-forms.test-runner
  (:require antizer-forms.pages.coffee-test
            [figwheel.main.testing :refer [run-tests-async]]))

(defn -main [& args]
  (run-tests-async 5000))
