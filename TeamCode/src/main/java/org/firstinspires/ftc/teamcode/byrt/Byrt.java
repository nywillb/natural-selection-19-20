package org.firstinspires.ftc.teamcode.byrt;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.robot.ByrtRobot;
import org.firstinspires.ftc.teamcode.robot.Robot;

@TeleOp(name = "\uD83D\uDC1D Byrt", group = "Byrt")
class Byrt extends LinearOpMode {

    ElapsedTime runtime = new ElapsedTime();
    SpinnerPosition[] spinnerPositions = new SpinnerPosition[]{SpinnerPosition.HALF, SpinnerPosition.OUT};
    int currentSpinnerPosition = 0;

    private boolean debounceA = false;
    private boolean debounceX = false;
    private boolean debounceY = false;

    @Override
    public void runOpMode() throws InterruptedException {
        ByrtRobot byrt = new ByrtRobot(hardwareMap, telemetry);

        waitForStart();
        runtime.reset();

        while(opModeIsActive()) {
            double drive = (-gamepad1.left_stick_y);
            double turn = gamepad1.right_stick_x;
            if (gamepad1.left_stick_y < -0.13 || gamepad1.left_stick_y > 0.13) {
                double leftPower = Range.clip(drive + turn, -1.0, 1.0);
                double rightPower = Range.clip(drive - turn, -1.0, 1.0);

                byrt.straightDrive(leftPower, rightPower);
            } else if (gamepad1.left_stick_x < -0.13 || gamepad1.left_stick_x > 0.13) {
                byrt.strafeLeft(gamepad1.left_stick_x);
            } else {
                byrt.straightDrive(0.0, 0.0);
            }

            if (gamepad1.left_trigger > 0.13) {
                byrt.nomNomNom(Range.clip(gamepad1.left_trigger, 0.0, 1.0));
            } else {
                byrt.nomNomNom(0.0);
            }

            if (gamepad1.dpad_up) {
                byrt.lift(0.5);
            } else if (gamepad1.dpad_down) {
                byrt.lift(-0.5);
            } else {
                byrt.lift(0.0);
            }

            if(gamepad1.a && !debounceA) {
                currentSpinnerPosition++;
                currentSpinnerPosition %= spinnerPositions.length;
                byrt.setSpinnerPosition(spinnerPositions[currentSpinnerPosition]);
            } else if (gamepad1.b) {
                currentSpinnerPosition = 0;
                byrt.setSpinnerPosition(SpinnerPosition.IN);
            }

            if(gamepad1.x && !debounceX) {
                byrt.setClawOpen(!byrt.isClawOpen());
            }

            if(gamepad1.y && !debounceY) {
                byrt.setFlickerOut(!byrt.isFlickerOut());
            }

            debounceA = gamepad1.a;
            debounceX = gamepad1.x;
            debounceY = gamepad1.y;

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }
    }
}