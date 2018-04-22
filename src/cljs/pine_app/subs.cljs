(ns pine-app.subs
  (:require [re-frame.core :as re-frame]
            [pine-app.queries :as queries]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::location
 (fn [db]
   (queries/get-location db)))

(re-frame/reg-sub
 ::active-routes
 :<- [::location]
 (fn [location _]
   (:active location)))
