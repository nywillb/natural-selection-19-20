package org.firstinspires.ftc.teamcode.pyppyn;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.robot.PyppynRobot;

@TeleOp(name = "\uD83D\uDD04 Pyppyn", group = "Pyppyn")
public class Pyppyn extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();

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

            double drive = -gamepad1.left_stick_y;
            double turn = gamepad1.right_stick_x;
            if (gamepad1.left_stick_y < -pyppyn.JOYSTICK_THRESHOLD || gamepad1.left_stick_y > pyppyn.JOYSTICK_THRESHOLD) {
                double leftPower = Range.clip(drive + turn, pyppyn.MIN_DRIVE_SPEED, pyppyn.MAX_DRIVE_SPEED);
                double rightPower = Range.clip(drive - turn, pyppyn.MIN_DRIVE_SPEED, pyppyn.MAX_DRIVE_SPEED);
                pyppyn.straightDrive(leftPower, rightPower);
            } else if ((gamepad1.right_stick_x > pyppyn.JOYSTICK_THRESHOLD) || gamepad1.right_bumper) {
                if (gamepad1.right_trigger>.5) pyppyn.slowRotateClockwise(pyppyn.SLOW_MODE_SPIN_SPEED);
                else pyppyn.rotateClockwise(pyppyn.SPIN_SPEED);
            } else if ((gamepad1.right_stick_x < -pyppyn.JOYSTICK_THRESHOLD) || gamepad1.left_bumper) {
                if (gamepad1.right_trigger>.5) pyppyn.slowRotateCounterclockwise(pyppyn.SLOW_MODE_SPIN_SPEED);
                else pyppyn.rotateCounterclockwise(pyppyn.SPIN_SPEED);
            } else if (gamepad1.left_stick_x < -pyppyn.JOYSTICK_THRESHOLD || gamepad1.left_stick_x > pyppyn.JOYSTICK_THRESHOLD) {
                pyppyn.strafeRight(gamepad1.left_stick_x);
            } else if (gamepad1.dpad_up) {
                pyppyn.straightDrive(pyppyn.SLOW_MODE_SPEED, pyppyn.SLOW_MODE_SPEED);
            } else if (gamepad1.dpad_down) {
                pyppyn.straightDrive(-pyppyn.SLOW_MODE_SPEED, -pyppyn.SLOW_MODE_SPEED);
            } else if (gamepad1.dpad_left) {
                pyppyn.strafeLeft(pyppyn.SLOW_MODE_SPEED);
            } else if (gamepad1.dpad_right) {
                pyppyn.strafeRight(pyppyn.SLOW_MODE_SPEED);
            } else {
                pyppyn.stop();
            }

            if(gamepad2.left_trigger > pyppyn.JOYSTICK_THRESHOLD) {
                pyppyn.lift(Range.clip(gamepad2.left_trigger, 0.0, pyppyn.MAX_LIFT_SPEED));
            } else if(gamepad2.right_trigger > pyppyn.JOYSTICK_THRESHOLD) {
                pyppyn.lift(-Range.clip(gamepad2.right_trigger, 0.0, pyppyn.MAX_LIFT_SPEED));
            } else {
                pyppyn.lift(0.0);
            }


            if (gamepad2.left_stick_y > pyppyn.JOYSTICK_THRESHOLD || gamepad2.left_stick_y < -pyppyn.JOYSTICK_THRESHOLD) {
                pyppyn.moveClaw(Range.clip(gamepad2.left_stick_y, -pyppyn.MAX_CLAW_SPEED, pyppyn.MAX_CLAW_SPEED));
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