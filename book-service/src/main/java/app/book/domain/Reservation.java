package app.book.domain;

import core.framework.api.validate.NotNull;
import core.framework.db.Column;
import core.framework.db.PrimaryKey;
import core.framework.db.Table;

import java.time.LocalDateTime;

/**
 * @author Ethan
 */
@Table(name = "reservations")
public class Reservation {
    @PrimaryKey(autoIncrement = true)
    @Column(name = "id")
    public Integer id;

    @NotNull
    @Column(name = "user_id")
    public Long userId;

    @NotNull
    @Column(name = "book_id")
    public Long bookId;

    @NotNull
    @Column(name = "reserve_time")
    public LocalDateTime reserveTime;
}
