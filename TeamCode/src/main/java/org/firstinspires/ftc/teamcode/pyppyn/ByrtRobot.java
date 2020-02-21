package org.firstinspires.ftc.teamcode.pyppyn;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.byrt.SpinnerPosition;
import org.firstinspires.ftc.teamcode.Robot;

public class ByrtRobot implements Robot {
    HardwareMap hardwareMap;

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    DcMotor nomLeft;
    DcMotor nomRight;

    DcMotor lift;

    Servo flicker;
    Servo spinner;
    Servo claw;

    private boolean flickerOut;
    private boolean clawOpen;

    private SpinnerPosition spinnerPosition;

    public ByrtRobot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;

        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");

        nomLeft = hardwareMap.get(DcMotor.class, "nom_left");
        nomRight = hardwareMap.get(DcMotor.class, "nom_right");

        lift = hardwareMap.get(DcMotor.class, "lift");

//        flicker = hardwareMap.get(Servo.class, "flicker");
//        spinner = hardwareMap.get(Servo.class, "spinner");
//        claw = hardwareMap.get(Servo.class, "claw");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        nomLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        nomRight.setDirection(DcMotorSimple.Direction.REVERSE);

        lift.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        nomLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        nomRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        this.introduceSelf(telemetry);
    }

    public boolean isFlickerOut() {
        return flickerOut;
    }

    public void setFlickerOut(boolean flickerOut) {
        if(flickerOut) {
            flicker.setPosition(0.88);
        } else {
            flicker.setPosition(0.53);
        }
        this.flickerOut = flickerOut;
    }

    public boolean isClawOpen() {
        return clawOpen;
    }

    public void setClawOpen(boolean clawOpen) {
        if(clawOpen) {
            claw.setPosition(0.55);
        } else {
            claw.setPosition(0.24);
        }
        this.clawOpen = clawOpen;
    }

    public SpinnerPosition getSpinnerPosition() {
        return spinnerPosition;
    }

    public void setSpinnerPosition(SpinnerPosition spinnerPosition) {
        if(spinnerPosition == SpinnerPosition.IN) {
            spinner.setPosition(0.12);
        } else if (spinnerPosition == SpinnerPosition.HALF) {
            spinner.setPosition(0.47);
        } else if (spinnerPosition == SpinnerPosition.OUT) {
            spinner.setPosition(0.81);
        }
        this.spinnerPosition = spinnerPosition;
    }

    @Override
    public String getName() {
        return "Byrt";
    }

    @Override
    public void introduceSelf(Telemetry telemetry) {
        telemetry.addData("Hello!", this.getName() + " reporting for duty!");
        telemetry.update();
    }

    public void leftMotors(double power) {
        frontLeft.setPower(power);
        backLeft.setPower(power);
    }

    public void rightMotors(double power) {
        frontRight.setPower(power);
        backRight.setPower(power);
    }

    public void nomNomNom(double power) {
        nomLeft.setPower(power);
        nomLeft.setPower(power);
    }

    public void lift(double power) {
        lift.setPower(power);
    }

    public void straightDrive(double leftPower, double rightPower) {
        this.leftMotors(leftPower);
        this.rightMotors(rightPower);
    }

    public void strafeLeft(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }

    public void strafeRight(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(power);
    }
}
