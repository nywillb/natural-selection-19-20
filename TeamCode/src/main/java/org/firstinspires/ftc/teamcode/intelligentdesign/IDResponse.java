package org.firstinspires.ftc.teamcode.intelligentdesign;

import fi.iki.elonen.NanoHTTPD;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class IDResponse extends NanoHTTPD.Response {
    public IDResponse(String data) {
        super(Status.OK, "text/plain", new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), data.length());
    }

    public IDResponse(IStatus status, String data) {
        super(status, "text/plain", new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), data.length());
    }

    public IDResponse(IStatus status, String mimeType, String data) {
        super(status, mimeType, new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), data.length());
    }

    public IDResponse(IStatus status, String mimeType, InputStream data, long totalBytes) {
        super(status, mimeType, data, totalBytes);
    }
}
