// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.opmode;

import org.wpilib.command3.Command;
import org.wpilib.command3.Trigger;
import org.wpilib.framework.OpModeRobot;
import org.wpilib.opmode.OpMode;
import org.wpilib.opmode.Utility;

import first.robot.Robot;

@Utility
public class MyUtility implements OpMode {
  @SuppressWarnings("unused")
  private final Robot robot;

  /** The Robot instance is passed into the opmode via the constructor.
   * <p>The constructor is run every time this OpMode is entered as disabled such:
   * <p>select this OpMode which starts as disabled and constructor runs
   * <p>select Enable and periodic runs
   * <p>select disable and end runs, close runs, constructor runs to be ready to enable auto again.
   * <p>If enabled and selects a different OpMode then the new OpMode constructor runs
   */
  public MyUtility(Robot robot) {
    this.robot = robot;
    System.out.println("MyUtility constructor");
    new Trigger(robot.di0).whileTrue(Command.noRequirements(coroutine -> {while(true) {System.out.println("MyUtility");coroutine.yield();}}).named("MyUtility"));
  }

 /**
   * This function is called periodically while the opmode is selected and the robot is disabled.
   * Code that should only run once when the opmode is selected should go in the opmode constructor.
   */
  int countDisabledPeriodic = 0;
  public void disabledPeriodic() {
    if (countDisabledPeriodic++ % 100 == 0) System.out.println("__________MyUtility disabledPeriodic " + countDisabledPeriodic);
  }

  /** Called once when this opmode transitions to enabled. */
  public void start() {
    System.out.println("MyUtility start");
  }

  /**
   * This function is called periodically while the opmode is enabled at the rate returned by {@link
   * OpModeRobot#getPeriod()}.
   * 
   * This method runs periodically, using the same period as the Robot instance.
   *
   * Additional periodic methods may be configured with addPeriodic(),
   * which can have periods that differ from the main Robot instance.
   */
  int countPeriodic = 0;
  @Override
  public void periodic() {
    if (countPeriodic++ % 100 == 0) System.out.println("__________MyUtility periodic " + countPeriodic);
  }

  /**
   * This function is called asynchronously when the robot disables or switches opmodes while this
   * opmode is enabled. Implementations should stop blocking work promptly.
   */
  public void end() {
    System.out.println("MyUtility end");
  }

  /**
   * This function is called when the opmode is no longer selected on the DS or after an enabled run
   * ends. The object will not be reused after this is called.
   */
  @Override
  public void close() {
    System.out.println("MyUtility close");
  }
}
