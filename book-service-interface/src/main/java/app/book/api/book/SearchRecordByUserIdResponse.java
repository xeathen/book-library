package app.book.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class SearchRecordByUserIdResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "books")
    public List<GetBorrowedRecordResponse> borrowedRecords;
}