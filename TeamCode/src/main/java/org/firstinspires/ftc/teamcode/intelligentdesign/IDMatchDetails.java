package org.firstinspires.ftc.teamcode.intelligentdesign;

import com.google.gson.JsonObject;
import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Position;
import org.json.JSONException;
import org.json.JSONObject;

public class IDMatchDetails {
    private long startTime;
    private MatchPhase phase;
    private Position position;
    private Alliance alliance;
    private String label;

    public IDMatchDetails(MatchPhase phase, Position position, Alliance alliance, String label) {
        this.phase = phase;
        this.position = position;
        this.alliance = alliance;
        this.label = label;
    }

    public IDMatchDetails(MatchPhase phase, String label) {
        this.phase = phase;
        this.label = label;
    }

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public String generateFileName() {
        String safelabel = label.replaceAll("[^a-zA-Z ]", "").toLowerCase();
        return startTime + "-" + safelabel + "-" + (phase == MatchPhase.AUTONOMOUS ? "auto" : "teleop") + ".json";
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("label", label);
        json.put("startTime", startTime);
        json.put("isRed", alliance == Alliance.RED);
        json.put("isAuto", phase == MatchPhase.AUTONOMOUS);
        json.put("startedAtBuildingZone", position == Position.BUILDING_ZONE);

        return json;
    }
}
