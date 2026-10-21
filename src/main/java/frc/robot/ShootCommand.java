package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Cannon;

import static frc.robot.constants.CannonConstants.*;

// Shoot command is a timed command, starts when a button is pressed
// Sends a pulse to open solenoid   (PULSE_TIME)
// Holds open                       (HOLD_TIME)
// Sends a pulse to close solenoid  (PULSE_TIME)
// Ends the commands
public class ShootCommand extends Command {
    private final Cannon m_activeCannon;

    private double stateStartTime;

    private enum State {
        Open,
        Hold,
        Close,
        End,
    }
    private State state = State.Open;


    public ShootCommand(Cannon activeCannon) {
        System.out.println("Shoot command");
        m_activeCannon = activeCannon;
        addRequirements(activeCannon);
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
        m_activeCannon.openCannon();
    }

    @Override 
    public void execute() {
        // Check if the current state is done
        double now = Timer.getFPGATimestamp();
        if (now - stateStartTime > stateDuration()) {
            m_activeCannon.neutralCannon();
            stateStartTime = now;

            // Transistion to the next state
            switch (state) {
                case Open:
                    state = State.Hold;
                    break;
                case Hold:
                    state = State.Close;
                    m_activeCannon.closeCannon();
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
        if (interrupted) {
            m_activeCannon.closeCannon();
        }
        m_activeCannon.neutralCannon();
        System.out.println("end");
    }

    @Override
    public boolean isFinished() {
        // End the command when the state is end
        return state == State.End;
    }
}
