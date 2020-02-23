package org.firstinspires.ftc.teamcode.intelligentdesign;

import android.annotation.SuppressLint;
import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Position;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class IntelligentDesign implements AutoCloseable {
    @SuppressLint("SdCardPath")
    public static final String BASE_PATH = "/sdcard/intelligentdesign/logs";
    public static final File BASE_PATH_FILE = new File(BASE_PATH);

    private IDMatchDetails matchDetails;


    private JSONArray logItems = new JSONArray();

    public IntelligentDesign(String label, MatchPhase phase) {
        matchDetails = new IDMatchDetails(phase, null, null, label);
    }

    public IntelligentDesign(String label, MatchPhase phase, Position position, Alliance alliance) {
        matchDetails = new IDMatchDetails(phase, position, alliance, label);
    }

    public void startMatch() {
        matchDetails.start();
    }

    public void addItem(IDItem item) throws JSONException {
        logItems.put(item.toJSON());
    }

    public void close() throws IOException, JSONException {
        File saveFile = new File(BASE_PATH_FILE, matchDetails.generateFileName());
        saveFile.createNewFile();

        PrintWriter writer = new PrintWriter(new FileOutputStream(saveFile, false));
        JSONObject json = new JSONObject();
        json.put("matchDetails", matchDetails.toJSON());
        json.put("logs", logItems);
        writer.print(json.toString());
        writer.close();
    }
}
