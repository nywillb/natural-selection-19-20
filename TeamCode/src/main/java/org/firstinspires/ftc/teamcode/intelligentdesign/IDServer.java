package org.firstinspires.ftc.teamcode.intelligentdesign;

import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.APIRouter;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.IDEndpointHandler;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * Intelligent Design is a simple web server designed for FTC by team 17126 Natural Selection
 *
 * @author William Barkoff (<a href="https://williambarkoff.com">williambarkoff.com</a>)
 * @author FTC Team 17126 Natural Selection
 */
public class IDServer extends NanoHTTPD {
    private HashMap<String, IDEndpointHandler> routes = new HashMap<>();
    private IDEndpointHandler APIHandler = new APIRouter();

    /**
     * Allows the user to set a custom API Handler.
     * @param APIHandler APIHandler to use.
     */
    public void setAPIHandler(IDEndpointHandler APIHandler) {
        this.APIHandler = APIHandler;
    }

    /**
     * Creates an IntelligentDesign server on port 17126.
     * @throws IOException In the event that there is an issue starting the server, an IOException is thrown.
     */
    public IDServer() throws IOException {
        super(17126);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    /**
     * Creates an Intelligent Design server.
     * @param port Port to start the server on
     * @throws IOException In the event that there is an issue starting the server, an IOException is thrown.
     */
    public IDServer(int port) throws IOException {
        super(port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    /**
     * Creates an Intelligent Design server.
     * @param hostname Hostname to start server on
     * @param port Port to start server on
     * @throws IOException In the event that there is an issue starting the server, an IOException is thrown.
     */
    public IDServer(String hostname, int port) throws IOException {
        super(hostname, port);
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    /**
     * Handles a route.
     * @param session The session that is used.
     * @return A response object.
     */
    @Override
    public Response serve(IHTTPSession session) {
        String route = session.getUri();
        if(route.startsWith("/api")) {
            return APIHandler.handle(session.getMethod(), session.getUri(), session.getParameters());
        } else {
            //TODO: Add static handling
            return new IDApiResponse("TODO: Add static handling");
        }
    }

    /**
     * Gets a mime type from a file name
     * @param path path of file
     * @return mime type
     */
    public static String getMimeType(String path) {
        if(path.endsWith(".json")) {
            return "application/json";
        } else if (path.endsWith("css")) {
            return "text/css";
        } else if (path.endsWith("js")) {
            return "text/javascript";
        } else if(path.endsWith("html") || path.endsWith("htm")) {
            return "text/html";
        } else if(path.endsWith("png")) {
            return "image/png";
        } else if(path.endsWith("jpg") || path.endsWith("jpeg")) {
            return "image/jpeg";
        } else if(path.endsWith("pdf")) {
            return "application/pdf";
        } else if (path.endsWith("txt")) {
            return "text/plain";
        }
        return "application/octet-stream";
    }

    /**
     * Returns the version of Intelligent Design being used.
     * @return the version of Intelligent Design being used.
     */
    public static String getVersion() {
        return "0.0.1";
    }
}