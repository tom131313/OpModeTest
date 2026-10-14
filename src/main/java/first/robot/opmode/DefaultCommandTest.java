package first.robot.opmode;

import org.wpilib.command3.Command;
import org.wpilib.command3.Mechanism;
import org.wpilib.command3.Scheduler;
import org.wpilib.opmode.OpMode;
import org.wpilib.opmode.Utility;

import first.robot.Robot;

@Utility(name = "Default Command Test", group = "Group 1")
public class DefaultCommandTest implements OpMode {

    @SuppressWarnings("unused")
    private final Robot robot;
    private static Scheduler scheduler = Scheduler.getDefault();

    private class A implements Mechanism {
      private final Command defaultCmd;
      private final Command testCmd;

      A()
      {
        defaultCmd = run(coroutine -> {
          coroutine.park();
        }).named("A [default cmd]");

        testCmd = run(coroutine -> {
          for(int i = 1; i <= 15; i++)
          {
              coroutine.yield();
          }
        }).named("A test cmd");

        setDefaultCommand(defaultCmd);
      }
    };

    private static class B implements Mechanism {
      private final Command defaultCmd;
      private final Command testCmd;

      B()
      {
        defaultCmd = run(coroutine -> {
          coroutine.park();
        }).named("B [default cmd]");

        testCmd = run(coroutine -> {
          for(int i = 0; i <= 15; i++)
          {
              coroutine.yield();
          }
        }).named("B test cmd");

        setDefaultCommand(defaultCmd);
      }
    };


  /** The Robot instance is passed into the opmode via the constructor. */
  public DefaultCommandTest(Robot robot) {
    this.robot = robot;
  }

  @Override
  public void disabledPeriodic() {
    /* Called on every robot period while the robot is disabled. */
  }

  @Override
  public void start() {
    /* Called once when the robot is enabled. */
    A a = new A();
    B b = new B();
    scheduler.schedule(a.testCmd.andThen(b.testCmd).withAutomaticName());
  }

  @Override
  public void periodic() {
    /* Called on every robot period while the robot is enabled. */
    System.out.println(scheduler.getRunningCommands());
  }

  @Override
  public void end() {
    /* Called when the robot is disabled (after previously being enabled). */
 }

  @Override
  public void close() {
    /* Called when the opmode is de-selected / no additional methods will be called. */
  }
}
/*
OUTPUT

The lines that start with the run time are from the command logger which is essentially similar to
the command logger we have been using for a few years (updated for command-based V3)

********** Creating OpMode Teleop Default Command Test **********
********** Starting OpMode Teleop Default Command Test **********
19564816900 A test cmd -> B test cmd[0] scheduled requiring {A, B}
19565695700 A test cmd -> B test cmd[2] mounted
19566089500 A test cmd[0] scheduled requiring {A}
19566437200 A test cmd[3] mounted
19566666400 A test cmd[3] yielded
19569415100 A test cmd -> B test cmd[2] yielded
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], A test cmd]
19855156200 A test cmd[3] completed after 16 runs
19856545900 B test cmd[0] scheduled requiring {B}
19856835300 B test cmd[4] mounted
19857084100 B test cmd[4] yielded
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
[SequentialGroup[name=A test cmd -> B test cmd], B test cmd]
20175849800 B test cmd[4] completed after 17 runs
20176302800 A test cmd -> B test cmd[2] completed after 3 runs
[]
20195166900 A [default cmd][0] scheduled requiring {A}
20195409000 B [default cmd][0] scheduled requiring {B}
20195666900 B [default cmd][6] mounted
20195871900 B [default cmd][6] yielded
20195990200 A [default cmd][5] mounted
20196173600 A [default cmd][5] yielded
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
[A [default cmd], B [default cmd]]
*/