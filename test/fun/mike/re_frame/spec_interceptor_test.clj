(ns fun.mike.re-frame.spec-interceptor-test
  (:require [clojure.spec.alpha :as s]
            [clojure.test :refer [deftest is]]
            [fun.mike.re-frame.spec-interceptor :refer [spec-interceptor]]))

(s/def ::counter (s/or :zero zero? :positive pos?))
(s/def ::error string?)

(s/def ::db (s/keys :req [::counter]
                    :opt [::error]))

(defn failure-handler
  [db event data]
  (merge db {::data data
             ::event event
             ::error "Failure!"
             ::counter 0}))

(def interceptor (spec-interceptor ::db failure-handler))

(deftest ok-from-fx
  (let [after (:after interceptor)
        db {::counter 0}
        context {:coeffects {:event [:next]}
                 :effects {:db db}}
        context* (after context)
        db* (get-in context* [:effects :db])]
    (is (= db db*))))

(deftest ok-from-cofx
  (let [after (:after interceptor)
        db {::counter 0}
        context {:coeffects {:event [:next]
                             :db db}}
        context* (after context)
        fx-db* (get-in context* [:effects :db])
        cofx-db* (get-in context* [:coeffects :db])]
    (is (nil? fx-db*))
    (is (= db cofx-db*))))

(deftest invalid-from-fx
  (let [after (:after interceptor)
        db {::counter -5}
        context {:coeffects {:event [:next]}
                 :effects {:db db}}
        context* (after context)
        db* (get-in context* [:effects :db])
        {:keys [::data ::event ::error ::counter]} db*]
    (is (= 0 counter))
    (is "Failure!" error)
    (is (= [:next] event))
    (is (map? data))))

(deftest invalid-from-cofx
  (let [after (:after interceptor)
        db {::counter -5}
        context {:coeffects {:event [:next]
                             :db db}}
        context* (after context)
        fx-db* (get-in context* [:effects :db])
        cofx-db* (get-in context* [:coeffects :db])
        {:keys [::data ::event ::error ::counter]} fx-db*]
    (is (= db cofx-db*))
    (is (= 0 counter))
    (is "Failure!" error)
    (is (= [:next] event))
    (is (map? data))))
