package org.firstinspires.ftc.teamcode.vyncynt;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Vyncynt", group = "0")
public class VyncyntTeleOp extends OpMode {
    public static final double JOYSTICK_THRESHOLD = 0.13;

    Vyncynt vyncynt;
    ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        vyncynt = new Vyncynt(hardwareMap, telemetry, false);
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        boolean isSlowMode = gamepad1.left_bumper;

        drive(isSlowMode);
        nom();
        lift();

        telemetry.addData("runtime", runtime.seconds());
        telemetry.update();
    }

    private void drive(boolean slowMode) {
        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        if (joystickActivated(gamepad1.left_stick_y)) {
            double leftPower = Range.clip(drive + turn, Vyncynt.MINIMUM_DRIVE_POWER, Vyncynt.MAXIMUM_DRIVE_POWER);
            double rightPower = Range.clip(drive - turn, Vyncynt.MINIMUM_DRIVE_POWER, Vyncynt.MAXIMUM_DRIVE_POWER);

            if (slowMode) {
                leftPower = Range.clip(drive + turn, Vyncynt.SLOW_MODE_MIN_POWER, Vyncynt.SLOW_MODE_MAX_POWER);
                rightPower = Range.clip(drive - turn, Vyncynt.SLOW_MODE_MIN_POWER, Vyncynt.SLOW_MODE_MAX_POWER);
            }

            vyncynt.straightDrive(leftPower, rightPower);
        } else if (joystickActivated(gamepad1.right_stick_x)) {
            double g1rightXmag = Math.abs(gamepad1.right_stick_x);
            // determine whether to rotate right or left and then rotate that way
            double power = slowMode ? Vyncynt.SLOW_MODE_SPIN_SPEED * g1rightXmag : Vyncynt.SPIN_SPEED * g1rightXmag;
            if (gamepad1.right_stick_x > 0) {
                vyncynt.rotateClockwise(power);
            } else {
                vyncynt.rotateCounterclockwise(power);
            }
        } else if (joystickActivated(gamepad1.left_stick_x)) {
            double leftX = Math.abs(gamepad1.left_stick_x);
            if (gamepad1.left_stick_x < 0) {
                vyncynt.strafeLeft(slowMode ? Vyncynt.SLOW_MODE_MAX_POWER : leftX);
            } else {
                vyncynt.strafeRight(slowMode ? Vyncynt.SLOW_MODE_MAX_POWER : leftX);
            }
        }
        // don't move if no motion controls are being activated
        else {
            vyncynt.stop();
        }
    }

    private void nom() {
        if(gamepad2.right_trigger > 0.3) {
            vyncynt.nomNomNom(Range.clip(gamepad2.right_trigger * 1.3, 0, 1));
        } else if (gamepad2.right_bumper) {
            vyncynt.nomNomNom(Vyncynt.REVERSE_NOM_SPEED);
        } else {
            vyncynt.nomNomNom(0);
        }

        if(gamepad2.left_trigger > 0.3) {
            vyncynt.closeClaw();
        } else {
            vyncynt.openClaw();
        }

        if(gamepad2.dpad_left) {
            vyncynt.slideForward();
        } else if (gamepad2.dpad_right) {
            vyncynt.slideBackward();
        } else {
            vyncynt.stopSlide();
        }
    }

    private void lift() {
        if(gamepad2.dpad_up) {
            vyncynt.lift(Vyncynt.LIFT_SPEED);
        } else if (gamepad2.dpad_down) {
            vyncynt.lift(-Vyncynt.LIFT_SPEED);
        } else {
            vyncynt.lift(0);
        }
    }

    private boolean joystickActivated(double joystick) {
        return (joystick < -JOYSTICK_THRESHOLD) || (joystick > JOYSTICK_THRESHOLD);
    }

}
