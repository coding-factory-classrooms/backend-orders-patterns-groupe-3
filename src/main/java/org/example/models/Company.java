package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Company {
    public static List<Customer> customers = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public static Order getOrder(int id) {
        Order order = null;
        for(Order o : orders) {
            if(o.getId() == id) {
                order = o;
            }
        }
        return order;
    }
}
