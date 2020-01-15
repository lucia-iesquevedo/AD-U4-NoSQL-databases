package main;

import com.mongodb.BasicDBObject;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import model.Customer;
import model.CustomerDate;
import model.DatabaseSequence;
import model.Item;
import model.Purchase;
import org.bson.types.ObjectId;
import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.unwind;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.data.mongodb.core.query.Update;
import static org.springframework.data.mongodb.core.validation.Validator.criteria;

/**
 *
 * @author Lucia
 */
public class DAOCustomer {
    
    MongoTemplate mp=null;
    
    public DAOCustomer() {
}  
    private static long generateSequence(String seqName, MongoTemplate mongo) {
        DatabaseSequence counter = mongo.findAndModify(query(where("_id").is(seqName)),
        new Update().inc("seq", 1), options().returnNew(true).upsert(true),DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 5;
    }
    
    public void add(Customer customer) {       
        mp = MongoConnection.getInstance().getTemplate();
        customer.setIdCustomer(generateSequence(Customer.SEQUENCE_NAME, mp));
        if (customer.getPurchases().isEmpty())
            mp.insert(customer);
        else
            System.out.println("Customer with purchases: Not inserted");
            //Add purchases with method addPurchases, for Item registration
    }
    
    public void addPurchase(Customer customer, Purchase purchase) {       
        mp = MongoConnection.getInstance().getTemplate();
        customer.getPurchases().add(purchase);
        mp.save(customer);
//        DAOItem daoItem = new DAOItem();
        //Item should be also UPDATED at service level
    }
    
    public void update(Customer customer) {
        mp = MongoConnection.getInstance().getTemplate();
        mp.save(customer);
    }
    
       
    public void delete(Customer customer) {
        mp = MongoConnection.getInstance().getTemplate();
        //Check if the customer has any purchases. Checking the DB, because the object may have been modified
        Customer cust = mp.findOne(query(where("_id").is(customer.getIdCustomer()).and("purchases").size(0)), Customer.class);
        if (cust != null)
            mp.remove(customer);
        else
            System.out.println("Customer not deleted "+customer);
    }
    
    public void deletePurchase(Customer customer, Purchase purchase) {
        mp = MongoConnection.getInstance().getTemplate();
        Update update = new Update();
        //update.pull("purchases", query(where("iditem").is(purchase.getIditem()).and("date").is(purchase.getDate())));
        update.pull("purchases",purchase);
        mp.updateMulti(query(where("_id").is(customer.getIdCustomer())), update, Customer.class);  
    }
    
    public void deletePurchases(ObjectId idItem) {
        //Borra las compras de ese item para cualquier customer
        mp = MongoConnection.getInstance().getTemplate();
        Update update = new Update();
        update.pull("purchases", query(where("iditem").is(idItem)));
        mp.updateMulti(new Query(), update, Customer.class);  
    }
    public void getAll() {
        mp = MongoConnection.getInstance().getTemplate();
       List<Customer> list = mp.findAll(Customer.class);
       list.forEach(System.out::println);
   }
    
    public Customer getByName(String name) {
        mp = MongoConnection.getInstance().getTemplate();
        Customer customer = mp.findOne(query(where("name").is(name)), Customer.class);
        System.out.println("The customer with name "+name+" is: "+customer);
        return customer;
    }
    
    public Customer getByDate(LocalDate date) {
        mp = MongoConnection.getInstance().getTemplate();
        
       // Aggregation ag= newAggregation(match(Criteria.where("purchases.date").is(date)),project("purchases"));
        //Query query = new Query();
        Aggregation ag = newAggregation(unwind("purchases"), match(where("purchases.date").is(date)),
        project("purchases","name"));
        AggregationResults<BasicDBObject> groupResults 
			= mp.aggregate(ag, Customer.class, BasicDBObject.class);
		List<BasicDBObject> result = groupResults.getMappedResults();
        

        Customer customer = mp.findOne(query(where("purchases.date").is(date)), Customer.class);
        System.out.println("The customer with date "+date+" is: "+result);
        return customer;
    }

}
    
