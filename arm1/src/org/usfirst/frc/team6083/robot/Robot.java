package org.usfirst.frc.team6083.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    
    //motor
    VictorSP talon_arm = new VictorSP(2);
    VictorSP talon_left = new VictorSP(1);
    VictorSP talon_right = new VictorSP(0);
    
    //Joystick
    Joystick joy = new Joystick(0);
    Joystick joy_3d = new Joystick(1);
    JoystickButton left = new JoystickButton(joy,5);
    JoystickButton right = new JoystickButton(joy,6);
    
    //Device
    PowerDistributionPanel pdp = new PowerDistributionPanel(1);
    Compressor comp = new Compressor(0);
    
    //SmartDashboard
    Preferences pref;
    
    //camera
    int session;
    Image frame;
    
    //Double
    Double LY;
    Double RY;
    Double val;
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	Double SpeedControal = 2.0;
    	
    	//Joystick controal
       	if(-joy_3d.getRawAxis(1)>0.1||-joy_3d.getRawAxis(1)<0.6){
            val = -joy.getRawAxis(1);
       	}	
       	else if(-joy_3d.getRawAxis(1)>0.5){
       		val = 0.5;
       	}
       	else{
       		val = 0.0;	
       	}
    	
    	
    	if(joy.getRawAxis(1)>0.1 || joy.getRawAxis(1)<-0.1){		
            LY = joy.getRawAxis(1);
        }	
        else{
            LY = 0.0 ;	
        }	
    	if(joy.getRawAxis(5)>0.1 || joy.getRawAxis(5)<-0.1){		
            RY = joy.getRawAxis(5);
        }	
        else{
            RY = 0.0 ;	
        }	

    	
    	//motor controal
    	
       	talon_arm.set(val);
       	
    	if(left.get()){
    		talon_left.set(LY/SpeedControal);                     	
    	}	
    	else {
    		talon_left.set(LY/(SpeedControal*2));
    	}
    	
    	if(right.get()){
    		talon_right.set(-RY/SpeedControal);                     	
    	}	
    	else {
    		talon_right.set(-RY/(SpeedControal*2));
    	}
       	
    	SmartDashboard.putNumber("Y value", joy.getRawAxis(1));
    	SmartDashboard.putNumber("Left Motor Encoder Value", -talon_left.get());
    	SmartDashboard.putNumber("Right Motor Encoder Value", talon_right.get());
    	SmartDashboard.putNumber("spSpeedControaleed", (-talon_left.get()+ talon_right.get())/2);
    	SmartDashboard.putNumber("Speed Plot", (-talon_left.get()+ talon_right.get())/2);
    	SmartDashboard.putNumber("LY value", joy.getRawAxis(1));
    	SmartDashboard.putNumber("RY value", joy.getRawAxis(5));
    	SmartDashboard.putNumber("PDP Voltage", pdp.getVoltage());
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
