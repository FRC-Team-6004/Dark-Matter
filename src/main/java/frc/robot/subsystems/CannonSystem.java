package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Single unified subsystem managing all 1-3 PWM-controlled SPARK MAX canon motors.
 */
public class CannonSystem extends SubsystemBase {

    private double openVoltage = 1;
    private double closeVoltage = -1;

    public enum cannons {
        rightCannon,
        middleCannon,
        leftCannon,
    };

    // Declare the 3 independent PWM motor instances
    private final PWMSparkMax motorOne;
    private final PWMSparkMax motorTwo;
    private final PWMSparkMax motorThree;

    public CannonSystem() {
        // Instantiate each controller directly to its assigned hardware PWM port
        motorOne   = new PWMSparkMax(9);
        motorTwo   = new PWMSparkMax(8);
        motorThree = new PWMSparkMax(7);

        

        // Optional: Invert motors here if any spin backward by default
        // motorOne.setInverted(true);
    }

    @Override
    public void periodic() {
        // motorTwo.set(1);
        // motorThree.set(1);
        // motorOne.set(1);

        // Runs every 20ms. Keep empty or add logging if needed.
    }

    public void openCannon(cannons activeCannon)
    {
        System.out.println("Open");
        switch (activeCannon) {
            case rightCannon:
                motorOne.set(openVoltage);
                break;
            case middleCannon:
                motorTwo.set(openVoltage);
                break;
            case leftCannon:
                motorThree.set(openVoltage);
                break;
            default:
                break;
        }
    }

    public void closeCannon(cannons activeCannon)
    {
        System.out.println("Close");
        switch (activeCannon) {
            case rightCannon:
                motorOne.set(closeVoltage);
                break;
            case middleCannon:
                motorTwo.set(closeVoltage);
                break;
            case leftCannon:
                motorThree.set(closeVoltage);
                break;
            default:
                break;
        }
    }



    /**
     * Commands the first canon motor to a specific speed.
     * @param speed Motor speed from -1.0 to 1.0 (0.0 stops the motor)
     */
    public void setCanonOne(double speed) {
        motorOne.set(speed);
    }

    /**
     * Commands the second canon motor to a specific speed.
     * @param speed Motor speed from -1.0 to 1.0 (0.0 stops the motor)
     */
    public void setCanonTwo(double speed) {
        motorTwo.set(speed);
    }

    /**
     * Commands the third canon motor to a specific speed.
     * @param speed Motor speed from -1.0 to 1.0 (0.0 stops the motor)
     */
    public void setCanonThree(double speed) {
        motorThree.set(speed);
    }

    /**
     * Safety fallback: Immediatley stops all 3 motors simultaneously.
     */
    public void stopAll() {
        motorOne.stopMotor();
        motorTwo.stopMotor();
        motorThree.stopMotor();
    }
}
