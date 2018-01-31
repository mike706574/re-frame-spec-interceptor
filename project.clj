(defproject fun.mike/re-frame-spec-interceptor "0.0.2-SNAPSHOT"
  :description "A spec interceptor for re-frame."
  :url "http://github.com/mike706574/re-frame-spec-interceptor"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/spec.alpha "0.1.143"]]
  :profiles {:dev {:source-paths ["dev"]
                   :target-path "target/dev"
                   :dependencies [[org.clojure/clojure "1.9.0"]]}}
  :repositories [["releases" {:url "https://clojars.org/repo"
                              :creds :gpg}]])
