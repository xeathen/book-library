package app.book.api.book;

import core.framework.api.json.Property;
import core.framework.db.Column;

/**
 * @author Ethan
 */
public class BookView {
    @Property(name = "id")
    @Column(name = "id")
    public Long id;

    @Property(name = "name")
    @Column(name = "name")
    public String name;

    @Property(name = "author_name")
    @Column(name = "author_name")
    public String authorName;

    @Property(name = "category_name")
    @Column(name = "category_name")
    public String categoryName;

    @Property(name = "tag_name")
    @Column(name = "tag_name")
    public String tagName;

    @Property(name = "publishing_house")
    @Column(name = "publishing_house")
    public String publishingHouse;

    @Property(name = "description")
    @Column(name = "description")
    public String description;

    @Property(name = "amount")
    @Column(name = "amount")
    public Integer amount;
}
