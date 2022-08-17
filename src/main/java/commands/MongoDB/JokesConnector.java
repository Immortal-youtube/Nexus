package commands.MongoDB;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Random;

public class JokesConnector {

    public static String joke() {
        Random random = new Random();
        MongoClientURI clientURI = new MongoClientURI(System.getenv("MONGO_URL"));
        MongoClient mongoClient = new MongoClient(clientURI);
        MongoDatabase database = mongoClient.getDatabase("Score");
        MongoCollection collection = database.getCollection("jokes");
        Document document = (Document) collection.find(new Document("id", random.nextInt(250))).first();
        String joke = document.getString("Jokes");
        String fin = joke.replace("<>","\n");

        return fin;



    }

}
