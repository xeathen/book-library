package app.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author Ethan
 */
@Table(name = "books")
public class Book {
    @PrimaryKey(autoIncrement = true)
    @NotNull
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "name")
    public String name;

    @NotNull
    @Column(name = "author_id")
    public Integer authorId;

    @NotNull
    @Column(name = "category_id")
    public Integer categoryId;

    @NotNull
    @Column(name = "tag_id")
    public Integer tagId;

    @NotNull
    @Column(name = "pub")
    public String pub;

    @Column(name = "description")
    public String description;

    @NotNull
    @Column(name = "num")
    public Integer num;
}
