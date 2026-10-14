package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CannonSystem;
import frc.robot.subsystems.CannonSystem.cannons;

// Shoot command is a timed command, starts when a button is pressed
// Sends a pulse to open solenoid   (PULSE_TIME)
// Holds open                       (HOLD_TIME)
// Sends a pulse to close solenoid  (PULSE_TIME)
// Ends the commands
public class ShootCommand extends Command {
    private final double PULSE_TIME = 0.5;
    private final double HOLD_TIME = 1.0;

    private final CannonSystem m_canonSystem;
    private final cannons m_activeCannon;

    private double stateStartTime;

    private enum State {
        Open,
        Hold,
        Close,
        End,
    }
    private State state = State.Open;


    public ShootCommand(CannonSystem cannonSystem, cannons activeCannon) {
        System.out.println("Shoot command");
        m_canonSystem = cannonSystem;
        m_activeCannon = activeCannon;
        addRequirements(cannonSystem);
    }

    private double stateDuration() {
        switch (state) {
            case Open: return PULSE_TIME;
            case Hold: return HOLD_TIME; 
            case Close: return PULSE_TIME;      
            default: return PULSE_TIME;
        }
    }

    @Override
    public void initialize() {
        // Send initail pulse to open
        state = State.Open;
        stateStartTime = Timer.getFPGATimestamp();
        m_canonSystem.openCannon(m_activeCannon);
    }

    @Override 
    public void execute() {
        // Check if the current state is done
        double now = Timer.getFPGATimestamp();
        if (now - stateStartTime > stateDuration()) {
            m_canonSystem.stopAll();
            stateStartTime = now;

            // Transistion to the next state
            switch (state) {
                case Open:
                    state = State.Hold;
                    break;
                case Hold:
                    state = State.Close;
                    m_canonSystem.closeCannon(m_activeCannon);
                    break;
                case Close:
                    state = State.End;
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("end");
    }

    @Override
    public boolean isFinished() {
        // End the command when the state is end
        return state == State.End;
    }
}
