# re-frame-spec-interceptor

[![Clojars Project](https://img.shields.io/clojars/v/fun.mike/re-frame-spec-interceptor.svg)](https://clojars.org/fun.mike/re-frame-spec-interceptor)

A re-frame spec interceptor.

## Example


```clojure
(require [fun.mike.re-frame.spec-interceptor :refer [spec-interceptor]])

;; Write a handler for app state validation error
(defn handle-invalid-db
  [db event data]
  (merge db {:status :error
             :data data
             :event :event}))

;; Create a spec for app state
(s/def :example/status #{:ok :error})
(s/def :example/text string?)
(s/def :example/db (s/keys :req-un [:example/status :example/text])

;; Build the interceptor
(def db-interceptor (spec-interceptor :example/db handle-invalid-db))

;; Use the interceptor when registering event handlers
(reg-event-db
 :set-text
 [db-interceptor]
 (fn [db [_ text]]
   (assoc db :text text)))
```

## Build

[![CircleCI](https://circleci.com/gh/mike706574/re-frame-spec-interceptor.svg?style=svg)](https://circleci.com/gh/mike706574/re-frame-spec-interceptor)

## Copyright and License

The use and distribution terms for this software are covered by the
[Eclipse Public License 1.0] which can be found in the file
epl-v10.html at the root of this distribution. By using this softwaer
in any fashion, you are agreeing to be bound by the terms of this
license. You must not remove this notice, or any other, from this
software.

[Eclipse Public License 1.0]: http://opensource.org/licenses/eclipse-1.0.php
