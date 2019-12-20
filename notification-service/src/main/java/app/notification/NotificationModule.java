package app.notification;

import core.framework.api.http.HTTPStatus;
import core.framework.module.Module;
import core.framework.web.Response;

import static core.framework.http.HTTPMethod.GET;

/**
 * @author Ethan
 */
public class NotificationModule extends Module {
    @Override
    protected void initialize() {
        http().httpPort(8081);
        http().route(GET, "/hello", request -> Response.text("hello world!").status(HTTPStatus.CREATED));
    }
}
