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
    public abstract Alliance getAlliance();
    public abstract Position getPosition();

    private ElapsedTime runtime = new ElapsedTime();
    private PyppynRobot pyppyn;

    IDLog id;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        id = new IDLog("Encoder Values", MatchPhase.AUTONOMOUS, getPosition(), getAlliance());

        Alliance alliance = getAlliance();
        Position position = getPosition();

        ElapsedTime runtime = new ElapsedTime();

        pyppyn = new PyppynRobot(this);

        telemetry.addData("Team", alliance);
        telemetry.addData("Position", position);
        telemetry.addData("Status", "Ready to crush this match!");

        telemetry.update();

        waitForStart();
        id.startMatch();

        if(position == BUILDING_ZONE) {
            while(opModeIsActive()) {
                telemetry.addData("Status", "Running auto!");
                telemetry.update();

                runtime.reset();
                while(runtime.seconds() <= 2) {
                    pyppyn.openArms();
                }
                pyppyn.moveClaw(0.0);

                runtime.reset();
                while(runtime.seconds() <= 1.3) {
                    pyppyn.lift(PyppynRobot.SLOW_MODE_MAX_POWER);
                }
                pyppyn.lift(0.0);

                pyppyn.moveDistance(1291, 0.3, false);
//                pyppyn.rotateDistance(67, 0.5);

                runtime.reset();
                while(runtime.seconds() <= 0.3) {
                    pyppyn.lift(-PyppynRobot.SLOW_MODE_MAX_POWER);
                }
                pyppyn.lift(0.0);

                pyppyn.moveDistance(-1091, 0.5, true);


                break;
            }
        }
        try {
            id.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }


}
