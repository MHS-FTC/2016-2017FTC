package org.firstinspires.ftc.teamcode.Configuration;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Utils.Mode;

/**
 * Created by ethan.hampton on 10/4/2016.
 * <p>
 * For our robot
 */

public class Robot {

    /* Public OpMode members. */
    public DcMotor leftMotor = null;
    public DcMotor rightMotor = null;


    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap, Mode mode) {

        // Define and Initialize Motors
        leftMotor = ahwMap.dcMotor.get("left motor");
        rightMotor = ahwMap.dcMotor.get("right motor");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        leftMotor.setPower(0);
        rightMotor.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

}
