package frc.Subsystems.ObjectiveController;

import edu.wpi.first.wpilibj.Joystick;
import frc.Subsystems.Hardware.AuxMotors;
import frc.Subsystems.Hardware.Drivetrain;


public class ObjectiveController extends VarNames{
    
    public ObjectiveController(AuxMotors _a, Drivetrain _d){
        dT = _d;
        aM = _a;
    }

    public void buttons(Joystick gamepad){

        if(gamepad.getRawButton(1)){
            b_motor = false;
            a_motor = !a_past ? !a_motor : a_motor;
            if(!a_past) aM.setIntake(a_motor ? -0.4 : 0);
        }

        if(gamepad.getRawButton(2)){
            a_motor = false;
            b_motor = !b_past ? !b_motor : b_motor;
            if(!b_past) aM.setIntake(b_motor ? 0.8 : 0);
        }

        if(gamepad.getRawButton(3)){
            y_motor = false;
            x_motor = !x_past ? !x_motor : x_motor;
            if(!x_past) aM.setStorage(x_motor ? 0.4 : 0);
        }

        if(gamepad.getRawButton(4)){
            x_motor = false;
            y_motor = !y_past ? !y_motor : y_motor;
            if(!y_past) aM.setStorage(y_motor ? -0.6 : 0);
        }

        if(gamepad.getRawButton(5)){
           aM.err_factor += !lb_past ? 0.05 : 0;
        }

        if(gamepad.getRawButton(6)){
            aM.err_factor -= !rb_past ? 0.05 : 0;
        }

        if(gamepad.getRawAxis(2) > 0.1){
            aM.manualShooter(0.5);
        }else if(gamepad.getRawAxis(3) > 0.1){
            aM.manualShooter(1);
        }else{
            aM.setShooter(0);
        }

        a_past = gamepad.getRawButton(1);
        b_past = gamepad.getRawButton(2);
        x_past = gamepad.getRawButton(3);
        y_past = gamepad.getRawButton(4);
        lb_past = gamepad.getRawButton(5);
        rb_past = gamepad.getRawButton(6);
    }

    public void stick(Joystick gamepad) {
        aM.setIntakeAngle(gamepad.getRawAxis(1) < -0.5 ? 0.4 : gamepad.getRawAxis(1) > 0.5 ? -0.4 : 0);
    }

}
