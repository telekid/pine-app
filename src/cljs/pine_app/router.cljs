(ns pine-app.router
  (:require [pine-app.routes :refer [routes]]
            [accountant.core :as accountant]
            [re-frame.core :refer [dispatch]]
            [pine.router :as pine]))

(pine/set-routes! routes)

(defn start []
  (accountant/configure-navigation!
   {:nav-handler #(dispatch [:handle-url-change %])
    :path-exists? #(boolean (pine/match-route %))})
  (accountant/dispatch-current!))


