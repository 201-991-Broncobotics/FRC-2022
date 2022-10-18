package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;


import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;

import frc.Subsystems.Hardware.*;
import frc.Subsystems.ObjectiveController.*;
import frc.Subsystems.DriverController.*;





public class Robot extends TimedRobot {

  private final Joystick gamepad1 = new Joystick(0);
  private final Joystick gamepad2 = new Joystick(1);
 // private Limelight limelight;
  private Drivetrain dT;
  private AuxMotors aM;
  private ObjectiveController oC;
  private DriverController dC;

 // private double startTime;
 // private double elapsedTime;




  @Override
  public void robotInit() {

    //  fl  fr  bl  br
    dT = new Drivetrain(0, 2, 7, 1);

    // In   Out   Conveyor   Angle  Climb1  Climb2 
    aM = new AuxMotors(5, 6, 4, 3, 8, 9);


    oC = new ObjectiveController(aM, dT);
    dC = new DriverController(aM, dT);
   

    UsbCamera camera = CameraServer.startAutomaticCapture();
   
    camera.setResolution(256, 256);
    camera.setFPS(15);


  }





  

  @Override
  public void teleopPeriodic() 
  { 
   
    double lsy = (double) gamepad1.getRawAxis(1);
    double rsx = (double) gamepad1.getRawAxis(4);
    double r_trigger = (double) gamepad1.getRawAxis(2);

    //limelight.showTelemetry();

    dT.slowDownFactor = (r_trigger > 0.2) ? 2 : 1;
    dT.tankDrive(lsy, rsx);

    oC.buttons(gamepad2);
    oC.stick(gamepad2);

    dC.climb(gamepad1);
    dC.checkToggle(gamepad1);

  }








  @Override
  public void autonomousInit(){
   //startTime = Timer.getFPGATimestamp();
   // double startPosition = dT.position();
    // elapsedTime = Timer.getFPGATimestamp() - startTime;

    dT.travelTo(0);
    
    autonomousShoot();

    dT.travelTo(200000);




    //Timer.delay(10);




  

    dT.travelTo(210000);

    aM.setIntake(0);

    Timer.delay(1);

    aM.setStorage(0);



   // Timer.delay(10);


    dT.travelTo(0);

    autonomousShoot();

    
  }





  @Override
  public void autonomousPeriodic(){
  
    
  }

public void autonomousShoot(){
  aM.manualShooter(-0.75);

  Timer.delay(1.75);

  aM.setStorage(0.6);

  Timer.delay(0.75);

  aM.setStorage(0);

  aM.manualShooter(0);
}

public void dropIntake(){
  aM.setIntakeAngle(-0.7);

  Timer.delay(0.75);

  aM.setIntakeAngle(0);

  aM.setIntake(0.5);

  Timer.delay(1.05);
}

}
