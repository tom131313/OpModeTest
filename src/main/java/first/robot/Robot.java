// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot;

import java.util.function.BooleanSupplier;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.framework.OpModeRobot;
import org.wpilib.hardware.discrete.DigitalInput;
import org.wpilib.hardware.hal.RobotMode;

import first.robot.opmode.MyProgrammedOpMode;

/**
 * The methods in this class are called automatically as described in the OpModeRobot documentation.
 * OpMode classes anywhere in the package (or sub-packages) where this class is located are
 * automatically registered to display in the Driver Station. If you change the name of this class
 * or the package after creating this project, you must also update the Main.java file in the
 * project.
 */

/**
 * Test OpModes and scoped Triggers and Mechanism Default Commands
 * 
 * Robot
 */
public class Robot extends OpModeRobot {

  public DigitalInput di = new DigitalInput(0);
  public BooleanSupplier di0 = () -> di.get();
  public Mechanism testMechanism = new Mechanism() {};
  public Trigger testTrigger; // public and named to test unbind() elsewhere

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    System.out.println("Robot constructor");
    addOpMode(RobotMode.TELEOPERATED, "Example Programmed OpMode", "Group 1", () -> new MyProgrammedOpMode(this));
    publishOpModes();
    // output from MyProgrammedOpMode
    // Example of programmed OpMode has been constructed for mode Example Programmed OpMode, TELEOPERATED

    testMechanism.setDefaultCommand(testMechanism.run(coroutine->System.out.println("Global Default Test Mechanism")).named("Test Mechanism"));
    testTrigger = new Trigger(di0).whileTrue(Command.noRequirements(coroutine -> {while(true) {System.out.println("Robot");coroutine.yield();}}).named("Robot"));
  }

  /** This function is called exactly once when the DS first connects. */
  @Override
  public void driverStationConnected() {System.out.println("Robot driverStationConnected");}

  /** Function called periodically every loop, regardless of enabled state or OpMode selection. */
  int countRobotPeriodic = 0;
  @Override
  public void robotPeriodic() {
    Scheduler.getDefault().run();
    if (countRobotPeriodic++ % 100 == 0) {
      System.out.println("__________Robot robotPeriodic " + countRobotPeriodic);
      System.out.println("dio 0 " + di0.getAsBoolean());
  }}

  /** Function called once during robot initialization in simulation. */
  @Override
  public void simulationInit() {System.out.println("Robot simulationInit");}

  /** Function called periodically in simulation. */
  int countSimulationPeriodic = 0;
  @Override
  public void simulationPeriodic() {
    if (countSimulationPeriodic++ % 100 == 0) System.out.println("__________Robot simulationPeriodic " + countSimulationPeriodic);}

  /** Function called once when the robot becomes disabled. */
  @Override
  public void disabledInit() {System.out.println("Robot disabledInit");}

  /** Function called periodically while the robot is disabled. */
  int countDisabledPeriodic = 0;
  @Override
  public void disabledPeriodic() {
    if (countDisabledPeriodic++ % 100 == 0) System.out.println("__________Robot disabledPeriodic " + countDisabledPeriodic);}

  /** Function called once when the robot exits disabled state. */
  @Override
  public void disabledExit() {System.out.println("Robot disabledExit");}

  /**
   * This function is called periodically anytime when no opmode is selected, including when the
   * Driver Station is disconnected.
   */
  int countNonePeriodic = 0;
  @Override
  public void nonePeriodic() {
    if (countNonePeriodic++ % 100 == 0) System.out.println("__________Robot nonePeriodic " + countNonePeriodic);}

  @Override
  public void close() {System.out.println("Robot close");
    // my stuff then
    super.close();
  }
}
//System.out.println(RobotState.isUtility() + " " + RobotState.isTeleop() + " " + RobotState.isAutonomous() + " " + RobotState.isEnabled());

/*
TEST PROCEDURE:

Simulation starts in disconnected
Click on Autonomous
Notice Robot disabledInit has not run
Select MyAuto OpMode
starts in disabled
Select enable
Select disable
Notice Robot disabledInit now runs correctly and continues to do so on any mode changes or enable/disable changes

TEST RESULTS:
====================================================================

*/
