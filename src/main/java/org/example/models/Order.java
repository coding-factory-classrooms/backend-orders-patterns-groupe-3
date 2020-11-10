package org.example.models;

import java.util.Date;
import java.util.List;

public class Order {
    private final Date creationDate;
    private final Date orderDate = null;
    private List<Article> articles;
    private Customer customer;
    private String status;
    private String deliveryAddress = null;

    public Order(List<Article> articles, Customer customer) {
        this.creationDate = new Date();
        this.articles = articles;
        this.customer = customer;
        this.status = OrderStatus.EN_PREPARATION.toString();
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void emptyCart() {
        this.articles.clear();
    }

    public void removeArticle(Article a) {
        this.articles.remove(a);
    }

    public void addArticle(Article a) {
        this.articles.add(a);
    }

    public List<Article> getArticles() {
        return this.articles;
    }
}
