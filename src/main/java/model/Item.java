/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Set;
import org.bson.types.ObjectId;

public class Item implements Serializable {
    private ObjectId idItem;
    private String name;
    private String company;
    private double price;
    private Set<Integer> customers;

    
    
    public Item() {
    }

    public Item(ObjectId idItem, String name, String company, double price) {
        this.idItem = idItem;
        this.name = name;
        this.company = company;
        this.price = price;
    }

    public Item(String name, String company, double price) {
        this.name = name;
        this.company = company;
        this.price = price;
    }

    public ObjectId getIdItem() {
        return idItem;
    }

    public void setIdItem(ObjectId idItem) {
        this.idItem = idItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<Integer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Integer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "Item{" + "idItem=" + idItem + ", name=" + name + ", company=" + company + ", price=" + price + ", customers=" + customers + '}';
    }

    
    
    
    public String toStringTextFile() {
        return idItem + ";" + name + ";" + company + ";" + price;
    }

    public String toStringVisual() {
        return "ID: " + idItem + "  Name: " + name + "  Company: " + company + " Price: " + price;
    }

}
