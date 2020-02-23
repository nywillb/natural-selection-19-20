package org.firstinspires.ftc.teamcode.intelligentdesign;

import android.annotation.SuppressLint;
import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class IDWebServer extends NanoHTTPD {
    @SuppressLint("SdCardPath")
    public static final String UI_PATH = "/sdcard/intelligentdesign/ui";
    private Telemetry telemetry;

    public IDWebServer(Telemetry telemetry) throws IOException {
        super(17126);
        this.telemetry = telemetry;
        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
    }

    public Response listLogs() {
        JSONArray logs = new JSONArray();
        File basePath = IntelligentDesign.BASE_PATH_FILE;

        String[] logFiles = basePath.list();
        for(String logFile : logFiles) {
            logs.put(logFile);
        }
        return newFixedLengthResponse(Response.Status.OK, "application/json", logs.toString());
    }

    public Response getLog(String file) {
        File log = new File(IntelligentDesign.BASE_PATH, file);
        if(!log.exists()) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "application/json", makeErrorResponse("log not found"));
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(log)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return newFixedLengthResponse(Response.Status.OK, "application/json", contentBuilder.toString());
    }

    public Response serveFile(String path, String filename) {
        File file = new File(filename, path);
        if(!file.exists()) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "application/json", makeErrorResponse("file not found"));
        }

        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return newFixedLengthResponse(Response.Status.OK, determineMimeType(file.toURI().toString()), contentBuilder.toString());
    }

    private String determineMimeType(String uri) {
        if(uri.endsWith(".html")) {
            return "text/html";
        } else if (uri.endsWith(".js")) {
            return "application/javascript";
        } else if (uri.endsWith(".css")) {
            return "text/css";
        } else if (uri.endsWith(".json")) {
            return "application/json";
        }
        return "text/plain";
    }

    private String makeErrorResponse(String error) {
        JSONObject json = new JSONObject();
        try {
            json.put("status", "error");
            json.put("error", error);
            return json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "this error shouldn't ever happen.";
        }
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        if(uri.startsWith("/api")) {
            if(uri.equals("/api/listLogs")) {
                return listLogs();
            } else if (uri.startsWith("/api/log")) {
                String file = uri.substring("/api/log".length()); // substring is 0-indexed, so .length() includes the trailing "/"
                return getLog(file);
            } else {
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "application/json", makeErrorResponse("not found"));
            }
        } else {
            // it's a UI thing
            if (uri.equals("/")) {
                uri = "/index.html";
            }
            return serveFile(uri.substring(1), UI_PATH);
        }
    }
}
