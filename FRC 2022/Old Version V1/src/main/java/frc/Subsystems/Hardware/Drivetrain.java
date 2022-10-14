package frc.Subsystems.Hardware;

import javax.xml.xpath.XPathEvaluationResult;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import frc.Subsystems.Variables;



public class Drivetrain implements Variables
{
    // defines the motors and other sensors
 
    TalonFX frontLeft;
    TalonFX frontRight;
    TalonFX backLeft;
    TalonFX backRight;
    BuiltInAccelerometer gyro = new BuiltInAccelerometer();
  
    public double slowDownFactor = 1;

   
    public Drivetrain(int __frontLeft__, int __frontRight__, int __backLeft__, int __backRight__)
    {
        // defines names for the motors
        frontLeft = new TalonFX(__frontLeft__);
        frontRight = new TalonFX(__frontRight__);
        backLeft =  new TalonFX(__backLeft__);
        backRight =  new TalonFX(__backRight__);

        backLeft.setNeutralMode(NeutralMode.Brake);
        frontLeft.setNeutralMode(NeutralMode.Brake);
        backRight.setNeutralMode(NeutralMode.Brake);
        frontRight.setNeutralMode(NeutralMode.Brake);

        // reverses motors so that you go foward with positive values
        frontLeft.setInverted(true);
        backRight.setInverted(true);

    }

    public void tankDrive(double yVal, double xVal){
        xVal = deadZone(xVal);
        yVal = deadZone(yVal);
        double x =Math.abs(xVal)*xVal;
        double y = Math.abs(yVal)*yVal;

        setPower(y-x, y+x, true);
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
            backLeft.set(ControlMode.PercentOutput, -left);
            frontRight.set(ControlMode.PercentOutput, right);
            backRight.set(ControlMode.PercentOutput, -right);
        }else{
            frontLeft.set(ControlMode.PercentOutput, left);
            backLeft.set(ControlMode.PercentOutput, -left);
            frontRight.set(ControlMode.PercentOutput, right);
            backRight.set(ControlMode.PercentOutput, -right);
        }

     
    }
 
    
    public void classicDrive(double ry, double ly){
        ry = deadZone(ry);
        ly = deadZone(ly);

        setPower(ly, ry, true);
    }

    public void setMode(boolean x){
        if(x){
            backLeft.setNeutralMode(NeutralMode.Brake);
            frontLeft.setNeutralMode(NeutralMode.Brake);
            backRight.setNeutralMode(NeutralMode.Brake);
            frontRight.setNeutralMode(NeutralMode.Brake);
        }else{
            backLeft.setNeutralMode(NeutralMode.Coast);
            frontLeft.setNeutralMode(NeutralMode.Coast);
            backRight.setNeutralMode(NeutralMode.Coast);
            frontRight.setNeutralMode(NeutralMode.Coast);
        }

    }

    public double position(){
        return frontLeft.getSelectedSensorPosition();
    }

    public void travelTo(double target_pos){
        //true : forwards
        boolean direction = (target_pos - position()) > 0;
        if(direction){
            while(target_pos > position()){
                setPower(0.3, 0.3, false);
            }
        }else if(target_pos != position()){
            while(target_pos < position()){
                setPower(-0.3, -0.3, false);
            }
        }
        setPower(0, 0, false);
    }

    
}

