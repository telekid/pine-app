(ns pine-app.views
  (:require [re-frame.core :as re-frame]
            [pine.core :as pine]
            [pine-app.subs :as subs]
            [pine-app.router :refer [path-for]]))

(declare view)

;; TODO: Move to pine
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

(defn button [click-handler label]
  [:button {:on-click click-handler} label])

(defn home-page [] [:h1 "Home"])

(defn portfolio-entry []
  [:h2 "Portfolio entry"])

(defn delete-portfolio-entry []
  [:h2 "Delete portfolio-entry"
    [button #(re-frame/dispatch [:navigate :portfolio]) "Delete"]])

(defn about-portfolio []
  [:h2 "About portfolio"])

(defn modal [& children]
  (print "children" children)
  (when (not (empty? (remove nil? children)))
    [:div [:h1 "Modal Container"]
     (into [:div] children)]))

(defn portfolio []
  [:div
   [:h1 "Portfolio"]
   [view :portfolio-entry [portfolio-entry]]
   [view :about-portfolio [about-portfolio]]])

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

     [:pre (str @location)]
     [view :home [home-page]]
     [view :portfolio [portfolio]]
     [modal [view :delete-portfolio-entry [delete-portfolio-entry]]]]))

;; TODO create apply-component for modals et. al
;; e.g. (apply-component modal)

;; TODO: Move to pine
(defn view [route-id & children]
  (when (contains? @(re-frame/subscribe [::subs/active-routes]) route-id)
    (into [:div] children)))
