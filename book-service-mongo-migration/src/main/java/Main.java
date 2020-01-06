import core.framework.mongo.MongoMigration;
import org.bson.Document;

import static com.mongodb.client.model.Indexes.ascending;

/**
 * @author Ethan
 */
public class Main {
    public static void main(String[] args) {
        var migration = new MongoMigration("sys.properties", "sys.mongo.adminURI");
        migration.migrate(mongo -> {
            mongo.runCommand(new Document().append("setParameter", 1).append("notablescan", 1));
        });

        migration = new MongoMigration("sys.properties");
        migration.migrate(mongo -> {
            mongo.createIndex("borrowed_records", ascending("user_id"));
            mongo.createIndex("borrowed_records", ascending("book_id"));
            mongo.createIndex("borrowed_records", ascending("user_name"));
            mongo.createIndex("borrowed_records", ascending("book_name"));
            mongo.createIndex("borrowed_records", ascending("actual_return_time"));
        });
    }
}