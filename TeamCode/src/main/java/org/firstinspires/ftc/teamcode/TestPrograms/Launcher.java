package org.firstinspires.ftc.teamcode.TestPrograms;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Configuration.SimpleRobot;

/**
 * Created by ethan.hampton on 10/5/2016.
 * <p>
 * Created to test launching of balls
 */
@TeleOp(name = "Launcher", group = "Test")
public class Launcher extends OpMode {

    private SimpleRobot simpleRobot = new SimpleRobot();

    private double power;

    @Override
    public void init() {

        simpleRobot.simpleInit(hardwareMap);
    }

    @Override
    public void loop() {
        power = gamepad1.left_stick_y;

        simpleRobot.leftMotor.setPower(power);
        simpleRobot.rightMotor.setPower(power);
    }
}
