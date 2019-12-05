/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.pyppyn;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDLog;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDLogItem;
import org.firstinspires.ftc.teamcode.intelligentdesign.MatchPhase;
import org.firstinspires.ftc.teamcode.robot.PyppynRobot;
import org.json.JSONException;

import java.io.IOException;

/**
 * This file contains an example of an iterative (Non-Linear) "OpMode".
 * An OpMode is a 'program' that runs in either the autonomous or the teleop period of an FTC match.
 * The names of OpModes appear on the menu of the FTC Driver Station.
 * When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all iterative OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */
@TeleOp(name = "\uD83D\uDCDD Pyppyn (with Logging)", group = "Pyppyn")
public class PyppynID extends OpMode {

    public ElapsedTime runtime = new ElapsedTime();
    public IDLog id = new IDLog("Encoder Values", MatchPhase.TELEOP);

    double frontLeftInitial;
    double frontRightInitial;
    double backLeftInitial;
    double backRightInitial;
    double liftInitial;

    PyppynRobot pyppyn;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        pyppyn = new PyppynRobot(hardwareMap, telemetry);

        // raise the claw servo on initialization
        pyppyn.openArms();

        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");

        backLeftInitial = pyppyn.backLeft.getCurrentPosition();
        backRightInitial = pyppyn.backRight.getCurrentPosition();
        frontLeftInitial = pyppyn.frontLeft.getCurrentPosition();
        frontRightInitial = pyppyn.frontRight.getCurrentPosition();
        liftInitial = pyppyn.lift.getCurrentPosition();
    }

    /*
    * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime.reset();
    }

    public void drive(boolean slowMode) {
        // includes strafing and turning, in normal or slow mode
        // currently turning is not proportional to joystick displacement however

        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;

        // gamepad1 left joystick y: forward --> move forward, backward --> move backward
        if (joystickActivated(gamepad1.left_stick_y))
        {
            double leftPower = Range.clip(drive + turn, pyppyn.MIN_DRIVE_POWER, pyppyn.MAXIMUM_DRIVE_POWER);
            double rightPower = Range.clip(drive - turn, pyppyn.MIN_DRIVE_POWER, pyppyn.MAXIMUM_DRIVE_POWER);

            if (slowMode)
            {
                leftPower = Range.clip(drive + turn, pyppyn.SLOW_MODE_MIN_POWER, pyppyn.SLOW_MODE_MAX_POWER);
                rightPower = Range.clip(drive - turn, pyppyn.SLOW_MODE_MIN_POWER, pyppyn.SLOW_MODE_MAX_POWER);
            }

            pyppyn.straightDrive(leftPower, rightPower);
        }
        // gamepad1 right joystick x: strafing left or right
        else if (joystickActivated(gamepad1.right_stick_x))
        {
            double g1rightXmag = Math.abs(gamepad1.right_stick_x);
            // determine whether to rotate right or left and then rotate that way
            if (gamepad1.right_stick_x > 0)
            {
                pyppyn.rotateClockwise(slowMode ? pyppyn.SLOW_MODE_SPIN_SPEED*g1rightXmag : pyppyn.SPIN_SPEED*g1rightXmag);
            }
            else
            {
                pyppyn.rotateCounterclockwise(slowMode ? pyppyn.SLOW_MODE_SPIN_SPEED*g1rightXmag : pyppyn.SPIN_SPEED*g1rightXmag);
            }
        }
        else if (joystickActivated(gamepad1.left_stick_x))
        {
            double leftX = Math.abs(gamepad1.left_stick_x);
            if (gamepad1.left_stick_x < 0)
            {
                pyppyn.strafeLeft(slowMode ? pyppyn.SLOW_MODE_MAX_POWER : leftX);
            }
            else
            {
                pyppyn.strafeRight(slowMode ? pyppyn.SLOW_MODE_MAX_POWER : leftX);
            }
        }
        // don't move if no motion controls are being activated
        else
        {
            pyppyn.stop();
        }
    }

    /**
     * handles spinning intake, opening and closing claw, opening and closing the arms
     */
    public void intake()
    {

        // gamepad2 right trigger intakes blocks
        if (triggerActivated(gamepad2.right_trigger))
        {
            pyppyn.nomNomNom(-pyppyn.MAXIMUM_DRIVE_POWER);
        }
        // outtake blocks
        else if (gamepad2.right_bumper)
        {
            pyppyn.nomNomNom(pyppyn.MAXIMUM_DRIVE_POWER);
        }
        else
        {
            pyppyn.nomNomNom(0.0);
        }

        // claw servo
        // holding down left trigger closes claw, otherwise it lifts up
        if (triggerActivated(gamepad2.left_trigger))
        {
            pyppyn.setClawOpen(false);
        }
        else
        {
            pyppyn.setClawOpen(true);
        }

        // rightstick X opens and closes the arms
        if (joystickActivated(gamepad2.right_stick_x))
        {
            if (gamepad2.right_stick_x > 0)
            {
                pyppyn.openArms();
            }
            else
            {
                pyppyn.closeArms();
            }
        }
        else
        {
            pyppyn.moveClaw(0.0);
        }
    }

    public void lift()
    {
        // handles the lift going up and down
        // left joystick up is lift going up, vice-versa
        // dpad physical up is also lift up, and dpad physical down is also lift down
        if (joystickActivated(gamepad2.left_stick_y))
        {
            double g2leftY = -gamepad2.left_stick_y;
            // lift upwards proportionally by scaling based on how far the joystick is
            if (g2leftY > 0)
            {
                pyppyn.lift(pyppyn.MAXIMUM_DRIVE_POWER*g2leftY);
            }
            else
            {
                pyppyn.lift(-pyppyn.MAXIMUM_DRIVE_POWER*Math.abs(g2leftY));
            }
        }
        else if (gamepad2.dpad_down || gamepad2.dpad_up)
        {
            // the dpad physical direction we are pressing in, for up and down, is opposite in the code
            if (gamepad2.dpad_down)
            {
                pyppyn.lift(pyppyn.SLOW_MODE_MAX_POWER);
            }
            else if (gamepad2.dpad_up)
            {
                pyppyn.lift(-pyppyn.SLOW_MODE_MAX_POWER);
            }
        }
        else
        {
            pyppyn.lift(0.0);
        }
    }

    public boolean joystickActivated(double joystick)
    {
        return (joystick < -pyppyn.JOYSTICK_THRESHOLD) || (joystick > pyppyn.JOYSTICK_THRESHOLD);
    }

    public boolean triggerActivated(double trigger)
    {
        return trigger > 0.5;
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        boolean drivingSlowMode = gamepad1.left_bumper;
        drive(drivingSlowMode);
        intake();
        lift();

        try {
            id.addItem(new IDLogItem("backLeft", pyppyn.backLeft.getCurrentPosition() - backLeftInitial));
            id.addItem(new IDLogItem("backRight", pyppyn.backRight.getCurrentPosition() - backRightInitial));
            id.addItem(new IDLogItem("frontLeft", pyppyn.frontLeft.getCurrentPosition() - frontLeftInitial));
            id.addItem(new IDLogItem("frontRight", pyppyn.frontRight.getCurrentPosition() - frontRightInitial));
            id.addItem(new IDLogItem("lift", pyppyn.lift.getCurrentPosition() - liftInitial));
        } catch (JSONException e) {
            e.printStackTrace();
        } }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
        // stop pyppyn movement
        pyppyn.stop();
        try {
            id.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}
