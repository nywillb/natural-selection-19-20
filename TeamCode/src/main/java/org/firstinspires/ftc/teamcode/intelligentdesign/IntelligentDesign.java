package org.firstinspires.ftc.teamcode.intelligentdesign;

import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.teamcode.intelligentdesign.defaultroutes.HelloWorldRoute;
import org.firstinspires.ftc.teamcode.intelligentdesign.defaultroutes.IDIntroRoute;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Intelligent Design is a simple web server designed for FTC by team 17126 Natural Selection
 *
 * @author William Barkoff
 * @author FTC Team 17126 Natural Selection
 */
public class IntelligentDesign extends NanoHTTPD {
    private HashMap<String, IDHandler> routes = new HashMap<>();

    /**
     * Creates an IntelligentDesign server on port 17126.
     * @throws IOException In the event that there is an issue starting the server, an IOException is thrown.
     */
    public IntelligentDesign() throws IOException {
        super(17126);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    /**
     * Creates an Intelligent Design server.
     * @param port Port to start the server on
     * @throws IOException In the event that there is an issue starting the server, and IOException is thrown.
     */
    public IntelligentDesign(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);

        this.addDefaultRoutes();
    }

    /**
     * Creates an Intelligent Design server.
     * @param hostname Hostname to start server on
     * @param port Port to start server on
     * @throws IOException In the event that there is an issue starting the server, and IOException is thrown.
     */
    public IntelligentDesign(String hostname, int port) throws IOException {
        super(hostname, port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);

        this.addDefaultRoutes();
    }

    /**
     * Adds the default routes.
     * @see HelloWorldRoute
     * @see IDIntroRoute
     */
    private void addDefaultRoutes() {
        Map<String, IDHandler> defaultRoutes = new HashMap<>();
        defaultRoutes.put("/hello", new HelloWorldRoute());
        defaultRoutes.put("/", new IDIntroRoute(getVersion()));

        for (Map.Entry<String, IDHandler> route : defaultRoutes.entrySet()) {
            routes.put(route.getKey(), route.getValue());
        }
    }

    /**
     * Registers a route for use with Intelligent Design
     * @param route route that the handler should be used with.
     * @param handler Intelligent Design handler object
     * @throws RouteAlreadyExistsException Thrown in the case that the route is already specified
     * @throws NullPointerException Thrown if the route or the handler is null.
     */
    public void registerRoute(String route, IDHandler handler) throws RouteAlreadyExistsException, NullPointerException {
        IDHandler existingRoute = routes.get(route);
        if(route == null || handler == null) {
            throw new NullPointerException("Route and handler cannot be null.");
        } else if(existingRoute != null) {
            throw new RouteAlreadyExistsException("Route " + route + " already exists.");
        }
        routes.put(route, handler);
    }

    /**
     * Handles a route. This should not be called.
     * @param session The session that is used.
     * @return A response object.
     */
    @Override
    public Response serve(IHTTPSession session) {
        IDHandler handler = routes.get(session.getUri());
        if(handler == null) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", session.getUri() + " was not found.");
        }
        return handler.onAction(session.getMethod(), session.getUri(), session.getParameters());
    }

    /**
     * Returns the version of Intelligent Design being used.
     * @return the version of Intelligent Design being used.
     */
    public static String getVersion() {
        return "0.0.1";
    }
}