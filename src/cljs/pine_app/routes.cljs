(ns pine-app.routes)

(def routes [{:route-id :port
              :test-path "/port"}
             {:route-id :portfolioabc
              :test-path "/portfolioabc"}
             {:route-id :portfolio
              :test-path "/portfolio"
              :routes [{:route-id :view
                        :test-path ["/view-" :id]
                        :routes [{:route-id :page
                                  :test-path "/page"}]}
                       {:route-id :about-portfolio
                        :test-path "/about"}]}
             {:route-id :home
              :test-path "/home"}
             {:route-id :portfolioo
              :test-path "/portfolioo"}
             {:route-id :port2
              :test-path "/port"}])

