package org.firstinspires.ftc.teamcode.odometry;

public class Angle {
    private AngleUnit defaultUnit;
    private double degrees;

    public Angle(double measure, AngleUnit unit) {
        degrees = unit.degrees * measure;
        defaultUnit = unit;
    }

    public double in(AngleUnit unit) {
        return degrees/unit.degrees;
    }

    public double inDefaultUnit() {
        return degrees/defaultUnit.degrees;
    }

    public AngleUnit getDefaultUnit() {
        return defaultUnit;
    }

    public void setDefaultUnit(AngleUnit defaultUnit) {
        this.defaultUnit = defaultUnit;
    }

    @Override
    public String toString() {
        return degrees / defaultUnit.degrees + defaultUnit.suffix;
    }

    public String toString(AngleUnit unit) {
        return degrees / unit.degrees + unit.suffix;
    }

    public Angle times(Double multiplier) {
        return new Angle(degrees * multiplier, AngleUnit.DEGREE);
    }

    public Angle times(AngleUnit multiplier) {
        return new Angle(degrees * multiplier.degrees, AngleUnit.DEGREE);
    }

    public Angle dividedBy(Double divisor) {
        return new Angle(degrees / divisor, AngleUnit.DEGREE);
    }

    public Angle dividedBy(AngleUnit divisor) {
        return new Angle(degrees / divisor.degrees, AngleUnit.DEGREE);

    }

    public Angle plus(AngleUnit summand) {
        return new Angle(degrees + summand.degrees, AngleUnit.DEGREE);
    }

    public Angle minus(AngleUnit subtrahend) {
        return new Angle(degrees - subtrahend.degrees, AngleUnit.DEGREE);
    }
}
