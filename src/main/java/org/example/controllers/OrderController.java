package org.example.controllers;

import org.example.core.Template;
import org.example.models.Catalog;
import org.example.models.Company;
import org.example.models.Order;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class OrderController {
    /**
     * Cart (all articles to be paid)
     * @param request
     * @param response
     * @return Cart template
     */
    public String cart(Request request, Response response) {
        Map<String, Object> catalog = new HashMap<>();
        catalog.put("articles", Catalog.catalog);

        return Template.render("clientBasket.html", catalog);
    }

    /**
     * See one specific command
     * @param request
     * @param response
     * @return Template for article detail
     */
    public String detail(Request request, Response response) {
        String idParam = request.params(":id");
        int id = Integer.parseInt(idParam);

        Order selectedOrder = Company.getOrder(id);
        selectedOrder.setStatus("EN_PREPARATION");

        Map<String, Object> order = new HashMap<>();
        order.put("selectedOrder", selectedOrder);

        return Template.render("ordered.html", order);
    }
}
