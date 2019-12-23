package app.book.domain;

import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author Ethan
 */
@Table(name = "books")
public class Book {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "author")
    public String author;

    @Column(name = "pub")
    public String pub;

    @Column(name = "category")
    public String category;

    @Column(name = "tag")
    public String tag;

    @Column(name = "description")
    public String description;

    @Column(name = "num")
    public Integer num;
}
