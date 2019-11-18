package org.firstinspires.ftc.teamcode.odometry;

import static org.firstinspires.ftc.teamcode.odometry.AngleUnit.RADIANS;
import static org.firstinspires.ftc.teamcode.odometry.DistanceUnit.MILLIMETERS;

public class Odometry {
    private final Distance WHEEL_DIAMETER;
    private final Distance WHEEL_DISTANCE;
    private final double TICKS_PER_ROTATION;
    private Distance x;
    private Distance y;
    private Angle theta;

    private Distance vx = new Distance(0, MILLIMETERS);
    private Distance vy = new Distance(0, MILLIMETERS);
    private Angle vtheta = new Angle(0, RADIANS);

    public Odometry(Distance WHEEL_DIAMETER, Distance WHEEL_DISTANCE, double TICKS_PER_ROTATION, Distance startX, Distance startY, Angle startTheta) {
        this.WHEEL_DISTANCE = WHEEL_DISTANCE;
        this.WHEEL_DIAMETER = WHEEL_DIAMETER;
        this.TICKS_PER_ROTATION = TICKS_PER_ROTATION;
        this.x = startX;
        this.y = startY;
        this.theta = startTheta;
    }

    public Distance getXVelocity(Distance aVelocity, Distance bVelocity, Distance cVelocity, Angle robotAngle) {
        return (aVelocity.minus(bVelocity.times(2.0)).plus(cVelocity)).times(Math.cos(robotAngle.in(RADIANS))).plus((aVelocity.times(-1.0).plus(cVelocity)).times(Math.sin(robotAngle.in(RADIANS))).dividedBy(2.0));
    }

    public Distance getYVelocity(Distance aVelocity, Distance bVelocity, Distance cVelocity, Angle robotAngle) {
        return (aVelocity.minus(bVelocity.times(2.0)).plus(cVelocity)).times(Math.cos(robotAngle.in(RADIANS))).plus((aVelocity.times(-1.0).plus(cVelocity)).times(Math.sin(robotAngle.in(RADIANS))).dividedBy(2.0));
    }

    public Angle getAngularVelocity(Distance aVelocity, Distance bVelocity, Distance cVelocity) {
        return new Angle((aVelocity.in(MILLIMETERS) + cVelocity.in(MILLIMETERS))/(2*WHEEL_DISTANCE.in(MILLIMETERS)), RADIANS);
    }
}
