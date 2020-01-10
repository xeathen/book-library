package app.book.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
import core.framework.mongo.Collection;
import core.framework.mongo.Field;
import core.framework.mongo.Id;

import java.time.LocalDateTime;

/**
 * @author Ethan
 */
@Collection(name = "borrowed_records")
public class BorrowedRecord {
    @Id
    public String id;

    @NotNull
    @Field(name = "user_id")
    public Long userId;

    @NotNull
    @NotBlank
    @Field(name = "username")
    public String username;

    @NotNull
    @Field(name = "book_id")
    public Long bookId;

    @NotNull
    @NotBlank
    @Field(name = "book_name")
    public String bookName;

    @NotNull
    @Field(name = "borrow_time")
    public LocalDateTime borrowTime;

    @NotNull
    @Field(name = "expected_return_time")
    public LocalDateTime expectedReturnTime;

    @Field(name = "actual_return_time")
    public LocalDateTime actualReturnTime;
}
