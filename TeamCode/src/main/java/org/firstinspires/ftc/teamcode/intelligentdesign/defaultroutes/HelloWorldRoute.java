package org.firstinspires.ftc.teamcode.intelligentdesign.defaultroutes;

import fi.iki.elonen.NanoHTTPD;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDHandler;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDResponse;

import java.util.List;
import java.util.Map;

public class HelloWorldRoute implements IDHandler {
    @Override
    public IDResponse onAction(NanoHTTPD.Method method, String route, Map<String, List<String>> parameters) {
        return new IDResponse("Hello, World!");
    }
}
