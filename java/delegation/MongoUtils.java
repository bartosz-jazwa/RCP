package delegation;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoUtils {
    private static MongoUtils ourInstance = new MongoUtils();
    private com.mongodb.client.MongoClient client;

    public static MongoUtils getInstance() {
        return ourInstance;
    }

    private MongoUtils() {
        client = MongoClients.create("mongodb://localhost");
    }

    public MongoClient getClient() {
        return client;
    }
}
