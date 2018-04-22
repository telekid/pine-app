(ns pine-app.events
  (:require [re-frame.core :as re-frame]
            [clojure.data :as data]
            [pine-app.db :as db]
            [pine.core :as pine]
            [accountant.core :as accountant]
            [pine-app.routes :refer [routes]]
            [pine-app.queries :as queries]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
 :navigate
 (fn [_ [_ route-id params]]
   {:update-url (pine/path-for route-id params routes)
    :dispatch [:handle-navigation]}))

(re-frame/reg-fx
 :update-url
 (fn [url]
   (accountant/navigate! url)))

(re-frame/reg-event-fx
 :handle-url-change
 (fn [_ [_ url]]
   {:dispatch [:handle-navigation (pine/match-route url routes)]}))

(re-frame/reg-event-fx
 :handle-navigation
 (fn [cofx [_ next-location]]
   (let [location (queries/get-location (:db cofx))
         [leaving entering retained] (data/diff (:active location) (:active next-location))
         effects (apply concat (list (map #(vector :leave %) leaving)
                                     (map #(vector :enter %) entering)
                                     (map #(vector :retain %) retained)))]
     {:db {:location next-location}
      :dispatch-n effects})))

(re-frame/reg-event-fx
 :enter
 (fn [_ [_ value]] (print "Entering: " value)))

(re-frame/reg-event-fx
 :retain
 (fn [_ [_ value]] (print "Retaining: " value)))

(re-frame/reg-event-fx
 :leave
 (fn [_ [_ value]] (print "Leaving: " value)))
