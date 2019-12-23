import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import core.framework.mongo.MongoMigration;
import org.bson.Document;

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
            mongo.createIndex("borrowed_records", Indexes.ascending("user_id", "book_id"),
                new IndexOptions()
                    .background(true)
                    .name("index_borrow_record_user_id"));
            mongo.createIndex("borrowed_records", Indexes.ascending("borrow_time"),
                new IndexOptions()
                    .background(true));
            mongo.createIndex("borrowed_records", Indexes.ascending("return_time"));
        });
    }
}