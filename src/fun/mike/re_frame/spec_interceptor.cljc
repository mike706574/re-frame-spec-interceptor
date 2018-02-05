(ns fun.mike.re-frame.spec-interceptor
  (:require [clojure.spec.alpha :as s]))

(defn spec-interceptor
  [spec f]
  {:id :spec
   :before nil
   :after (fn spec-validation
            [context]
            (let [event (get-in context [:coeffects :event])
                  db (or (get-in context [:effects :db])
                         (get-in context [:coeffects :db]))]
              (if (s/valid? spec db)
                context
                (->> (f db event (s/explain-data spec db))
                     (assoc-in context [:effects :db])))))})
