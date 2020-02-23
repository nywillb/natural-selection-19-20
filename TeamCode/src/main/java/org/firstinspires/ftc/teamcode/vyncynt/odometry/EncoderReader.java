package org.firstinspires.ftc.teamcode.vyncynt.odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.vyncynt.Vyncynt;

@TeleOp(name = "Encoder reader")
public class EncoderReader extends LinearOpMode {
    @Override
    public void runOpMode() {
        Vyncynt vyncynt = new Vyncynt(hardwareMap, telemetry);
        ElapsedTime time = new ElapsedTime();

        telemetry.addData("status", "init complete");
        telemetry.update();

        waitForStart();
        time.reset();

        while(opModeIsActive()) {
            telemetry.addData("L", vyncynt.fl.getCurrentPosition());
            telemetry.addData("R", vyncynt.fr.getCurrentPosition());
            telemetry.addData("H", vyncynt.bl.getCurrentPosition());
            telemetry.addData("Elapsed time (s)", time.seconds());
            telemetry.update();
        }
    }
}
