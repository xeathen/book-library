package app.book.domain;

import core.framework.db.Column;

/**
 * @author Ethan
 */
public class BookView {
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "author_name")
    public String authorName;

    @Column(name = "category_name")
    public String categoryName;

    @Column(name = "tag_name")
    public String tagName;

    @Column(name = "publishing_house")
    public String publishingHouse;

    @Column(name = "description")
    public String description;

    @Column(name = "quantity")
    public Integer quantity;
}
