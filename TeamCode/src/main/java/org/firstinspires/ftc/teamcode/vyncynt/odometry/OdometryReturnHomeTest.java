package org.firstinspires.ftc.teamcode.vyncynt.odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.vyncynt.Vyncynt;

@TeleOp(name = "Odometry Return Home Test")
public class OdometryReturnHomeTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Vyncynt vyncynt = new Vyncynt(hardwareMap, telemetry, true);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("x", vyncynt.getXCoordinate());
            telemetry.addData("y", vyncynt.getYCoordinate());
            telemetry.addData("Θ", vyncynt.getOrientation());

            telemetry.addData("Status", "Give me a shove!");

            telemetry.update();

            telemetry.addData("status", "doing stuff");
            vyncynt.driveToPosition(0, 0, 0.5, 1, this);
            vyncynt.rotateToAngle(0, 10, 0.5, this);
            telemetry.update();
        }

        vyncynt.stop();
    }
}