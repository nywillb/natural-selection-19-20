package org.firstinspires.ftc.teamcode.odometry;

public class Distance {
    private DistanceUnit defaultUnit;
    private double millimeters;

    public Distance(double distance, DistanceUnit unit) {
        millimeters = unit.millimeter * distance;
    }

    public double in(DistanceUnit unit) {
        return millimeters/unit.millimeter;
    }

    public double inDefaultUnit() {
        return millimeters/defaultUnit.millimeter;
    }

    public DistanceUnit getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(DistanceUnit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    @Override
    public String toString() {
        return millimeters / defaultUnit.millimeter + defaultUnit.suffix;
    }

    public String toString(DistanceUnit unit) {
        return millimeters / unit.millimeter + unit.suffix;
    }

    public Distance times(Double multiplier) {
        return new Distance(millimeters * multiplier, DistanceUnit.MILLIMETERS);
    }

    public Distance times(Distance multiplier) {
        return new Distance(millimeters * multiplier.millimeters, DistanceUnit.MILLIMETERS);
    }

    public Distance dividedBy(Double divisor) {
        return new Distance(millimeters / divisor, DistanceUnit.MILLIMETERS);
    }

    public Distance dividedBy(Distance divisor) {
        return new Distance(millimeters / divisor.millimeters, DistanceUnit.MILLIMETERS);
    }

    public Distance plus(Distance summand) {
        return new Distance(millimeters + summand.millimeters, DistanceUnit.MILLIMETERS);
    }

    public Distance minus(Distance subtrahend) {
        return new Distance(millimeters - subtrahend.millimeters, DistanceUnit.MILLIMETERS);
    }
}
