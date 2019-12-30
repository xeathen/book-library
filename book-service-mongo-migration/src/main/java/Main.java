import core.framework.mongo.MongoMigration;
import org.bson.Document;

import static com.mongodb.client.model.Indexes.ascending;
import static com.mongodb.client.model.Indexes.compoundIndex;

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
            mongo.createIndex("borrowed_records", compoundIndex(ascending("user_id", "book_id")));
            mongo.createIndex("borrowed_records", ascending("user_name"));
            mongo.createIndex("borrowed_records", ascending("book_name"));
            mongo.createIndex("borrowed_records", ascending("user_name"));
            mongo.createIndex("borrowed_records", ascending("borrow_time"));
            mongo.createIndex("borrowed_records", ascending("return_time"));
            mongo.createIndex("borrowed_records", ascending("is_Return"));
        });
    }
}