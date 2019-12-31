package app.web.api.book;

import core.framework.api.json.Property;

import java.util.List;

/**
 * @author Ethan
 */
public class SearchRecordAJAXResponse {
    @Property(name = "total")
    public Integer total;

    @Property(name = "records")
    public List<BorrowedRecordAJAXView> borrowedRecords;
}
