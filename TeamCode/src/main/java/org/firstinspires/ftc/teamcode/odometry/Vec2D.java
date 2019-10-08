package org.firstinspires.ftc.teamcode.odometry;

/**
 * A class to represent 2D Vectors.
 *
 * @author Md Abid Sikder
 */
public class Vec2D
{
    private double x;
    private double y;

    /**
     * @return the x
     */
    public double getX() {
        return x;
    }

    /**
     * @param x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public double getY() {
        return y;
    }

    /**
     * @param y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Transforms a vector to a double array, where the first element is
     * the x-component, and the second element is the y-component.
     *
     * @return an array.
     */
    public double[] toArray() {
        double[] arr = new double[2];
        arr[0] = this.getX();
        arr[1] = this.getY();

        return arr;
    }

    /**
     * Returns a deep copy of the object.
     *
     * @return the deep copy.
     */
    public Vec2D getCopy() {
        Vec2D copy = new Vec2D();
        copy.setX(this.getX());
        copy.setY(this.getY());

        return copy;
    }

    /**
     * Adds a vector's components to this one.
     * Modifies the current vector.
     *
     * @param vec
     */
    public void addVec(Vec2D vec) {
        this.setX(this.getX() + vec.getX());
        this.setY(this.getY() + vec.getY());
    }

    /**
     * Multiply the vector by a scalar.
     * Modifies the current vector.
     *
     * @param scalar
     */
    public void timesScalar(double scalar) {
        this.setX(this.getX() * scalar);
        this.setY(this.getY() * scalar);
    }

    /**
     * Instantiates a zero filled vector.
     *
     */
    public Vec2D() {
        this(0, 0);
    }

    /**
     * Instantiates a user-specified vector.
     *
     * @param x
     * @param y
     */
    public Vec2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the string representation, which is just the contained x and y values.
     */
    public String toString() {
        StringBuilder builder = new StringBuilder("");
        builder.append("(");
        builder.append(this.getX());
        builder.append(",");
        builder.append(this.getY());
        builder.append(")");

        return builder.toString();
    }
}
