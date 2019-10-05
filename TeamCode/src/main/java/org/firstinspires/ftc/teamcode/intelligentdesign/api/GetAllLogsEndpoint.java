package org.firstinspires.ftc.teamcode.intelligentdesign.api;

import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.teamcode.intelligentdesign.IntelligentDesign;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiErrorResponse;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiResponse;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * The GetAllLogsEndpoint gets a list of all the logs that are available in IntelligentDesign
 * @see IntelligentDesign
 */
public class GetAllLogsEndpoint implements IDEndpointHandler {
    @Override
    public IDApiResponse handle(NanoHTTPD.Method method, String route, Map<String, List<String>> parameters) {
        if(method != NanoHTTPD.Method.GET) {
            return IDApiErrorResponse.build(Status.METHOD_NOT_ALLOWED, "Method " + method.toString() + " not allowed.");
        }

        String[] files = IntelligentDesign.getLogFiles();
        JSONArray filesJSON;
        try {
            filesJSON = new JSONArray(files);
            JSONObject resp = new JSONObject();
            resp.put("status", "ok");
            resp.put("logs", filesJSON);
            return new IDApiResponse("application/json", resp.toString());
        } catch (JSONException e) {
            return IDApiErrorResponse.build(Status.INTERNAL_ERROR, "internal_server_error");
        }
    }
}
