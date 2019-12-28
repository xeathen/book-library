import com.mongodb.client.model.IndexOptions;
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
            mongo.createIndex("borrowed_records", ascending("user_id", "book_id"),
                new IndexOptions()
                    .background(true));
            mongo.createIndex("borrowed_records", ascending("borrow_time"),
                new IndexOptions()
                    .background(true));
            mongo.createIndex("borrowed_records", ascending("return_time"));
            //TODO:add index
//            mongo.createIndex("test", compoundIndex(ascending()));
        });
    }
}