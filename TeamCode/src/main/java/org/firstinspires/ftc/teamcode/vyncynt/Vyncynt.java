package org.firstinspires.ftc.teamcode.vyncynt;

import com.qualcomm.robotcore.hardware.*;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Vyncynt {
    // Odometry constants
    public static final double ODOMETRY_WHEEL_DIAMETER = 2.0;
    public static final double ODOMETRY_WHEEL_CIRCUMFERENCE = ODOMETRY_WHEEL_DIAMETER * Math.PI;
    public static final double TICKS_PER_ODOMETER_ROTATION = 200.0;
    public static final double ODOMETER_TICKS_PER_INCH = TICKS_PER_ODOMETER_ROTATION / ODOMETRY_WHEEL_CIRCUMFERENCE;

    // Drive constants
    public static final double MAXIMUM_DRIVE_POWER = 0.8;
    public static final double MINIMUM_DRIVE_POWER = -MAXIMUM_DRIVE_POWER;
    public static final double SLOW_MODE_MAX_POWER = 0.2;
    public static final double SLOW_MODE_MIN_POWER = -SLOW_MODE_MAX_POWER;
    public static final double SPIN_SPEED = 0.5;
    public static final double SLOW_MODE_SPIN_SPEED = 0.3;

    // Nom constants
    public static final double REVERSE_NOM_SPEED = -0.5;

    // Lift constants
    public static final double LIFT_SPEED = 0.3;

    DcMotor fl;
    DcMotor fr;
    DcMotor bl;
    DcMotor br;
    DcMotor lift;
    DcMotor intakeL;
    DcMotor intakeR;

    Servo intakeServo;
    Servo flPlatform;
    Servo blPlatform;
    Servo frPlatform;
    Servo brPlatform;

    NormalizedColorSensor colorSensor;

    public Vyncynt(HardwareMap hardwareMap, Telemetry telemetry) {
        telemetry.addData("Status", "Initializing");
        telemetry.update();

        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");

        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);

        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lift = hardwareMap.get(DcMotor.class, "lift");
        intakeL = hardwareMap.get(DcMotor.class, "intakeL");
        intakeR = hardwareMap.get(DcMotor.class, "intakeR");

        lift.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeL.setDirection(DcMotorSimple.Direction.REVERSE);
        intakeR.setDirection(DcMotorSimple.Direction.FORWARD);

        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intakeR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


//        intakeServo = hardwareMap.get(Servo.class, "intakeServo");
//        flPlatform = hardwareMap.get(Servo.class, "flPlatform");
//        frPlatform = hardwareMap.get(Servo.class, "frPlatform");
//        blPlatform = hardwareMap.get(Servo.class, "blPlatform");
//        brPlatform = hardwareMap.get(Servo.class, "brPlatform");

        colorSensor = hardwareMap.get(NormalizedColorSensor.class, "colorSensor");

        telemetry.addData("Status", "Vyncynt reporting for duty!");
        telemetry.update();
    }

    public void straightDrive(double leftPower, double rightPower) {
        fl.setPower(leftPower);
        bl.setPower(leftPower);
        fr.setPower(-rightPower);
        br.setPower(-rightPower);
    }

    public void strafeRight(double power) {
        fl.setPower(power);
        bl.setPower(-power);
        fr.setPower(power);
        br.setPower(-power);
    }

    public void strafeLeft(double power) {
        fl.setPower(-power);
        bl.setPower(power);
        fr.setPower(-power);
        br.setPower(power);
    }

    public void lift(double power) {
        lift.setPower(power);
    }

    public void nomNomNom(double power) {
        intakeL.setPower(power);
        intakeR.setPower(power);
    }

    public void rotateClockwise(double power) {
        fl.setPower(power);
        bl.setPower(power);
        fr.setPower(power);
        br.setPower(power);
    }

    public void rotateCounterclockwise(double power) {
        fl.setPower(-power);
        bl.setPower(-power);
        fr.setPower(-power);
        br.setPower(-power);
    }

    public void stop() {
        fl.setPower(0);
        bl.setPower(0);
        fr.setPower(0);
        br.setPower(0);
    }
}
