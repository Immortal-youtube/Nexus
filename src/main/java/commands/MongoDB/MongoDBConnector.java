package commands.MongoDB;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.sql.*;


public class MongoDBConnector {
    static Dotenv dotenv = Dotenv.load();
    public static void add(String number) throws SQLException {
        String uri = dotenv.get("MONGO_URL");
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        MongoDatabase database = mongoClient.getDatabase("Score");
        MongoCollection collection = database.getCollection("test");
        Document document =  (Document) collection.find(new Document("PersonID", number)).first();
        if(document == null){
            Document newDocument = new Document("PersonID", number);
            newDocument.append("score", 0);
            collection.insertOne(newDocument);
        }



    }
    public static void see(String number, SlashCommandInteractionEvent event) throws SQLException {
        String uri = dotenv.get("MONGO_URL");
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        MongoDatabase database = mongoClient.getDatabase("Score");
        MongoCollection collection = database.getCollection("test");
        Document document =  (Document) collection.find(new Document("PersonID", number)).first();
        int newScore = document.getInteger("score");
        event.deferReply().queue();
        event.getHook().sendMessage("Your score is: " + newScore).queue();
    }

    public static void update(String number) throws SQLException {
        String uri = dotenv.get("MONGO_URL");
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        MongoDatabase database = mongoClient.getDatabase("Score");
        MongoCollection collection = database.getCollection("test");
        Document document =  (Document) collection.find(new Document("PersonID", number)).first();
        int score = document.getInteger("score");
        int newScore = score + 1;
        Bson bson = new Document("score", newScore);
        Bson update = new Document("$set",bson);
        collection.updateOne(document,update);

    }

}
