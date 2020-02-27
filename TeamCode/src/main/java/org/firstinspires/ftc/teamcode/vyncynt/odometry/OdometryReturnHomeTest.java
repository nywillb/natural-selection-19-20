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

            if(Math.hypot(vyncynt.getXCoordinate(), vyncynt.getYCoordinate()) > 300) {
                telemetry.addData("status", "doing stuff");
                telemetry.update();
                vyncynt.driveToPosition(0, 0, 0.5, 200, this);
                vyncynt.rotateToAngle(0, 3, 0.5, this);
            }
        }

        vyncynt.stop();
    }
}