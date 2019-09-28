package org.firstinspires.ftc.teamcode.pyppyn;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.robot.PyppynRobot;

@TeleOp(name = "\uD83D\uDD04 Pyppyn", group = "Pyppyn")
public class Pyppyn extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

    private final double MAX_LIFT_SPEED = 0.5;
    private final double MAX_DRIVE_SPEED = 0.8;
    private final double MIN_DRIVE_SPEED = -0.8;
    private final double SLOW_MODE_SPEED = 0.3;
    private final double SPIN_SPEED = 0.5;
    private final double SLOW_MODE_SPIN_SPEED = 0.3;
    private final double MAX_CLAW_SPEED = 0.3;

    private boolean debounce2A = false;
    private boolean debounce2B = false;
    private boolean debounce2X = false;
    private boolean debounce2Y = false;


    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize Pyppyn
        PyppynRobot pyppyn = new PyppynRobot(hardwareMap, telemetry);

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            boolean isSlowMode = gamepad1.right_bumper;

            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            if (gamepad1.left_stick_y < -0.13 || gamepad1.left_stick_y > 0.13) {
                if(isSlowMode){
                    double leftPower = Range.clip(drive + turn, -SLOW_MODE_SPEED, SLOW_MODE_SPEED);
                    double rightPower = Range.clip(drive - turn, -SLOW_MODE_SPEED, SLOW_MODE_SPEED);
                    pyppyn.straightDrive(leftPower, rightPower);
                } else {
                    double leftPower = Range.clip(drive + turn, MIN_DRIVE_SPEED, MAX_DRIVE_SPEED);
                    double rightPower = Range.clip(drive - turn, MIN_DRIVE_SPEED, MAX_DRIVE_SPEED);
                    pyppyn.straightDrive(leftPower, rightPower);
                }
            } else if (gamepad1.right_stick_x > 0.13) {
                if (isSlowMode) pyppyn.slowRotateCounterclockwise(SLOW_MODE_SPIN_SPEED);
                else pyppyn.rotateCounterclockwise(SPIN_SPEED);
            } else if (gamepad1.right_stick_x < -0.13) {
                if (isSlowMode) pyppyn.slowRotateClockwise(SLOW_MODE_SPIN_SPEED);
                else pyppyn.rotateClockwise(SPIN_SPEED);
            } else if (gamepad1.left_stick_x < -0.13 || gamepad1.left_stick_x > 0.13) {
                pyppyn.strafeRight(gamepad1.left_stick_x);
            } else {
                pyppyn.stop();
            }



            if(gamepad2.left_trigger > 0.13) {
                pyppyn.lift(Range.clip(gamepad2.left_trigger, 0.0, MAX_LIFT_SPEED));
            } else if(gamepad2.right_trigger > 0.13) {
                pyppyn.lift(-Range.clip(gamepad2.right_trigger, 0.0, MAX_LIFT_SPEED));
            } else {
                pyppyn.lift(0.0);
            }


            if (gamepad2.left_stick_y > 0.13 || gamepad2.left_stick_y < -0.13) {
                pyppyn.moveClaw(Range.clip(gamepad2.left_stick_y, -MAX_CLAW_SPEED, MAX_CLAW_SPEED));
            } else pyppyn.moveClaw(0.0);

            if (gamepad2.a) pyppyn.nomNomNom(-1.0);
            if (gamepad2.b) pyppyn.nomNomNom(1.0);
            else pyppyn.nomNomNom(0.0);


            if (gamepad2.y && !debounce2Y) {
                pyppyn.setClawOpen(!pyppyn.isClawOpen());
            }

            debounce2A = gamepad2.a;
            debounce2B = gamepad2.b;
            debounce2X = gamepad2.x;
            debounce2Y = gamepad2.y;

            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.update();
        }

    }
}
