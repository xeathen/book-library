package app.author.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
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

    @NotNull
    @NotBlank
    @Column(name = "name")
    public String name;
}
