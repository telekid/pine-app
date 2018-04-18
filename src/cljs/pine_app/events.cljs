(ns pine-app.events
  (:require [re-frame.core :as re-frame]
            [pine-app.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 :navigate
 (fn [db [_ result]]
   (assoc db :location result)))
