package org.firstinspires.ftc.teamcode.intelligentdesign;

import android.content.res.AssetManager;
import android.content.res.Resources;
import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.APIRouter;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.IDEndpointHandler;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiErrorResponse;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDFileResponse;

import java.io.IOException;
import java.io.InputStream;
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
            if(route.equals("/")) {
                route = "/index.html";
            }
            Resources resources = Application.getAppResources();
            AssetManager assets = resources.getAssets();
            try {
                InputStream fileStream = assets.open("intelligentdesign-ui" + route);
                return IDFileResponse.build(fileStream, route);
            } catch (IOException e) {
                return IDApiErrorResponse.build(Response.Status.NOT_FOUND, "file " + route + " not found.");
            }
        }
    }
}