(defproject antizer-forms "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://antizer-forms.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.7.1"

  :dependencies [;; Core
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/clojurescript "1.10.339"]

                 ;; UI framework and components
                 [reagent "0.8.1"]
                 [antizer "0.3.1"]

                 ;; Routing
                 [clj-commons/secretary "1.2.4"]
                 [venantius/accountant "0.2.4"]

                 ;; Integration tests
                 [etaoin "0.3.2"]
                 [figwheel-sidecar "0.5.18"]
                 [com.bhauman/rebel-readline "0.1.4"]

                 ;; Server to host the (empty) back-end so integration tests can run
                 [compojure "1.6.1"]
                 [ring/ring-core "1.7.0"]
                 [ring/ring-jetty-adapter "1.7.0"]]

  :plugins [[lein-cljsbuild "1.1.7"]]

  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:output-to     "resources/dist/dev-main.js"
                                   :optimizations :whitespace
                                   :pretty-print  true}}]}

  :source-paths ["src"]

  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/cljs-out" "resources/dist"]

  :aliases {"fig"       ["trampoline" "run" "-m" "figwheel.main"]
            "fig:build" ["trampoline" "run" "-m" "figwheel.main" "-b" "dev" "-r"]
            "fig:min"   ["run" "-m" "figwheel.main" "-O" "advanced" "-bo" "dev"]
            "fig:test"  ["run" "-m" "figwheel.main" "-co" "test.cljs.edn" "-m" antizer-forms.test-runner]}

  :test-selectors {:default (complement :integration)
                   :integration :integration}
  
  :profiles {:dev {:dependencies [[com.bhauman/figwheel-main "0.2.0"]
                                  [com.bhauman/rebel-readline-cljs "0.1.4"]]}})
