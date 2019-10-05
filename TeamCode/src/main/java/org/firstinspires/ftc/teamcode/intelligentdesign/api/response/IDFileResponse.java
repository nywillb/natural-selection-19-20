package org.firstinspires.ftc.teamcode.intelligentdesign.api.response;

import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;

public class IDFileResponse extends NanoHTTPD.Response {


    /**
     * Creates a fixed length response if totalBytes>=0, otherwise chunked.
     *
     * @param status
     * @param mimeType
     * @param data
     * @param totalBytes
     */
    private IDFileResponse(IStatus status, String mimeType, InputStream data, long totalBytes) {
        super(status, mimeType, data, totalBytes);
    }

    public static IDFileResponse build (InputStream file, String route) {
        long totalBytes;
        try {
            totalBytes = file.available();
        } catch (IOException e) {
            totalBytes = -1;
        }
        String mimeType = IDFileResponse.getMimeType(route);
        return new IDFileResponse(Status.OK, mimeType, file, totalBytes);
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
        } else if (path.endsWith(".woff2")) {
            return "font/woff2";
        }
        return "application/octet-stream";
    }
}
