package org.firstinspires.ftc.teamcode.intelligentdesign.api;

import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.teamcode.intelligentdesign.api.response.IDApiResponse;

import java.util.List;
import java.util.Map;

public interface IDEndpointHandler {
    IDApiResponse handle(NanoHTTPD.Method method, String route, Map<String, List<String>> parameters);
}
