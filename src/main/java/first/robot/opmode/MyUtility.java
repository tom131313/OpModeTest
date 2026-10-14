// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.opmode;

import java.util.function.Consumer;

import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.driverstation.DriverStationDisplay;
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
  
    // OpMode sideload demo
    Scheduler.getDefault().sideload(MyUtilitySideload);

    // Trigger chaining - demo both trigger modifiers and start commands

    new Trigger(robot.di0)
    .whileTrue (Command.noRequirements(coroutine -> {while(true) {System.out.println("di0 true #1");coroutine.yield();}}).named("#1"))
    .negate()
    .whileFalse(Command.noRequirements(coroutine -> {while(true) {System.out.println("di0 true #2");coroutine.yield();}}).named("#2"))
    // output di0 true:
    // di0 true #2
    // di0 true #1
    ;

    new Trigger(robot.di0)
    .negate()
    .whileTrue (Command.noRequirements(coroutine -> {while(true) {System.out.println("di0 true #3");coroutine.yield();}}).named("#1"))
    .whileFalse(Command.noRequirements(coroutine -> {while(true) {System.out.println("di0 true #4");coroutine.yield();}}).named("#2"))
    // output di0 true:
    // di0 true #4
    ;

    new Trigger(robot.di0)
    .whileTrue (Command.noRequirements(coroutine -> {while(true) {System.out.println("di0 true #5");coroutine.yield();}}).named("#1"))
    .whileFalse(Command.noRequirements(coroutine -> {while(true) {System.out.println("di0 true #6");coroutine.yield();}}).named("#2"))
    .negate()
    // output di0 true:
    // di0 true #5
    ;
    // di0 true #5
    // di0 true #4
    // di0 true #2
    // di0 true #1

    // OpMode trigger scope demo
    new Trigger(robot.di0).whileTrue(Command.noRequirements(
      coroutine ->
        {
          while(true)
          {
            System.out.println("MyUtility");
            coroutine.yield();
          }
        }).named("MyUtility"));

    // OpMode trigger scope with command scope sideload demo
    new Trigger(robot.di0).whileTrue(Command.noRequirements(
      coroutine ->
        {
          Scheduler.getDefault().sideload(this::MyCommandSideload);

          while(true)
          {
            DriverStationDisplay.addKeyedLine("Command Message 1", "I'm in MyCommand");
            System.out.println("MyCommand");
            coroutine.yield();
          }
        }).named("MyCommand"));
  }

 /**
   * This function is called periodically while the opmode is selected and the robot is disabled.
   * Code that should only run once when the opmode is selected should go in the opmode constructor.
   */
  int countDisabledPeriodic = 0;
  public void disabledPeriodic() {
    if (countDisabledPeriodic++ % 100 == 0) {
      DriverStationDisplay.addKeyedLine("count utility disabled periodic","__________MyUtility disabledPeriodic " + countDisabledPeriodic);
    }
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
    if (countPeriodic++ % 100 == 0) {
      DriverStationDisplay.addKeyedLine("count utility periodic","__________MyUtility periodic " + countPeriodic);
    }
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

  /**
   * Two ways to express a sideload - as a lambda or not
   * <p>
   * As a lambda the lambda could be coded in-line, too - sideload(lambda expression).
   * Using the method reference technique likely is the cleanest, easiest to read code as shown
   * with the MyCommandSideload example.
   * <p>
   * This is the lambda expression.
   * <pre><code>
   * // OpMode sideload demo
   * Scheduler.getDefault().sideload(MyUtilitySideload);
   * </code></pre>
   */
  Consumer<Coroutine> MyUtilitySideload = coroutine ->
  {
    System.out.println("starting MyUtility Sideload");
    while(true)
    {
      DriverStationDisplay.addData("MyUtility Scheduler", "sideload");
      coroutine.yield();
    }
  };

  /**
   * Two ways to express a sideload - as a lambda or not
   * <p>
   * As a lambda the lambda could be coded in-line, too - sideload(lambda expression).
   * Using the method reference technique likely is the cleanest, easiest to read code as shown
   * with the MyCommandSideload example.
   * <p>
   * This is the method to be referenced which is usually used directly or optionally can be used
   * as a variable value.
   * <pre><code>
   * // OpMode sideload demo
   * Scheduler.getDefault().sideload(this::MyCommandSideload);
   * </code></pre>
   * @param coroutine
   */
  void MyCommandSideload(Coroutine coroutine)
  {
    System.out.println("starting MyCommand Sideload");
    while(true)
    {
      DriverStationDisplay.addData("MyCommand Scheduler", "sideload");
      coroutine.yield();
    }
  }
}
