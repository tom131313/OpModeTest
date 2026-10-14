// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.opmode;

import static org.wpilib.units.Units.Seconds;

import org.wpilib.command3.Command;
import org.wpilib.command3.Scheduler;
import org.wpilib.command3.Trigger;
import org.wpilib.driverstation.DriverStationDisplay;
import org.wpilib.framework.OpModeRobot;
import org.wpilib.opmode.OpMode;
import org.wpilib.opmode.Teleop;

import first.robot.Robot;

@Teleop
public class MyTeleop implements OpMode {
  // @SuppressWarnings("unused")
  private final Robot robot;

  /** The Robot instance is passed into the opmode via the constructor.
   * <p>The constructor is run every time this OpMode is entered as disabled such:
   * <p>select this OpMode which starts as disabled and constructor runs
   * <p>select Enable and periodic runs
   * <p>select disable and end runs, close runs, constructor runs to be ready to enable auto again.
   * <p>If enabled and selects a different OpMode then the new OpMode constructor runs
   */
  public MyTeleop(Robot robot) {
    System.out.println("MyTeleop constructor");
    this.robot = robot;
    robot.testMechanism.setDefaultCommand(robot.testMechanism.run(coroutine-> DriverStationDisplay.addData("Teleop Mode Test Mechanism", "Default Command" )).named("Test Mechanism"));
    new Trigger(robot.di0).whileTrue(Command.noRequirements(coroutine -> {while(true) {DriverStationDisplay.addData("MyTeleop", "di0 triggered command");coroutine.yield();}}).named("MyTeleop"));
    // now the Robot and OpMode triggers are active
  }

 /**
   * This function is called periodically while the opmode is selected and the robot is disabled.
   * Code that should only run once when the opmode is selected should go in the opmode constructor.
   */
  int countDisabledPeriodic = 0;
  public void disabledPeriodic() {
    if (countDisabledPeriodic++ % 100 == 0) {
      DriverStationDisplay.addKeyedLine("count teleop disabled periodic","__________MyTeleop disabledPeriodic " + countDisabledPeriodic);
    }
  }

  /** Called once when this opmode transitions to enabled. */
  public void start() {
    System.out.println("MyTeleop start");

    var testCommand = Command.noRequirements(
        coroutine -> {
            System.out.println("MyTeleopCommand");
            robot.testMechanism.setDefaultCommand(robot.testMechanism.run(coroutine2-> DriverStationDisplay.addData("Command Test Mechanism", "Default Command" )).named("Test Mechanism"));

            var myTeleopTriggeredCommand = 
                Command.noRequirements(
                    coroutine2 ->
                    {
                        while(true)
                        {
                            DriverStationDisplay.addData("Command Test Mechanism", "MyTeleopTriggeredCommand");
                            coroutine2.yield();
                        }
                    }
                ).named("MyTeleopTriggeredCommand");

            // robot.testTrigger.unbind(); // comment/uncomment to test wiping out the global trigger bindings. It works and robot bindings have to be rerun to reactivate.
            // Trigger bindings in OpMode and Command scope are automatically unbound if out of scope and may be garbage collected.
            // Those Triggers for OpMode and Command scope must be created from scratch each time the scope is entered as they may have been garbage collected
            // unless referenced elsewhere.

            new Trigger(robot.di0).whileTrue(myTeleopTriggeredCommand);
            // now the Robot, OpMode, and Command triggers are active

            coroutine.wait(Seconds.of(15.));
        }
    ).named("MyTeleopCommand");

    Scheduler.getDefault().schedule(testCommand);
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
      DriverStationDisplay.addKeyedLine("count teleop periodic","__________MyTeleop periodic " + countPeriodic);
    }
  }

  /**
   * This function is called asynchronously when the robot disables or switches opmodes while this
   * opmode is enabled. Implementations should stop blocking work promptly.
   */
  public void end() {
    System.out.println("MyTeleop end");
  }

  /**
   * This function is called when the opmode is no longer selected on the DS or after an enabled run
   * ends. The object will not be reused after this is called.
   */
  @Override
  public void close() {
    System.out.println("MyTeleop close");
  }
}
