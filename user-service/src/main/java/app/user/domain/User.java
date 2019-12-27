package app.user.domain;

import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

/**
 * @author Ethan
 */
@Table(name = "users")
public class User {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Long id;

    @Column(name = "user_name")
    public String userName;

    @Column(name = "password")
    public String password;

    @Column(name = "user_email")
    public String userEmail;

    @Column(name = "status")
    //TODO:enum
    public Boolean status;
}
