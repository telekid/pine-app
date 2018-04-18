(ns pine-app.routes)

(def routes [{:route-id :home
              :test-path "/"}
             {:route-id :portfolio
              :test-path "/portfolio"
              :routes [{:route-id :portfolio-entry
                        :test-path ["/entry-" :id]
                        :routes [{:route-id :delete-portfolio-entry
                                  :test-path "/delete"}]}
                       {:route-id :about-portfolio
                        :test-path "/about"}]}
             {:route-id :blog
              :test-path "/blog"
              :routes [{:route-id :blog-entries
                        :test-path "/entries"}]}
             {:route-id :account
              :test-path "/account"}])

