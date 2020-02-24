package org.firstinspires.ftc.teamcode.vyncynt.odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.vyncynt.Vyncynt;

@TeleOp(name = "Where am I?")
public class WhereAmI extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Vyncynt vyncynt = new Vyncynt(hardwareMap, telemetry, true);

        waitForStart();

        while(opModeIsActive()) {
            telemetry.addData("x", vyncynt.getXCoordinate());
            telemetry.addData("y", vyncynt.getYCoordinate());
            telemetry.addData("Θ", vyncynt.getOrientation());
            telemetry.update();
        }
    }
}
