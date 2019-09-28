package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class PyppynRobot implements Robot {
    HardwareMap hardwareMap;

    DcMotor frontLeft;
    DcMotor frontRight;
    DcMotor backLeft;
    DcMotor backRight;

    DcMotor lift;

    DcMotor claw;
    DcMotor leftSpinner;
    DcMotor rightSpinner;

    Servo clawServo;

    private boolean clawOpen = false;

    public PyppynRobot(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;

        frontLeft = hardwareMap.get(DcMotor.class, "front_left");
        frontRight = hardwareMap.get(DcMotor.class, "front_right");
        backLeft = hardwareMap.get(DcMotor.class, "back_left");
        backRight = hardwareMap.get(DcMotor.class, "back_right");

        lift = hardwareMap.get(DcMotor.class, "lift");

        claw = hardwareMap.get(DcMotor.class, "claw");

        leftSpinner = hardwareMap.get(DcMotor.class, "left_spinner");
        rightSpinner = hardwareMap.get(DcMotor.class, "right_spinner");


        clawServo = hardwareMap.get(Servo.class, "claw_servo");

        frontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        lift.setDirection(DcMotorSimple.Direction.FORWARD);

        leftSpinner.setDirection(DcMotorSimple.Direction.FORWARD);
        rightSpinner.setDirection(DcMotorSimple.Direction.REVERSE);

        claw.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        claw.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightSpinner.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        clawServo.setPosition(0.62);

        this.introduceSelf(telemetry);
    }

    public boolean isClawOpen() {
        return clawOpen;
    }

    public void setClawOpen(boolean clawOpen) {
        if (clawOpen) {
            clawServo.setPosition(0.92);
        } else {
            clawServo.setPosition(0.76);
        }

        this.clawOpen = clawOpen;
    }

    public void rotateClockwise(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    public void rotateCounterclockwise(double power) {
        frontLeft.setPower(-power);
        frontRight.setPower(-power);
        backLeft.setPower(-power);
        backRight.setPower(-power);
    }

    public void slowRotateClockwise(double power) {
        frontLeft.setPower(power);
        backRight.setPower(power);
    }

    public void slowRotateCounterclockwise(double power) {
        frontLeft.setPower(-power);
        backRight.setPower(-power);
    }

    public void strafeRight(double power) {
        frontLeft.setPower(power);
        backLeft.setPower(-power);
        frontRight.setPower(power);
        backRight.setPower(-power);
    }

    public void strafeLeft(double power) {
        frontLeft.setPower(-power);
        backLeft.setPower(power);
        frontRight.setPower(-power);
        backRight.setPower(power);
    }

    public void straightDrive(double leftPower, double rightPower) {
        frontLeft.setPower(leftPower);
        backLeft.setPower(leftPower);
        frontRight.setPower(-rightPower);
        backRight.setPower(-rightPower);
    }

    public void lift(double power) {
        lift.setPower(power);
    }

    public void stop() {
        frontLeft.setPower(0.0);
        backLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backRight.setPower(0.0);
    }

    public void moveClaw(double power) {
        claw.setPower(power);
    }

    public void nomNomNom(double power) {
        leftSpinner.setPower(power);
        rightSpinner.setPower(power);
    }

    @Override
    public String getName() {
        return "Pyppyn";
    }

    @Override
    public void introduceSelf(Telemetry telemetry) {
        telemetry.addData("Hello!", this.getName() + " reporting for duty!");
        telemetry.update();
    }
}
