package frc.Subsystems;

import javax.xml.xpath.XPathEvaluationResult;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import frc.robot.Variables;



public class Drivetrain implements Variables
{
    // defines the motors and other sensors
 
    VictorSPX frontLeft;
    VictorSPX frontRight;
    VictorSPX backLeft;
    VictorSPX backRight;
    BuiltInAccelerometer gyro = new BuiltInAccelerometer();
  
    public double slowDownFactor = 1;
   
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
        if(3*yVal > xVal){
            //turn
            setPower(xVal, -xVal, true);
        }else if(xVal > 0){
            //right curve
            setPower(yVal, yVal - (driving_scale_const * xVal * yVal), true);
        }else if(xVal <= 0){
            //left curve
            setPower(yVal - (driving_scale_const * -xVal * yVal), yVal, true);
        }
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

