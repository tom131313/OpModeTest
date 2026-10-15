package first.robot.opmode;

import org.wpilib.driverstation.RobotState;
import org.wpilib.opmode.OpMode;

import first.robot.Robot;

public class MyProgrammedOpMode implements OpMode {
    public MyProgrammedOpMode(Robot robot)
    {
        System.out.println("Example of programmed OpMode has been constructed for mode "
            + RobotState.getOpMode() + ", " + RobotState.getRobotMode());
    }
}
