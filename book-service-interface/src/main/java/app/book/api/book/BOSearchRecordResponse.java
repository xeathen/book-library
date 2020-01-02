package app.book.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class BOSearchRecordResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "records")
    public List<BorrowedRecordView> borrowedRecords;
}
