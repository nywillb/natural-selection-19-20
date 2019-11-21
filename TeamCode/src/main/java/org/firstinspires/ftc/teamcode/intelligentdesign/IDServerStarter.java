package org.firstinspires.ftc.teamcode.intelligentdesign;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.wifi.WifiDirectAssistant;

import java.util.Arrays;

@TeleOp(name = "\uD83D\uDDFA Start Intelligent Design Server")
public class IDServerStarter extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        try {
            IDWebServer server = new IDWebServer(telemetry);

            telemetry.addData("\uD83D\uDDFA️ Intelligent Design", "Serving on port " + server.getListeningPort() + ".");

            // This api is a bit undocumented
            WifiDirectAssistant wda = WifiDirectAssistant.getWifiDirectAssistant(hardwareMap.appContext);

            telemetry.addData("\uD83C\uDF10 Connect to Wifi network", wda.getGroupNetworkName());
            telemetry.addData("\uD83D\uDD10 Password", wda.getPassphrase());

            Context ctx = hardwareMap.appContext;
            WifiManager wm = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);

            // I think we're supposed to use java.net.InetAddress#getHostAddress() instead, but I need to look into that.
            telemetry.addData("\uD83D\uDDA5 Go to", "http://" + Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress()) + ":17126");

            telemetry.addLine();

            telemetry.addLine("Note that by stopping this opmode, you are *NOT* stopping the Intelligent Design server.");

            telemetry.update();

            waitForStart();
        } catch (Exception e) {
            telemetry.addData("Uh oh...", "An server error has occurred");
            telemetry.addData("Error", e.toString());
            telemetry.addData("Stack Trace", Arrays.toString(e.getStackTrace()));
            telemetry.update();
        }
    }
}
