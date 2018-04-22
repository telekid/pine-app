(ns pine-app.router
  (:require [pine-app.routes :refer [routes]]
            [accountant.core :as accountant]
            [re-frame.core :refer [dispatch]]
            [pine.core :as pine]))

(defn start []
  (accountant/configure-navigation!
   {:nav-handler #(dispatch [:handle-url-change %])
    :path-exists? #(boolean (pine/match-route % routes))})
  (accountant/dispatch-current!))

(defn path-for [route params] (pine/path-for route params routes))
