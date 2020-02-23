package org.firstinspires.ftc.teamcode.vyncynt.odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDLog;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDLogItem;
import org.firstinspires.ftc.teamcode.intelligentdesign.MatchPhase;
import org.firstinspires.ftc.teamcode.vyncynt.Vyncynt;
import org.json.JSONException;

import java.io.IOException;

@TeleOp(name = "Encoder reader")
public class EncoderReader extends LinearOpMode {
    @Override
    public void runOpMode() {
        Vyncynt vyncynt = new Vyncynt(hardwareMap, telemetry);
        ElapsedTime time = new ElapsedTime();
        IDLog id = new IDLog("Encoder log", MatchPhase.TELEOP);

        telemetry.addData("status", "init complete");
        telemetry.update();

        waitForStart();
        time.reset();
        id.startMatch();

        while(opModeIsActive()) {
            telemetry.addData("L", vyncynt.fl.getCurrentPosition());
            telemetry.addData("R", vyncynt.fr.getCurrentPosition());
            telemetry.addData("H", vyncynt.bl.getCurrentPosition());

            try {
                id.addItem(new IDLogItem("Left Encoder", vyncynt.fl.getCurrentPosition()));
                id.addItem(new IDLogItem("Right Encoder", vyncynt.fr.getCurrentPosition()));
                id.addItem(new IDLogItem("Horizontal Encoder", vyncynt.bl.getCurrentPosition()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            telemetry.addData("Elapsed time (s)", time.seconds());
            telemetry.update();
        }

        try {
            id.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
