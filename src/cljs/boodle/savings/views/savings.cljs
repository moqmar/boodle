(ns boodle.savings.views.savings
  (:require [boodle.common :as common]
            [boodle.i18n :refer [translate]]
            [re-frame.core :as rf]))

(defn total
  []
  (fn []
    (let [rows @(rf/subscribe [:savings])
          total (:total rows)]
      (str (common/format-number total) (translate :it :currency)))))

(defn render-row
  [row]
  (when-let [amount (:amount row)]
    (let [amount-str (common/format-neg-or-pos amount)
          td-color (if (pos? amount) :td.has-text-success :td.has-text-danger)]
      [:tr {:key (random-uuid)}
       [:td (:date row)]
       [:td (:item row)]
       [td-color
        (str amount-str (translate :it :currency))]])))

(defn table
  []
  (fn []
    (let [rows @(rf/subscribe [:savings])
          savings (:savings rows)]
      [:table.table.is-striped.is-fullwidth
       [:thead
        [:tr
         [:th (translate :it :savings/table.date)]
         [:th (translate :it :savings/table.item)]
         [:th (translate :it :savings/table.amount)]]]
       [:tbody
        (doall (map render-row savings))]])))

(defn buttons
  []
  [:div.field.is-grouped.is-grouped-centered
   [:p.control
    [:button.button.is-primary
     {:on-click #(rf/dispatch [:add-saving])}
     (translate :it :savings/button.add)]]])
