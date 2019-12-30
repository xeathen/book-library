package app.user.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public enum UserStatusView {
    @Property(name = "ACTIVE")
    ACTIVE,
    @Property(name = "INACTIVE")
    INACTIVE
}
