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

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;
import org.firstinspires.ftc.teamcode.robot.PyppynRobot;
import org.firstinspires.ftc.teamcode.robot.Robot;

/**
 * This file illustrates the concept of driving a path based on time.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backwards for 1 Second
 *   - Stop and close the claw.
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Auto1", group="Pyppyn")
public class Auto1 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        PyppynRobot pyppyn = new PyppynRobot(hardwareMap, telemetry);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        telemetry.addData("started", "started");

        int leftTicks = 10;
        int rightTicks = 10;
        double speed = 1;

        int frontLeftTarget = pyppyn.frontLeft.getCurrentPosition() + leftTicks;
        int backLeftTarget = pyppyn.backLeft.getCurrentPosition() + leftTicks;
        int frontRightTarget = pyppyn.frontRight.getCurrentPosition() - rightTicks;
        int backRightTarget = pyppyn.backRight.getCurrentPosition() - rightTicks;

        pyppyn.frontLeft.setTargetPosition(frontLeftTarget);
        pyppyn.backLeft.setTargetPosition(backLeftTarget);
        pyppyn.frontRight.setTargetPosition(frontRightTarget);
        pyppyn.backRight.setTargetPosition(backRightTarget);

        // Turn On RUN_TO_POSITION
        pyppyn.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pyppyn.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pyppyn.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pyppyn.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // reset the timeout time and start motion.
        pyppyn.frontLeft.setPower(speed);
        pyppyn.backLeft.setPower(speed);
        pyppyn.frontRight.setPower(-speed);
        pyppyn.backRight.setPower(-speed);


        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while (opModeIsActive() &&
                (pyppyn.frontLeft.isBusy() && pyppyn.backLeft.isBusy() && pyppyn.frontRight.isBusy() && pyppyn.backRight.isBusy())) {

            // Display it for the driver.
            telemetry.addData("status", "running");
            telemetry.update();
        }

        // Stop all motion;
        pyppyn.frontLeft.setPower(0);
        pyppyn.backLeft.setPower(0);
        pyppyn.frontRight.setPower(0);
        pyppyn.backRight.setPower(0);

        // Turn off RUN_TO_POSITION
        pyppyn.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pyppyn.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pyppyn.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pyppyn.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move
    }
}
