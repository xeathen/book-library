package app.user.domain;

import core.framework.api.validate.NotBlank;
import core.framework.api.validate.NotNull;
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

    @NotNull
    @NotBlank
    @Column(name = "username")
    public String username;

    @NotNull
    @NotBlank
    @Column(name = "password")
    public String password;

    @NotNull
    @NotBlank
    @Column(name = "email")
    public String email;

    @NotNull
    @Column(name = "status")
    public UserStatus status;

    @NotNull
    @NotBlank
    @Column(name = "salt")
    public String salt;

    @NotNull
    @Column(name = "iteration")
    public Integer iteration;
}
