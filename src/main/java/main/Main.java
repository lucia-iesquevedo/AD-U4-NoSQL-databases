/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import model.Customer;
import model.Item;
import model.Purchase;
import model.TipoUsuario;

public class Main {

    public static void main(String[] args) {
        //Set log
        System.setProperty("log4j.configurationFile", "log4j2.xml");
       

        //DAOItem tests
       Item item = new Item("HardDisk", "ACME", 45.5);
       Item item2= new Item("Mouse", "Softonic", 4.5);
//        DAOItem daoItem = new DAOItem();
//        daoItem.add(item);
//        daoItem.add(item2);
//        daoItem.addCustomer(item, 1);
//        daoItem.addCustomer(item, 1);
//        daoItem.addCustomer(item, 2);
//        daoItem.getAll();
//        daoItem.deleteCustomer(item, 1);
//        daoItem.getAll();
//        item.setName("Monitor");
//        daoItem.update(item);
//        daoItem.getAll();

//        
//        //DAOCustomer
        DAOCustomer daoCustomer=new DAOCustomer();
        
        Customer c = new Customer(0, "jj", "kk", "llll","a,2,","u",TipoUsuario.USUARIO);
//        Customer c2 = new Customer(0, "pepe", "kk", "llll","a,2,","u",TipoUsuario.USUARIO);
        daoCustomer.add(c);
        Purchase p = new Purchase(item.getIdItem(),item.getName(), LocalDateTime.of(2001,1,1,0,0));
        Purchase p2 = new Purchase(item2.getIdItem(),item2.getName(), LocalDateTime.now());
        Purchase p3 = new Purchase(item.getIdItem(),item.getName(), LocalDateTime.of(2001,1,1,0,0));
        
        daoCustomer.addPurchase(c, p);
        daoCustomer.addPurchase(c, p2);
        daoCustomer.addPurchase(c, p3);
        
        daoCustomer.getAll();
        daoCustomer.getByDate(LocalDate.of(2001,1,1));
//        daoCustomer.add(c2);
//        daoCustomer.getAll();
//        
//        daoCustomer.addPurchase(c, p);
//        daoItem.addCustomer(item, c.getIdCustomer());
//        daoCustomer.addPurchase(c, p2);
//        daoItem.addCustomer(item2, c.getIdCustomer());
//        daoCustomer.addPurchase(c2, p2);
//        daoItem.addCustomer(item2, c2.getIdCustomer());
//        //daoCustomer.delete(c);
//        daoCustomer.getAll();
//        daoItem.getAll();
        
        //daoCustomer.deletePurchase(c, p2);
        //daoCustomer.getAll();
//        daoCustomer.deletePurchases(p2.getIditem());
//        daoCustomer.getAll();
        //Check if the customer has more purchases of that item. If not, removeCustomer form Item
//      

//        daoCustomer.getByName(c.getName());
//        
//        c.setName("Pepe");
//        daoCustomer.update(c);
//        System.out.println("Customer actualizado: "+c);
//        
//        daoCustomer.delete(c);
////  
//        AQUI
//

//        c = mp.findOne(query(where("idCustomer").is(2)), Customer.class);
//        c.getPurchases().add(new Purchase(1,1,LocalDate.of(2000,1,1)));
//        mp.save(c);
//
//       lista = mp.findAll(Customer.class);
//
//        lista.forEach(System.out::println);


//        MongoRepository<Customer,String> repository = new SimpleMongoRepository<Customer,String>();
//        MongoCollection<Document> coll = db.getCollection("test");
//
//        getAllDocuments(coll);
//
//        db.listCollectionNames().forEach((Consumer<String>) System.out::println);

    }

}
