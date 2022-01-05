package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.TimedRobot;



public class Robot extends TimedRobot {
  private final Joystick gamepad = new Joystick(0);

  private Drivetrain dT;

  @Override
  public void robotInit() {

    //  fl  fr  bl  br
    dT = new Drivetrain(0, 1, 2, 3);

  }


  @Override
  public void teleopPeriodic() 
  { 

    double lsx = (double) gamepad.getRawAxis(0);
    double lsy = (double) gamepad.getRawAxis(1);
    double rsx = (double) gamepad.getRawAxis(4);
    double r_trigger = (double) gamepad.getRawAxis(2);

    if(r_trigger > 0.2){
      dT.slowDownFactor = 2;
    }else{
      dT.slowDownFactor = 1;
    }

    dT.TankDrive(lsy, lsx);

   
    
  }
}
