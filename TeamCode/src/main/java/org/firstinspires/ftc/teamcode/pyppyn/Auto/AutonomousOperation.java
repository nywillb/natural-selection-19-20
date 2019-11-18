package org.firstinspires.ftc.teamcode.pyppyn.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Position;
import org.firstinspires.ftc.teamcode.robot.PyppynRobot;

import static org.firstinspires.ftc.teamcode.Position.BUILDING_ZONE;

public abstract class AutonomousOperation extends LinearOpMode {
    public abstract Alliance getAlliance();
    public abstract Position getPosition();

    private ElapsedTime runtime = new ElapsedTime();
    private PyppynRobot pyppyn;

    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        Alliance alliance = getAlliance();
        Position position = getPosition();

        pyppyn = new PyppynRobot(this);

        telemetry.addData("Team", alliance);
        telemetry.addData("Position", position);
        telemetry.addData("Status", "Ready to crush this match!");

        telemetry.update();

        waitForStart();

        if(position == BUILDING_ZONE) {
            while(opModeIsActive()) {
                telemetry.addData("Status", "Running auto!");
                telemetry.update();
                runtime.reset();

                pyppyn.moveDistance(200, 0.5);

                break;
            }
        }
    }
}
