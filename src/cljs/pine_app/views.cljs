(ns pine-app.views
  (:require [re-frame.core :as re-frame]
            [pine.re-frame.components :refer [view active? link]]
            [pine-app.subs :as subs]
            [react :as react]))

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
  (let [location (re-frame/subscribe [:pine/location])]
    [:div
     (into [:ul]
      (map
       (fn [[route-id title params]]
         [:li [active? "active" [link {:route-id route-id :params params} title]]])
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
