package org.firstinspires.ftc.teamcode.intelligentdesign.api;

import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.teamcode.intelligentdesign.IntelligentDesign;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiErrorResponse;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class GetVersionEndpoint implements IDEndpointHandler {

    @Override
    public IDApiResponse handle(NanoHTTPD.Method method, String route, Map<String, List<String>> parameters) {
        try {
            JSONObject resp = new JSONObject();
            resp.put("status", "ok");
            resp.put("version", IntelligentDesign.getVersion());
            resp.put("madeBy", new JSONArray().put("William Barkoff").put("FTC Team 17126 Natural Selection"));
            return new IDApiResponse(NanoHTTPD.Response.Status.OK, "application/json", resp.toString());
        } catch (JSONException e) {
            // Unreachable state
            return IDApiErrorResponse.build(NanoHTTPD.Response.Status.INTERNAL_ERROR, "internal_server_error");
        }
    }
}
