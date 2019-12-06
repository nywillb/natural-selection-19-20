package org.firstinspires.ftc.teamcode.pyppyn.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Position;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDLog;
import org.firstinspires.ftc.teamcode.intelligentdesign.MatchPhase;
import org.firstinspires.ftc.teamcode.robot.PyppynRobot;
import org.json.JSONException;

import java.io.IOException;

import static org.firstinspires.ftc.teamcode.Position.BUILDING_ZONE;

public abstract class AutonomousOperation extends LinearOpMode {
    IDLog id;
    private ElapsedTime runtime = new ElapsedTime();
    private PyppynRobot pyppyn;

    public abstract Alliance getAlliance();

    public abstract Position getPosition();

    @Override
    public void runOpMode() throws InterruptedException {
        try {
            telemetry.addData("Status", "Initializing...");
            telemetry.update();

            id = new IDLog("IMU Values", MatchPhase.AUTONOMOUS, getPosition(), getAlliance());

            Alliance alliance = getAlliance();
            Position position = getPosition();

            pyppyn = new PyppynRobot(this);

            telemetry.addData("Team", alliance);
            telemetry.addData("Position", position);
            telemetry.addData("Status", "Ready to crush this match!");

            telemetry.update();



            waitForStart();
            id.startMatch();

//            pyppyn.rotateDistance(360, .7);

            if (position == BUILDING_ZONE) {
                while (opModeIsActive()) {
                    telemetry.addData("Status", "Running auto!");
                    telemetry.update();

                    runtime.reset();

                    pyppyn.liftDistance(400, 0.5);

                    runtime.reset();
                    pyppyn.moveDistance(1500, 0.3, false, id, 7, runtime);

                    pyppyn.liftDistance(-400, 0.5);
                    if(alliance == Alliance.RED) {
                        runtime.reset();
                        pyppyn.moveDistance(-900, 0.8, true, id, 7, runtime);
                        pyppyn.liftDistance(400, 0.5);
                        while(runtime.seconds() < 1.00) {
                            pyppyn.straightDrive(0.6, 0.6);
                        }
                        runtime.reset();
                        while(runtime.seconds() < 1.0) {
                            pyppyn.straightDrive(-PyppynRobot.SLOW_MODE_MAX_POWER, -PyppynRobot.SLOW_MODE_MAX_POWER);
                        }
                    } else if(alliance == Alliance.BLUE) {
                        runtime.reset();
                        pyppyn.moveDistance(-900, 0.8, true, id, 7, runtime);
                        pyppyn.liftDistance(400, 0.5);
                        while(runtime.seconds() < 1.00) {
                            pyppyn.straightDrive(0.6, 0.6);
                        }
                        runtime.reset();
                        while(runtime.seconds() < 1.0) {
                            pyppyn.straightDrive(-PyppynRobot.SLOW_MODE_MAX_POWER, -PyppynRobot.SLOW_MODE_MAX_POWER);
                        }
                    }


//                    runtime.reset();
//                    if(alliance == Alliance.RED) {
//                        while(runtime.seconds() < 4) {
//                            pyppyn.straightDrive(-0.8, -0.5);
//                        }
//                    } else if(alliance == Alliance.BLUE) {
//                        while(runtime.seconds() < 4) {
//                            pyppyn.straightDrive(-0.5, -0.8);
//                        }
//                    }
                    pyppyn.stop();


//                try {
//                    pyppyn.moveDistance(1291, 0.3, false, id);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
////                pyppyn.rotateDistance(67, 0.5);
//
//                runtime.reset();
//                while(runtime.seconds() <= 0.3) {
//                    pyppyn.lift(-PyppynRobot.SLOW_MODE_MAX_POWER);
//                }
//                pyppyn.lift(0.0);
//
//                try {
//                    pyppyn.moveDistance(-1391, 0.5, true, id);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                runtime.reset();
//                while(runtime.seconds() <= 3) {
//                    pyppyn.lift(PyppynRobot.SLOW_MODE_MAX_POWER);
//                }
//                pyppyn.lift(0.0);
//
//                runtime.reset();
//                while (runtime.seconds() <= 3) {
//                    pyppyn.straightDrive(-0.2, -0.2);
//                }
//                pyppyn.stop();
//
//
                    break;
                }
            }
            id.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
