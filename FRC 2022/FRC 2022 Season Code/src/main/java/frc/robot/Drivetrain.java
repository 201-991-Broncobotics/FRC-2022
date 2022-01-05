package frc.robot;

import javax.xml.xpath.XPathEvaluationResult;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;


public class Drivetrain
{
    // defines the motors and other sensors
    VictorSPX frontLeft;
    VictorSPX frontRight;
    VictorSPX backLeft;
    VictorSPX backRight;
    BuiltInAccelerometer gyro = new BuiltInAccelerometer();
    boolean strafing = true;
    double gyro_angle_ref = 0;
    double slowDownFactor = 1;

    public Drivetrain(int __frontLeft__, int __frontRight__, int __backLeft__, int __backRight__)
    {
        // defines names for the motors
        frontLeft = new VictorSPX(__frontLeft__);
        frontRight = new VictorSPX(__frontRight__);
        backLeft =  new VictorSPX(__backLeft__);
        backRight =  new VictorSPX(__backRight__);

        // reverses motors so that you ovefoward with positive values
        frontLeft.setInverted(true);
        backLeft.setInverted(true);
    

    }

    public void TankDrive(double yVal, double xVal){
        xVal = deadZone(xVal);
        yVal = deadZone(yVal);

        double sum = Math.abs(xVal) + Math.abs(yVal);

        if(sum>1){
            xVal /= sum;
            yVal /= sum;
        }

        setPower(xVal+yVal, -xVal+yVal, true);
    }

    private double deadZone(double val){
        if(Math.abs(val) < 0.1){
            return 0;
        }else{
            return val;
        }
    }

    private void setPower(double left, double right, boolean slowDown){
        if(slowDown){
            left /= slowDownFactor;
            right /= slowDownFactor;
            frontLeft.set(ControlMode.PercentOutput, left);
            backLeft.set(ControlMode.PercentOutput, left);
            frontRight.set(ControlMode.PercentOutput, right);
            backRight.set(ControlMode.PercentOutput, right);
        }else{
            frontLeft.set(ControlMode.PercentOutput, left);
            backLeft.set(ControlMode.PercentOutput, left);
            frontRight.set(ControlMode.PercentOutput, right);
            backRight.set(ControlMode.PercentOutput, right);
        }
     
    }
    

    
}

