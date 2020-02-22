package org.firstinspires.ftc.teamcode.vyncynt.odometry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.vyncynt.Vyncynt;

@TeleOp(name = "Odometry Square Test")
public class OdometrySquareTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Vyncynt vyncynt = new Vyncynt(hardwareMap, telemetry);

        waitForStart();

        while(opModeIsActive()) {
            vyncynt.driveToPosition(0, 0, 0.5, 2, this);
            vyncynt.driveToPosition(24, 0, 0.5, 2, this);
            vyncynt.driveToPosition(24, 24, 0.5, 2, this);
            vyncynt.driveToPosition(0, 24, 0.5, 2, this);
        }
    }
}