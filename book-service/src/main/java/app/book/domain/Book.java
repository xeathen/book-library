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

    @Column(name = "author_id")
    public Integer authorId;

    @Column(name = "category_id")
    public Integer categoryId;

    @Column(name = "tag_id")
    public Integer tagId;

    @Column(name = "pub")
    public String pub;

    @Column(name = "description")
    public String description;

    @Column(name = "num")
    public Integer num;
}
