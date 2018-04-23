(ns pine-app.subs
  (:require [re-frame.core :as re-frame]
            [pine-app.queries :as queries]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))
