package org.firstinspires.ftc.teamcode.API;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Utils.State;

/**
 * Created by ethan.hampton on 10/11/2016.
 * <p>
 * setup for autonomous library
 */

public class RobotConfig {
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private int ENCODER_TICKS_PER_ROTATION;

    //elapsed time
    public ElapsedTime time = new ElapsedTime();
    //what time to wait till
    public double waitTime = 0;
    //what time that current step started
    public double startTime = 0;

    //the step we are on, tells us speed, left motor distance, right motor distance and any flags
    public int currentStep = 0;

    /*
    to reference step one you would do something like steps[0][x]
    to go backwards, change motor distances not speed, this means you can turn on a dime
    the values per step are as follows:
    1. left motor distance in rotations
    2. right motor distance in rotations
    3. Speed from 1 - 100 (if you want to use default then use -1 and that will auto fill to the current default value, default is 75)
    4. any flags that may be needed, -1 signifies that the program should stop  (optional)
    */
    public double[][] steps;
    //our current state
    public State currentState = State.INIT;

    //The target positions for both motors
    private double leftTarget = 0;
    private double rightTarget = 0;
    public double rightSpeed = 0;
    public double leftSpeed = 0;

    /**
     * @param rightMotor assumes motor has been configured correctly
     * @param leftMotor  assumes motor has been configured correctly
     * @param encoderTicksPerRotation how many encoder ticks per rotation
     */
    public void Init(DcMotor rightMotor, DcMotor leftMotor, int encoderTicksPerRotation) {
        // Set all motors to run with encoders to use encoders to track position
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor = rightMotor;
        this.leftMotor = leftMotor;
        ENCODER_TICKS_PER_ROTATION = encoderTicksPerRotation;
    }

    /**
     * default simple drive
     *
     * @param leftRotations  how many rotations left motor should go, can be negative
     * @param rightRotations how many rotations left motor should go, can be negative
     * @param speed          -1 if using default speed, assumes speed is always positive
     */
    public void setDrive(double leftRotations, double rightRotations, double speed) {
        //if speed isn't specified then use default speed
        if (speed == -1) {
            speed = 75;//75 is default speed
        }

        /*
        if the speed is from a scale from 0 to 100 instead of 0 to 1
        change it so it is in a scale the robot will work with
         */
        if (speed > 1) {
            speed = speed / 100;
        }

        //create speed for each motors in order to scale properly
        leftSpeed = speed;
        rightSpeed = speed;

        //reverse speed as necessary, if we need negative rotations
        if (leftRotations < 0) {
            leftSpeed = -leftSpeed;
        }
        if (rightRotations < 0) {
            rightSpeed = -rightSpeed;
        }

        /*
        scale speed so that turns are relatively smooth, doesn't change anything if they are the same
        note that we are scaling by 2 so we have a differential effort
        */
        if (Math.abs(leftRotations) > Math.abs(rightRotations)) {
            double scale = Math.abs(rightRotations) / Math.abs(leftRotations);
            rightSpeed = rightSpeed * scale;
        } else if (Math.abs(rightRotations) > Math.abs(leftRotations)) {
            double scale = Math.abs(leftRotations) / Math.abs(rightRotations);
            leftSpeed = leftSpeed * scale;
        }


        //sets targets
        leftTarget = leftRotations * ENCODER_TICKS_PER_ROTATION;
        rightTarget = rightRotations * ENCODER_TICKS_PER_ROTATION;

        resetAllEncoders();
        leftMotor.setTargetPosition((int) leftTarget);
        rightMotor.setTargetPosition((int) rightTarget);
        startAllEncoders();

        //if either motor doesn't need to move then don't move it
        if (!(leftTarget == 0)) {
            leftMotor.setPower(leftSpeed);
        } else {
            leftMotor.setPower(0);
        }
        if (!(rightTarget == 0)) {
            rightMotor.setPower(rightSpeed);
        } else {
            rightMotor.setPower(0);
        }
    }


    public void resetAllEncoders() {
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void startAllEncoders() {
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }


    public double getRightTarget() {
        return rightTarget;
    }

    public void setRightTarget(double rightTarget) {
        this.rightTarget = rightTarget;
    }

    public double getLeftTarget() {
        return leftTarget;
    }

    public void setLeftTarget(double leftTarget) {
        this.leftTarget = leftTarget;
    }
}