package app.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;

import java.time.ZonedDateTime;

/**
 * @author Ethan
 */
@Collection(name = "borrowed_records")
public class BorrowedRecords {
    @Id
    public String id;

    @NotNull
    @Field(name = "user_id")
    public Long userId;

    @NotNull
    @Field(name = "book_id")
    public Long bookId;

    @NotNull
    @Field(name = "borrow_time")
    public ZonedDateTime borrowTime;

    @NotNull
    @Field(name = "return_time")
    public ZonedDateTime returnTime;

    @NotNull
    @Field(name = "is_returned")
    public Boolean isReturned;
}
