package app.bo.api.user;

import core.framework.api.json.Property;

/**
 * @author Ethan
 */
public class ChangeStatusAJAXResponse {
    @Property(name = "status")
    public UserStatusAJAXView status;
}
