package org.firstinspires.ftc.teamcode.intelligentdesign.api;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.Status;
import org.firstinspires.ftc.teamcode.intelligentdesign.IntelligentDesign;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiErrorResponse;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.List;
import java.util.Map;

public class GetLogEndpoint implements IDEndpointHandler {
    @Override
    public IDApiResponse handle(NanoHTTPD.Method method, String route, Map<String, List<String>> parameters) {
        String fileParam;
        try {
            fileParam = parameters.get("file").get(0);
        } catch (NullPointerException e) {
            return IDApiErrorResponse.build(NanoHTTPD.Response.Status.BAD_REQUEST, "invalid_params");
        }
        File file = new File(IntelligentDesign.getLogFolder(), fileParam);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch(FileNotFoundException e) {
            return IDApiErrorResponse.build(NanoHTTPD.Response.Status.NOT_FOUND, "file_not_found");
        }
        try {
            try {
                StringBuilder sb = new StringBuilder();
                String line = reader.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = reader.readLine();
                }

                JSONObject resp = new JSONObject();
                resp.put("status", "ok");
                JSONObject logData = new JSONObject(sb.toString());
                resp.put("data", logData);
                return new IDApiResponse(Status.OK, "application/json", resp.toString());
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            return IDApiErrorResponse.build(Status.INTERNAL_ERROR, "internal_server_error");
        } catch (JSONException e) {
            return IDApiErrorResponse.build(Status.INTERNAL_ERROR, "log_file_invalid");
        }
    }
}
