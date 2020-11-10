package org.example;

import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import org.example.models.Article;
import org.example.models.Customer;
import org.example.models.Order;
import spark.CustomErrorPages;
import spark.Spark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class App {
    public static void main(String[] args) {
        initialize();

        Customer c1 = new Customer("AIT MEDDOUR", "Latamen");
        Article a1 = new Article("Tomate rouge");
        a1.setPicture("https://www");
        Article a2 = new Article("Pomme verte");
        List<Article> l1 = new ArrayList<>();

        Order o1 = new Order(l1, c1);
        o1.addArticle(a1);
        o1.addArticle(a2);

        System.out.println(o1.getStatus());
        System.out.println(o1.getCustomer().getFirstName());

        for(Article article : o1.getArticles()) {
            System.out.println("Article : " + article.getName());
        }

        Spark.get("/", (req, res) -> {
            return Template.render("clientBasket.html", new HashMap<>());
        });
    }

    static void initialize() {
        Template.initialize();

        // Display exceptions in logs
        Spark.exception(Exception.class, (e, req, res) -> e.printStackTrace());

        // Serve static files (img/css/js)
        Spark.staticFiles.externalLocation(Conf.STATIC_DIR.getPath());

        // Configure server port
        Spark.port(Conf.HTTP_PORT);
        final LoggerMiddleware log = new LoggerMiddleware();
        Spark.before(log::process);
    }
}
