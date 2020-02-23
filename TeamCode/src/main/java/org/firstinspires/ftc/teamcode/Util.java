package org.firstinspires.ftc.teamcode;

public class Util {
    public static double calculateAngularDistance(double angle1, double angle2) {
        double a = angle1 - angle2;
        a = floorMod((a + 180), 360 - 180);
        return a;
    }

    public static double floorMod(double a, double n) {
        return (a % n + n) % n;
    }
}
