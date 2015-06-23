(ns serpinskis.core
  (:require [quil.core :as q]
            ))

(defn serp-triangle
  ([]
   (serp-triangle [{:x  0   :y 1
                    :x1 0.5  :y1 0
                    :x2 1 :y2 1}]))
  ([parent]
   (mapcat (fn [tri] [{:x (:x tri) :y (:y tri)
                      :x1 (* 1/2 (+ (:x tri) (:x1 tri))) :y1 (* 1/2 (+ (:y tri) (:y1 tri)))
                      :x2 (* 1/2 (+ (:x tri) (:x2 tri))) :y2 (:y tri)}
                     {:x (* 1/2 (+ (:x tri) (:x1 tri))) :y (* 1/2 (+ (:y tri) (:y1 tri)))
                      :x1 (:x1 tri) :y1 (:y1 tri)
                      :x2 (* 1/2 (+ (:x1 tri) (:x2 tri))) :y2 (* 1/2 (+ (:y1 tri) (:y2 tri)))}
                     {:x (* 1/2 (+ (:x tri) (:x2 tri))) :y (:y tri)
                      :x1 (* 1/2 (+ (:x1 tri) (:x2 tri))) :y1 (* 1/2 (+ (:y1 tri) (:y2 tri)))
                      :x2 (:x2 tri) :y2 (:y2 tri)}])
           parent)))

(defn scale-triangle [width height triangle]
  {:x (int (* width (:x triangle))) :y (int (* height (:y triangle)))
   :x1 (int (* width (:x1 triangle))) :y1 (int (* height (:y1 triangle)))
   :x2 (int (* width (:x2 triangle))) :y2 (int (* height (:y2 triangle)))})

(defn apply-tri [fn tri]
  (fn (:x tri) (:y tri)
    (:x1 tri) (:y1 tri)
    (:x2 tri) (:y2 tri)))

(defn draw-state []  
  (doseq [tri (serp-triangle (serp-triangle (serp-triangle (serp-triangle (serp-triangle)))))]
    (apply-tri q/triangle (scale-triangle (q/width) (q/height) tri))))

(q/defsketch serpinskis
  :title "Serpinski triangle"
  :size [800 600]
  ; setup function called only once, during sketch initialization.
  :setup (fn []
           (q/background 200))
  :draw draw-state
  )
