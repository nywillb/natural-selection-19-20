package org.firstinspires.ftc.teamcode.intelligentdesign;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;

@TeleOp(name = "\uD83D\uDDFA️ Intelligent Design Log Server")
public class IDLogViewerOpMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        try {
            IDWebServer server = new IDWebServer(telemetry);
            Method getString = Build.class.getDeclaredMethod("getString", String.class);
            getString.setAccessible(true);

            telemetry.addData("\uD83D\uDDFA️ Intelligent Design", "Serving on port " + server.getListeningPort() + ".");

            try {
                URL url = new URL("http://localhost:8080/js/rcInfo.json");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                JSONObject data = new JSONObject(content.toString());
                telemetry.addData("\uD83C\uDF10 Connect to Wifi network", data.get("networkName"));
                telemetry.addData("\uD83D\uDD10 Password", data.get("passphrase").toString());
                telemetry.addData("\uD83D\uDDA5 Go to", data.get("serverUrl").toString().replace(":8080", ":17126"));
            } finally {
                telemetry.update();
            }
            waitForStart();

//            server.stop();
        } catch (Exception e) {
            telemetry.addData("Uh oh...", "An server error has occurred");
            telemetry.addData("Error", e.toString());
            telemetry.addData("Stack Trace", Arrays.toString(e.getStackTrace()));
            telemetry.update();
        }
    }
}
