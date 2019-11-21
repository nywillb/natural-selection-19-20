package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDLog;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDLogItem;
import org.json.JSONException;

import static java.lang.Thread.sleep;

public class PyppynRobot implements Robot {

    public static final double JOYSTICK_THRESHOLD = 0.13;
    public static final double JOYSTICK_THRESHOLD_SQUARED = Math.pow(JOYSTICK_THRESHOLD, 2);

    public static final double MAX_LIFT_SPEED = 0.5;
    public static final double MAX_CLAW_SPEED = 0.3;

    public static final double MAXIMUM_DRIVE_POWER = 0.8;
    public static final double MIN_DRIVE_POWER = -MAXIMUM_DRIVE_POWER;
    public static final double SLOW_MODE_MAX_POWER = 0.2;
    public static final double SLOW_MODE_MIN_POWER = -SLOW_MODE_MAX_POWER;

    public static final double SPIN_SPEED = 0.5;
    public static final double SLOW_MODE_SPIN_SPEED = 0.3;

    public static final BNO055IMU.AngleUnit INTERNAL_ANGLE_UNIT = BNO055IMU.AngleUnit.RADIANS;
    public static final AngleUnit REPORTING_ANGLE_UNIT = AngleUnit.RADIANS;
    public static final double WHEEL_DIAMETER = 2.75;
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIAMETER * Math.PI;

    public abstract static class Odometry {
        public static final double WHEEL_DIAMETER = 50.0;
        public static final double WHEEL_DISTANCE = 83.1;
        public static final double ROTATIONS_PER_CIRCLE = 2 * WHEEL_DIAMETER / WHEEL_DISTANCE;
    }

    LinearOpMode opMode;
    Telemetry telemetry;

    HardwareMap hardwareMap;

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;

    public DcMotor lift;

    public DcMotor claw;
    public DcMotor leftSpinner;
    public DcMotor rightSpinner;

    public Servo clawServo;

    public BNO055IMU imu;

    private double referenceAngle = 0.0;

    private boolean clawOpen = false;

    @Deprecated
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

        setClawOpen(true);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = INTERNAL_ANGLE_UNIT;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu.initialize(parameters);

        this.introduceSelf(telemetry);
    }

    public boolean opModeIsActive() {
        return opMode.opModeIsActive();
    }

    public void idle() {
        opMode.idle();
    }

    public PyppynRobot(LinearOpMode opMode) {
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.telemetry = opMode.telemetry;

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

        setClawOpen(true);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        imu = hardwareMap.get(BNO055IMU.class, "imu");

        parameters.mode = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit = INTERNAL_ANGLE_UNIT;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled = false;

        imu.initialize(parameters);

        this.introduceSelf(opMode.telemetry);
    }

    public void calibrateIMU() throws InterruptedException {
        while (!imu.isSystemCalibrated()) {
            sleep(50);
        }
        referenceAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, REPORTING_ANGLE_UNIT).firstAngle;
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

    @Deprecated
    public void slowRotateClockwise(double power) {
        frontLeft.setPower(power);
        backRight.setPower(power);
    }

    @Deprecated
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

    public void openArms()
    {
        claw.setPower(SLOW_MODE_MAX_POWER);
    }

    public void closeArms()
    {
        claw.setPower(-SLOW_MODE_MAX_POWER);
    }

    public void nomNomNom(double power) {
        leftSpinner.setPower(power);
        rightSpinner.setPower(power);
    }

    public void rotateXTicks(double speed, int ticks, double timeoutS, boolean opModeIsActive) {
        // Determine new target position, and pass to motor controller
        int frontLeftTarget = frontLeft.getCurrentPosition() + ticks;
        int backLeftTarget = backLeft.getCurrentPosition() + ticks;
        int frontRightTarget = frontLeft.getCurrentPosition() + ticks;
        int backRightTarget = backLeft.getCurrentPosition() + ticks;

        frontLeft.setTargetPosition(frontLeftTarget);
        backLeft.setTargetPosition(backLeftTarget);
        frontRight.setTargetPosition(frontRightTarget);
        backRight.setTargetPosition(backRightTarget);

        // Turn On RUN_TO_POSITION
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        ElapsedTime runtime = new ElapsedTime();
        runtime.reset();

        backLeft.setPower(Math.abs(speed));
        backRight.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.

        while (opModeIsActive && (runtime.seconds() < timeoutS) && (frontLeft.isBusy() && backLeft.isBusy() && frontRight.isBusy() && backRight.isBusy())) {

        }

        // Stop all motion;
        stop();

        // Turn off RUN_TO_POSITION
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void moveDistance(int distance, double power, boolean correctSelf) {
        int target = frontRight.getCurrentPosition() - distance;

        int distanceToTarget = frontRight.getCurrentPosition() - target;
        while (opModeIsActive() && Math.abs(distanceToTarget) > 10) {
            distanceToTarget = frontRight.getCurrentPosition() - target;
            int negativeFactor = (distanceToTarget > 0 ? 1 : -1);
            if(!correctSelf) {
                negativeFactor = 1;
            }

            double factor = 1.0f;
            if (Math.abs(distanceToTarget) > (distance / 2)) {
                factor = 0.75f;
            }

            frontLeft.setPower(factor * negativeFactor * power);
            backLeft.setPower(factor * negativeFactor * power);
            frontRight.setPower(-factor * negativeFactor * power);
            backRight.setPower(-factor * negativeFactor * power);

            idle();

            telemetry.addData("Current position", frontRight.getCurrentPosition());
            telemetry.addData("Target position", target);
            telemetry.update();
        }

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public void rotateDistance(int distance, double power) {
        int target = frontRight.getCurrentPosition() - distance;

        int distanceToTarget = frontRight.getCurrentPosition() - target;
        while (opModeIsActive() && Math.abs(distanceToTarget) > 10) {
            distanceToTarget = frontRight.getCurrentPosition() - target;
            int negativeFactor = (distanceToTarget > 0 ? 1 : -1);

            double factor = 1.0f;
            if (Math.abs(distanceToTarget) > (distance / 2)) {
                factor = 0.75f;
            }

            frontLeft.setPower(-factor * negativeFactor * power);
            backLeft.setPower(-factor * negativeFactor * power);
            frontRight.setPower(-factor * negativeFactor * power);
            backRight.setPower(-factor * negativeFactor * power);

            idle();

            telemetry.addData("Current position", frontRight.getCurrentPosition());
            telemetry.addData("Target position", target);
            telemetry.update();
        }

        frontRight.setPower(0);
        frontLeft.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
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
