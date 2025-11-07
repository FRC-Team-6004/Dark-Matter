package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Pneumatics extends SubsystemBase {
    // Create solenoids on CTRE PH ports 1, 2, and 3
    private final Solenoid solenoid1 = new Solenoid(32, PneumaticsModuleType.CTREPCM, 4);
    private final Solenoid solenoid2 = new Solenoid(32, PneumaticsModuleType.CTREPCM, 2);
    private final Solenoid solenoid3 = new Solenoid(32, PneumaticsModuleType.CTREPCM, 3);

    public Pneumatics() {
        // Start with all off
        solenoid1.set(false);
        solenoid2.set(false);
        solenoid3.set(false);
        solenoid1.startPulse();
    }

    // Individual control methods
    public void setSolenoid1(boolean on) {
        solenoid1.set(on);
        System.out.println("sol1: " + on);
    }

    public void setSolenoid2(boolean on) {
        solenoid2.set(on);
        System.out.println("sol2: " + on);

    }

    public void setSolenoid3(boolean on) {
        solenoid3.set(on);
        System.out.println("sol3: " + on);

    }

    // Optional: turn all off at once
    public void disableAll() {
        solenoid1.set(false);
        solenoid2.set(false);
        solenoid3.set(false);
    }
}