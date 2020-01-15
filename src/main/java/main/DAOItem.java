package main;

import com.mongodb.MongoClient;
import com.mongodb.MongoCommandException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;
import static com.mongodb.client.model.Filters.nin;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.Item;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lucia
 */
public class DAOItem {
    
    MongoDatabase mdb=null;
    MongoCollection<Document> col=null;
    
    public DAOItem() {
//    try {
//            mdb = MongoConnection.getInstance().getDatabase();
//            mdb.createCollection("items");
//        } catch (MongoCommandException e) {
//            LoggerFactory.getLogger(Main.class.getName()).info("Mongo items collection exists");
//        }
}  
    
    public void add(Item item) {
        mdb = MongoConnection.getInstance().getDatabase();
        col = mdb.getCollection("items");
        
        Document d = new Document();
	    d.put("name", item.getName());
        d.put("company",item.getCompany());
        d.put("price",item.getPrice());
	
        col.insertOne(d); 
        item.setIdItem((ObjectId)d.get( "_id" ));
    }
    
    public void update(Item item) {
        mdb = MongoConnection.getInstance().getDatabase();
        col = mdb.getCollection("items");
        
        //col.updateOne(eq("i", 10), new Document("$set", new Document("i", 110)));
        Document search = new Document();
        search.put("_id", item.getIdItem());
        Document newData = new Document();
        newData.put("name", item.getName());
        newData.put("company", item.getCompany());
        newData.put("price", item.getPrice());
        Document update = new Document();
        update.put("$set", newData);
        col.updateOne(search, update);        
    }
    
    public void addCustomer(Item item, long idCustomer) {
        mdb = MongoConnection.getInstance().getDatabase();
        col = mdb.getCollection("items");
        
        //Document doc = col.find(and(eq("_id", item.getIdItem()),nin("customers",idCustomer))).first();
        //Check that the customer is not already into the array
        col.updateOne(and(eq("_id", item.getIdItem()),nin("customers",idCustomer)), new Document("$push", new Document("customers", idCustomer)));

//        Document search = new Document();
//        search.put("_id", item.getIdItem());
//        Document newData = new Document();
//        newData.put("customers", idCustomer);
//
//        Document update = new Document();
//        update.put("$push", newData);
//
//        col.updateOne(search, update);
    }
    
    public void deleteCustomer(Item item, long idCustomer) {
        mdb = MongoConnection.getInstance().getDatabase();
        col = mdb.getCollection("items");
        
        col.updateOne(and(eq("_id", item.getIdItem())), new Document("$pull", new Document("customers", idCustomer)));
    }

    
    public void delete(Item item) {
        mdb = MongoConnection.getInstance().getDatabase();
        col = mdb.getCollection("items");
        // Check if the item has been bought by ant customer
        Document doc = col.find(and(eq("_id", item.getIdItem()),exists("customers"))).first();
        
        if (doc != null) {
            System.out.println("The item has customers");
            //Ask for confirmation. If yes
            //Delete purchases in customer
            // col.deleteOne(eq("_id", item.getIdItem()));
            //if no, show message No deletion
        }
        else  //Item has no purchases
            col.deleteOne(eq("_id", item.getIdItem()));
    }
    
    public void getAll() {
        List<Item> listItems = new ArrayList();
        mdb = MongoConnection.getInstance().getDatabase();
        col = mdb.getCollection("items");
        FindIterable<Document> fi = col.find();
        MongoCursor<Document> cursor = fi.iterator();

        try {
          while (cursor.hasNext()) {

                Document doc = cursor.next();
                //Print on screen
                //System.out.println(doc.toJson());

                //Save into an object
                Item item= new Item();

                item.setIdItem((ObjectId)doc.get("_id"));
                item.setName((String)doc.get("name"));
                item.setCompany((String)doc.get("company"));
                item.setPrice((double)doc.get("price"));

                // Retrieve elements from the array Customers
                if (null != doc.get("customers")) {
                    ArrayList<Integer> sublistCustomers = (ArrayList<Integer>)doc.get("customers");
                    Set<Integer> setCust = new HashSet<Integer>(sublistCustomers);
                    item.setCustomers(setCust);                    
                }
                listItems.add(item);
                System.out.println(item);
          }
        } finally {
                cursor.close();
        }
        System.out.println("Number of items: "+listItems.size());
   }
    
    public Item getByName(String name) {
        mdb = MongoConnection.getInstance().getDatabase();
        col = mdb.getCollection("items");
        Document doc = col.find(eq("name", name)).first();
        Item item= new Item();
        item.setIdItem((ObjectId)doc.get("_id"));
        item.setName((String)doc.get("name"));
        item.setCompany((String)doc.get("company"));
        item.setPrice((double)doc.get("price"));                
        // Retrieve elements from the array Customers
        if (null != doc.get("customers")) {
            ArrayList<Integer> sublistCustomers = (ArrayList<Integer>)doc.get("customers");
            Set<Integer> setCust = new HashSet<Integer>(sublistCustomers);
            item.setCustomers(setCust);
        }
        return item;
    }
}
    
