package org.firstinspires.ftc.teamcode.intelligentdesign;

import fi.iki.elonen.NanoHTTPD;

import java.util.List;
import java.util.Map;

public interface IDHandler {
    IDResponse onAction(NanoHTTPD.Method method, String route, Map<String, List<String>> parameters);
}
