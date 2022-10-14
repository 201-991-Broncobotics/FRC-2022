package frc.Subsystems.DriverController;

import frc.Subsystems.Hardware.*;

import edu.wpi.first.wpilibj.Joystick;

public class DriverController {
    private Drivetrain dT;
    private AuxMotors aM;
    private boolean a_past = false;
    private boolean dt_state = true;


    public DriverController(AuxMotors _a, Drivetrain _d){
            dT = _d;
            aM = _a;
    } 

    public void climb(Joystick gamepad, boolean var){
        if(!var){
            aM.climb1(gamepad.getRawButton(6) ? 0.7 : (gamepad.getRawAxis(3) > 0.1) ? -0.7 : 0);    
        }else if(gamepad.getRawButton(6)){
            aM.climb1(0.4);
        }else{
            aM.climb1(0);
        }
 
        
    }

    public void checkToggle(Joystick gamepad){
        if(gamepad.getRawButton(1) && !a_past){
            dt_state = !dt_state;
            dT.setMode(dt_state);
        }
        a_past= gamepad.getRawButton(1);
    }
}
