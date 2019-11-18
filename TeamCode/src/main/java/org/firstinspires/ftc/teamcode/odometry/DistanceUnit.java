package org.firstinspires.ftc.teamcode.odometry;

public enum DistanceUnit {
    MILLIMETERS(1, "mm", "millimeter"),
    CENTIMETERS(100, "cm", "centimeter"),
    METERS(1000, "m", "meter"),
    INCHES(25.4, "in", "inch"),
    FEET(304.8, "ft", "feet"),
    FLOOR_TILES(60.96, "tiles", "floor tiles"),
    ANGSTROM(0.00000010, "Å", "ångström"),
    WIFFLES(89.0, "wif", "wiffle"),
    SMOOTS(1700, "sm", "smoot"),
    BANANAS(179, "🍌", "banana"),

    /**
     * @deprecated use {@link DistanceUnit#METERS} instead.
     */
    @Deprecated
    METERES(1000, "m", "meter");

    double millimeter;
    String suffix;
    String name;

    DistanceUnit(double millimeter, String suffix, String name) {
        this.millimeter = millimeter;
        this.suffix = suffix;
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
