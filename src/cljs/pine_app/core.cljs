(ns pine-app.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [pine-app.events :as events]
            [pine-app.views :as views]
            [pine-app.config :as config]
            [pine-app.router :as router]))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (router/start)
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
