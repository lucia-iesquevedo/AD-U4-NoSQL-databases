package main;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.core.MongoTemplate;


public class MongoConnection {

        public static MongoConnection mongoConnection=null;
        public MongoClient mongoClient=null;
	
	
	private MongoConnection() {
                mongoClient = new MongoClient(new MongoClientURI("mongodb+srv://root:root@dataaccess-mxsl7.mongodb.net/webstoredb?retryWrites=true"));
                //mongoClient = new MongoClient( "localhost" , 27017 );
	}

        public static MongoConnection getInstance() {
        if (mongoConnection == null) {
            mongoConnection = new MongoConnection();
        }

        return mongoConnection;
    }
	

	public void closeConnection() {
		System.out.println("Releasing all open resources ...");
                mongoClient.close();
	}
	
        
        public MongoClient getConnection() {
              return mongoClient;
    }
        public MongoDatabase getDatabase() {
            return mongoClient.getDatabase("webstoredb");
        }
        public MongoTemplate getTemplate() {
            MongoTemplate mp = new MongoTemplate(mongoClient, "webstoredb");
            return mp;
        }
}


