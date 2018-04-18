(ns pine-app.views
  (:require [re-frame.core :as re-frame]
            [pine.core :as pine]
            [pine-app.subs :as subs]
            [pine-app.router :refer [path-for]]))

(defn active-route
  [{:keys [route-id active-class params]
    :as keys
    :or {params {}}}
   & children]
  (let [active-routes (re-frame/subscribe [::subs/active-routes])]
    [:a (-> keys
            (dissoc :route-id :active-class :params)
            (assoc :href (path-for route-id params))
            ((fn [ks]
               (if (contains? @active-routes route-id)
                 (update-in ks [:class-name] #(str % " " active-class))
                 ks))))
     children]))

(defn main-panel []
  (let [location (re-frame/subscribe [::subs/location])]
    [:div
     (into [:ul]
      (map
       (fn [[route-id title params]]
         [:li [active-route {:route-id route-id
                             :active-class "active"
                             :params params}
               title]])
       [[:home "Home"]
        [:portfolio "Portfolio"]
        [:portfolio-entry "Portfolio Entry" {:portfolio-entry {:id 123}}]
        [:delete-portfolio-entry "Delete Portfolio Entry" {:portfolio-entry {:id 123}}]
        [:about-portfolio "About Portfolio"]
        [:blog "Blog"]
        [:blog-entries "Blog Entries"]
        [:account "Account"]]))

     [:pre (str @location)]]))

