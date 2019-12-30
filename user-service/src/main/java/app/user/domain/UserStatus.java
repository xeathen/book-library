package app.user.domain;

import core.framework.db.DBEnumValue;

/**
 * @author Ethan
 */
public enum UserStatus {
    @DBEnumValue("ACTIVE")
    ACTIVE,
    @DBEnumValue("INACTIVE")
    INACTIVE
}
