(ns fun.mike.re-frame.spec-interceptor
  (:require [clojure.spec.alpha :as s]))

(defn spec-interceptor
  "Returns an interceptor that validates the app state against `spec` after an event.

   When the app state is invalid, the app state is updated to the return value of the given `failure-handler`,

  `failure-handler` is a function that take the invalid app state, the event, and the result of `clojure.spec.alpha/explain-data`."
  [spec failure-handler]
  {:id :spec
   :before nil
   :after (fn spec-validation
            [context]
            (let [event (get-in context [:coeffects :event])
                  db (or (get-in context [:effects :db])
                         (get-in context [:coeffects :db]))]
              (if (s/valid? spec db)
                context
                (->> (failure-handler db event (s/explain-data spec db))
                     (assoc-in context [:effects :db])))))})
