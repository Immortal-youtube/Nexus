package commands.MongoDB;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Random;

public class JokesConnector {

    public static String joke() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(System.getenv("JOKE_LINK")))
                .header(System.getenv("JOKE_API_HEADING"), System.getenv("JOKE_API"))
                .header(System.getenv("JOKE_HOST"), System.getenv("JOKE_HOST_KEY"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
        JsonArray array = jsonObject.get("body").getAsJsonArray();
        System.out.println(array.get(0));
        String setup = array.get(0).getAsJsonObject().get("setup").getAsString();
        String punchline = array.get(0).getAsJsonObject().get("punchline").getAsString();
        System.out.println(setup);
        System.out.println(punchline);
        return setup + "\n" + punchline;
    }

}
