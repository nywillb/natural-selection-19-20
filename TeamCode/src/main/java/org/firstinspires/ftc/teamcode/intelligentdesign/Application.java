package org.firstinspires.ftc.teamcode.intelligentdesign;

import android.content.Context;
import android.content.res.Resources;

public class Application extends android.app.Application {
    private Context context;
    private Resources resources;

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = getApplicationContext();
        this.resources = getResources();
    }

    private Application() {
        this.onCreate();
    }

    public static Context getAppContext() {
        return new Application().context;
    }
    public static Resources getAppResources() {
        return new Application().resources;
    }
}
