package org.firstinspires.ftc.teamcode.intelligentdesign;

import android.content.Context;

public class Application extends android.app.Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        Application.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Application.context;
    }
}
