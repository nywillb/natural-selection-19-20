package org.firstinspires.ftc.teamcode.intelligentdesign.api.response;

import org.json.JSONException;
import org.json.JSONObject;

public class IDApiErrorResponse extends IDApiResponse {
    private IDApiErrorResponse(IStatus status, String mimeType, String data) {
        super(status, mimeType, data);
    }

    public static IDApiErrorResponse build(Status status, String error) {
        try {
            JSONObject resp = new JSONObject();
            resp.put("status", "error");
            resp.put("error", error);
            return new IDApiErrorResponse(status, "application/json", resp.toString());
        } catch (JSONException e) {
            /* unreachable state */
            /* see https://xkcd.com/2200/ */
            return new IDApiErrorResponse(status, "application/json", "{\"status\": \"error\", \"error\": \"" + error + "\"}");
        }
    }
}
