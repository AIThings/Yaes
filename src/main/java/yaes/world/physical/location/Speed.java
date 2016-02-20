/*
 * Created on Sep 15, 2004
 */
package yaes.world.physical.location;

/**
 * 
 * 
 * <code>yaes.world.map.Speed</code>
 * 
 * The implementation of the speed class.
 * 
 * @author Ladislau Boloni (lboloni@eecs.ucf.edu)
 */
public class Speed {
    /**
     * Constructor function which creates a speed object from the polar
     * coordinates
     * 
     * @param angle
     * @param magnitude
     * @return
     */
    public static Speed createSpeedFromPolarCoordinates(double angle,
            double magnitude) {
        double newSpeedX = magnitude * Math.sin(angle);
        double newSpeedY = magnitude * Math.cos(angle);
        return new Speed(newSpeedX, newSpeedY);
    }

    private final double xSpeed;

    private final double ySpeed;

    /**
     * Creates the speed class from the cartesian components
     * 
     * @param speed
     * @param speed2
     */
    public Speed(double speed, double speed2) {
        super();
        xSpeed = speed;
        ySpeed = speed2;
    }

    public double getAngle() {
        return Math.atan2(xSpeed, ySpeed);
    }

    public double getMagnitude() {
        return Math.hypot(xSpeed, ySpeed);
    }

    /**
     * @return Returns the xSpeed.
     */
    public double getXSpeed() {
        return xSpeed;
    }

    /**
     * @return Returns the ySpeed.
     */
    public double getYSpeed() {
        return ySpeed;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("Speed (vx=" + xSpeed + ", vy=" + ySpeed + ", magn= "
                + getMagnitude() + ", angle=" + getAngle() + ")");
        return buffer.toString();
    }

}