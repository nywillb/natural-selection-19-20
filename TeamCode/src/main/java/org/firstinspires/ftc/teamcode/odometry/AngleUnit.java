package org.firstinspires.ftc.teamcode.odometry;

public enum AngleUnit {
    DEGREE(1, "º", "degree"),
    MINUTES(1.0/60.0, "'", "arc minute"),
    SECONDS(1.0/3600.0, "\"", "arc second"),
    RADIANS(180.0 / Math.PI, "rad", "radian"),

    /**
     * @deprecated Use another unit instead. (NIST Special Publication 1038, Sec. 4.3.3)
     */
    @Deprecated
    GRADIAN(0.9, "grad", "gradian");

    double degrees;
    String suffix;
    String name;

    AngleUnit(double degrees, String suffix, String name) {
        this.degrees = degrees;
        this.suffix = suffix;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
