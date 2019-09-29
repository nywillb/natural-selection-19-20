package org.firstinspires.ftc.teamcode.intelligentdesign.logs;

import android.content.Context;
import android.os.Build;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.firstinspires.ftc.teamcode.intelligentdesign.Application;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Log {
    private JSONArray log = new JSONArray();
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Context context = Application.getAppContext();
    private File logFolder = new File(context.getFilesDir(), "intelligentDesign-logs");
    private File file;

    public Log() throws IOException, LogFileAlreadyExistsException {
        file = new File(logFolder, Log.createFileName());
        if(!file.createNewFile()) {
            throw new LogFileAlreadyExistsException(file.getAbsolutePath());
        }
    }

    public void add(Object obj) throws JSONException {
        JSONObject logItem = new JSONObject();

        logItem.put("matchtime", null);
        logItem.put("timestamp", System.currentTimeMillis());
        logItem.put("stacktrace", Arrays.toString(Thread.currentThread().getStackTrace()));
        String jsonObjStr = gson.toJson(obj);
        Object jsonObj = new JSONTokener(jsonObjStr).nextValue();
        logItem.put("data", jsonObj);

        log.put(logItem);
    }

    public void saveLog() throws FileNotFoundException {
        FileOutputStream stream = new FileOutputStream(this.file);
    }

    public static String createFileName() {
        TimeZone nycTime = TimeZone.getTimeZone("America/New_York");
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.US);
        formatter.setTimeZone(nycTime);
        return formatter.format(new Date()) + "-log.json";
    }

    public JSONArray getLog() {
        return log;
    }

    public File getLogFolder() {
        return logFolder;
    }

    public File getFile() {
        return file;
    }
}
