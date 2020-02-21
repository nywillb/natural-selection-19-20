package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Robot is an interface that implements a robot
 */
@Deprecated
public interface Robot {
    /**
     * Each robot has a name, this function returns the name
     *
     * @return The robot's name
     */
    public String getName();

    /**
     * Each robot can introduce itself, this function allows the robot to introduce itself
     *
     * @param telemetry The telemetry object, that allows the robot to interact with it
     */
    public void introduceSelf(Telemetry telemetry);
}

