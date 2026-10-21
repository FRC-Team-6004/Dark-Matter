package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.constants.CannonConstants.*;


public class Cannon extends SubsystemBase {
    private final PWMSparkMax m_motor;

    public Cannon(int channel) {
        m_motor = new PWMSparkMax(channel);
        setDefaultCommand(Commands.runOnce(() -> neutralCannon(), this));
    }

    public void openCannon() {
        m_motor.set(OPEN_VOLTAGE_DUTY);
    }

    public void closeCannon() {
        m_motor.set(CLOSE_VOLTAGE_DUTY);
    }

    public void neutralCannon(){
        m_motor.stopMotor();
    }

}
