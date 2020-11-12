package org.example;

import org.apache.velocity.app.Velocity;
import org.example.controllers.OrderController;
import org.example.core.Conf;
import org.example.core.Template;
import org.example.middlewares.LoggerMiddleware;
import org.example.models.*;
import org.example.models.memento.Caretaker;
import org.example.models.memento.Originator;
import spark.CustomErrorPages;
import spark.Spark;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        initialize();

        // test classes
        test_classes();

        OrderController orderController = new OrderController();

        // Cart
        Spark.get("/cart", (req, res) -> {
            return orderController.cart(req, res);
        });

        // Detail for one specific command
        Spark.get("/order/:id", (req, res) -> {
            return orderController.detail(req, res);
        });

        Spark.get("/orders", (req, res) -> {
            Map<String, Object> orders = new HashMap<>();
            orders.put("orders", Company.orders);
            orders.put("statusesList", OrderStatus.values());

            return Template.render("saleList.html", orders);
        });

        Spark.get("/shop", (req, res) -> {
            Map<String, Object> catalog = new HashMap<>();
            catalog.put("articles", Catalog.catalog);

            return Template.render("shop.html", catalog);
        });

        Spark.get("/", (req, res) -> {
            return Template.render("clientBasket.html", new HashMap<>());
        });
    }

    static void test_classes() {
        Caretaker caretaker = new Caretaker();

        Originator originator = new Originator();

        Customer c1 = new Customer("AIT MEDDOUR", "Latamen");
        Customer c2 = new Customer("FENEA", "Robin");
        Customer c3 = new Customer("DAGDELEN", "Enes");
        Customer c4 = new Customer("MILLET", "Thomas");

        // all customers about our company
        Company.customers.add(c1);
        Company.customers.add(c2);
        Company.customers.add(c3);
        Company.customers.add(c4);

        // 1st article
        Article a1 = new Article("Tomate rouge");
        a1.setPrice(10);
        a1.setDescription("La tomate est une plante herbacée annuelle à port rampant, aux tiges ramifiées. " +
                "Il existe trois ports : retombant, semi retombant et horizontal. " +
                "De nos jours, il est difficile de déterminer la taille de la tomate puisqu'on utilise exclusivement des hybrides à croissance indéterminée. " +
                "Il est nécessaire de les palisser car la tige est très peu ligneuse et a une section creuse. " +
                "Pour palisser, on entoure un lien autour de la tige, lien que l'on accroche à un support ou à une bobine reliée à la charpente de la serre."
        );
        a1.setPicture("https://upload.wikimedia.org/wikipedia/commons/2/26/Tomato_on_its_stem.jpg");

        // 2nd article
        Article a2 = new Article("Pomme verte");
        a2.setPicture("https://www.e-liquide.com/1014-large_default/arome-pomme-verte.jpg");
        a2.setDescription("Les Granny Smith sont des pommes de calibre moyen, récoltées avant la pleine maturité quand elles sont encore de couleur « vert pomme ». " +
                "Certaines ayant bénéficié d'un temps de maturation plus long, ont des reflets roses."
        );
        a2.setPrice(6);

        // create local catalog
        Catalog.catalog.add(a1);
        Catalog.catalog.add(a2);

        // Order list
        List<OrderLine> l1 = new ArrayList<>();
        List<OrderLine> l2 = new ArrayList<>();

        OrderLine o1 = new OrderLine(a1, 2);
        OrderLine o2 = new OrderLine(a2, 3);

        OrderLine o3 = new OrderLine(a1, 5);
        OrderLine o4 = new OrderLine(a2, 5);

        l1.add(o1);
        l1.add(o2);
        l2.add(o3);
        l2.add(o4);

        // First tests
        Order cart = new Order(l1, c1);

        // all orders of our company
        Company.orders.add(cart);

        Order order2 = new Order(l2, c2);
        Company.orders.add(order2);


        originator.set(cart);
        cart.setStatus(OrderStatus.EN_ATTENTE_DU_LIVREUR.toString());

        originator.set(cart);
        caretaker.addMemento(originator.saveToMemento());

        cart.setStatus(OrderStatus.EN_PREPARATION.toString());
        caretaker.addMemento( originator.saveToMemento());

        cart.setStatus(OrderStatus.EN_LIVRAISON.toString());
        originator.set(cart);

        Order retrievedCart = (Order) originator.restoreFromMemento(caretaker.getMemento(1));
        if(retrievedCart != null) {
            System.out.println(retrievedCart.getStatus());
        }

        System.out.println("nombre d items enregistres : " + caretaker.getSavedStates().size() + " ");

        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
                DateFormat.SHORT,
                DateFormat.SHORT);

        // show command status and customer
        System.out.println("Statut de la commande : " + cart.getStatus() + " du " + shortDateFormat.format(cart.getCreationDate()));
        System.out.println("Client : " + cart.getCustomer().getFirstName() + " " + cart.getCustomer().getLastName());

        // Loop order lines in current cart
        for(OrderLine orderLine : cart.getorderLines()) {
            System.out.println("Article : " + orderLine.getArticle().getName());
            System.out.println("Quantité : " + orderLine.getQuantity());
        }

        System.out.println("\n");
        System.out.println("Total HT : " + cart.getTotalHT());
        System.out.println("Total TTC : " + cart.getTotalTTC());

        System.out.println("\n\n");
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
