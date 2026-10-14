package first.robot.opmode;

import org.wpilib.driverstation.RobotState;
import org.wpilib.opmode.OpMode;

import first.robot.Robot;

/**
 * Must specify "implements OpMode" interface and have a "public" constructor to be eligible to be added as an OpMode
 * If the class is OpMode annotated ("@...") it is automatically added as an OpMode. This example is not annotated
 * and must be programmatically added as an OpMode.
 * 
 * <p>This class may be instantiated not as an added OpMode. That seems confusing and likely is not a good idea.
 * There doesn't seem to be a reliable way of always detecting this situation so be careful. This example doesn't even
 * partially try.
 * 
 * MyProgrammedOpMode
 */
public class MyProgrammedOpMode implements OpMode {

    Robot robot;

    public MyProgrammedOpMode(Robot robot)
    {
        this.robot = robot;
        System.out.println("Example of programmed OpMode has been constructed for OpMode <" 
            + RobotState.getOpMode() + "> : " + RobotState.getRobotMode());
        // Example of programmed OpMode has been constructed for OpMode <Example Programmed OpMode> : TELEOPERATED
    }
}
