package app.book.domain;

import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author Ethan
 */
@Table(name = "authors")
public class Author {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Integer id;

    @Column(name = "name")
    public String name;
}
