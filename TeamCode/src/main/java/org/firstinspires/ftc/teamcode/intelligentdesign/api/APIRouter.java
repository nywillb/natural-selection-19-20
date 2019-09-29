package org.firstinspires.ftc.teamcode.intelligentdesign.api;

import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.GetLogEndpoint;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiErrorResponse;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIRouter implements IDEndpointHandler {
    private Map<String, IDEndpointHandler> routes = new HashMap<>();

    public APIRouter() {
        routes.put("/api/getAllLogs", new GetAllLogsEndpoint());
        routes.put("/api/get", new GetLogEndpoint());
    }

    @Override
    public IDApiResponse handle(NanoHTTPD.Method method, String route, Map<String, List<String>> parameters) {
        IDEndpointHandler handler = routes.get(route);
        if(handler == null) {
            return IDApiErrorResponse.build(NanoHTTPD.Response.Status.NOT_FOUND, "Endpoint not found");
        }
        return handler.handle(method, route, parameters);
    }

    @Deprecated
    public IDApiResponse onAction(NanoHTTPD.Method method, String route, Map<String, List<String>> parameters) {
        return this.handle(method, route, parameters);
    }
}
