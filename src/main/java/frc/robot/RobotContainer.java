// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.CannonSystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CannonSystem.cannons;

public class RobotContainer {
    private final double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private final double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    // Modules
    private final Telemetry                 logger          = new Telemetry(MaxSpeed);
    private final CommandXboxController     joystick        = new CommandXboxController(0);
    private final CommandSwerveDrivetrain   drivetrain      = TunerConstants.createDrivetrain();
    private final CannonSystem              cannonSystem    = new CannonSystem();
    //private final AddressableLED            leds            = new AddressableLED(0);
    //private final AddressableLEDBuffer      ledBuffer       = new AddressableLEDBuffer(350);

    private int ledCount = 0;

    public RobotContainer() {
        configureBindings();

        /*
        // Init LEDs
        leds.setLength(ledBuffer.getLength());
        leds.setData(ledBuffer);
        leds.start();

        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, 255, 255, 255);
        }
        leds.setData(ledBuffer);*/

    }

    private void configureBindings() {
        // Note that X is defined as forward according to WPILib convention,
        // and Y is defined as to the left according to WPILib convention.
        drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed * .4) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed * .4) // Drive left with negative X (left)
                    .withRotationalRate(-joystick.getRightX() * MaxAngularRate * .6) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        joystick.y().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));


        // Shoot commands using the POV and A button
        joystick.povRight().and(joystick.a()).onTrue(new ShootCommand(cannonSystem, cannons.rightCannon));
        joystick.povUp().and(joystick.a()).onTrue(new ShootCommand(cannonSystem, cannons.middleCannon));
        joystick.povLeft().and(joystick.a()).onTrue(new ShootCommand(cannonSystem, cannons.leftCannon));

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public void periodic() {
        //BlueAnimation();
    }

    /*
    public void BlueAnimation() {
        double stren = ((Math.sin((1.0 * ledCount) / 30)) * 25) + 40;
        for (int i = 0; i < ledBuffer.getLength(); i++) {
            ledBuffer.setRGB(i, 0, 0, (int) (stren));
        }
        ledCount += 1;
        //System.out.println("ledc = " + ledc + "   stren = " +  stren);
        leds.setData(ledBuffer);
    }*/
    
}
