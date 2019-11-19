package org.firstinspires.ftc.teamcode.intelligentdesign;

import org.json.JSONException;
import org.json.JSONObject;

public class IDLogItem {
    long time;
    String context;
    double datapoint;

    public IDLogItem(String context, double datapoint) {
        this.context = context;
        this.datapoint = datapoint;
        this.time = System.currentTimeMillis();
    }

    public IDLogItem(long time, String context, double datapoint) {
        this.time = time;
        this.context = context;
        this.datapoint = datapoint;
    }

    JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("time", time);
        json.put("context", context);
        json.put("datapoint", datapoint);

        return json;
    }
}
