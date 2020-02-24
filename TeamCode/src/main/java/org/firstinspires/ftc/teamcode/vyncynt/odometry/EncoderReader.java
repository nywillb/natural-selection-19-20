package org.firstinspires.ftc.teamcode.vyncynt.odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.intelligentdesign.IntelligentDesign;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDItem;
import org.firstinspires.ftc.teamcode.intelligentdesign.MatchPhase;
import org.firstinspires.ftc.teamcode.vyncynt.Vyncynt;
import org.json.JSONException;

import java.io.IOException;

@TeleOp(name = "Encoder reader")
public class EncoderReader extends LinearOpMode {
    @Override
    public void runOpMode() {
        Vyncynt vyncynt = new Vyncynt(hardwareMap, telemetry, false);
        ElapsedTime time = new ElapsedTime();
        IntelligentDesign id = new IntelligentDesign("Encoder log", MatchPhase.TELEOP);

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
                id.addItem(new IDItem("Left Encoder", vyncynt.fl.getCurrentPosition()));
                id.addItem(new IDItem("Right Encoder", vyncynt.fr.getCurrentPosition()));
                id.addItem(new IDItem("Horizontal Encoder", vyncynt.bl.getCurrentPosition()));
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
