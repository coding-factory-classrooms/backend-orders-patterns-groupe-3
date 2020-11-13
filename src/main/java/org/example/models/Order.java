package org.example.models;

import org.example.models.memento.Originator;

import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private final Date creationDate;
    private final Date orderDate = null;
    private List<OrderLine> orderLines;
    private Customer customer;
    private String status;
    private String deliveryAddress = null;
    public double VATRate = 20;

    public Order(List<OrderLine> orderLines, Customer customer) {
        this.id = Company.orders.size() + 1;
        this.creationDate = new Date();
        this.orderLines = orderLines;
        this.customer = customer;
        this.status = OrderStatus.NOUVEAU.toString();
    }

    public int getId() {
        return id;
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
        this.orderLines.clear();
    }

    public void removeOrderLine(OrderLine a) {
        this.orderLines.remove(a);
    }

    public void addOrderLine(OrderLine a) {
        this.orderLines.add(a);
    }

    public List<OrderLine> getorderLines() {
        return this.orderLines;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public double getTotalHT() {
        double totalPrice = 0;

        for(OrderLine orderLine : this.getorderLines()) {
            totalPrice += orderLine.getArticle().getPrice() * orderLine.getQuantity();
        }

        return totalPrice;
    }

    public double getTotalTTC() {
        return getTotalHT() + (getTotalHT() * (VATRate / 100));
    }

    public String getState() {
        return status;
    }

    public Memento saveToMemento() {
        System.out.println("Originator: sauvegarde dans le memento.");
        return new Order.Memento(this.status);
    }


    public void restoreFromMemento(Memento memento) {
        this.status = memento.getSavedStatus();
        System.out.println("Originator: State after restoring from Memento: " + this.status);
    }

    public static class Memento {
        private final String status;

        public Memento(String statusToSave) {
            status = statusToSave;
        }

        // accessible by outer class only
        private String getSavedStatus() {
            return status;
        }
    }
}
