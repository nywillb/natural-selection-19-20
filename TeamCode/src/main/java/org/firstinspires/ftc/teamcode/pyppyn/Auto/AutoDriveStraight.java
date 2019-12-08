package org.firstinspires.ftc.teamcode.pyppyn.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Alliance;
import org.firstinspires.ftc.teamcode.Position;
import org.firstinspires.ftc.teamcode.intelligentdesign.IDLog;
import org.firstinspires.ftc.teamcode.intelligentdesign.MatchPhase;
import org.firstinspires.ftc.teamcode.robot.PyppynRobot;
import org.json.JSONException;

@Autonomous(name="⬆️️ Drive Straight Auto")
public class AutoDriveStraight extends LinearOpMode {

    private PyppynRobot pyppyn;
    private ElapsedTime time;


    @Override
    public void runOpMode() throws InterruptedException {
        pyppyn = new PyppynRobot(this);
        time = new ElapsedTime();

        waitForStart();
        time.reset();
        try {
            pyppyn.moveDistance(200, 0.5, true, new IDLog("Fake ID", MatchPhase.AUTONOMOUS), 5, time);
        } catch (JSONException e) {
            e.printStackTrace   ();
        }
    }
}
