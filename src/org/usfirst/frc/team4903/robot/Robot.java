
package org.usfirst.frc.team4903.robot;


import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.CameraServer;

import com.ni.vision.NIVision.Image;
/**
 * This is a demo program showing the use of the RobotDrive class.
 * The SampleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're inexperienced,
 * don't. Unless you know what you are doing, complex code will be much more difficult under
 * this system. Use IterativeRobot or Command-Based instead if you're new.
 */

/**
 * CODE BY:
 *   Dilpreet Chana
 *   Roman Seviaryn
 *   Vasav Shaw
 *   Himel Mondal
 *   Joey Zhao
 *   Yasir Khan
 */

public class Robot extends SampleRobot {

    Auto brain;
    RobotDrive myRobot;
    Data library ;
    Controls control;
    Joystick baseControl,clawControl;
    Ultrasonic ultra;
    CameraServer server;
    CameraServer server2;
    
    double Right_x;
    double Right_y;
    double Left_x;
    double Left_y;
   
    double speed_x;
	double speed_y;
	double R_speed_x;
	double R_speed_y;
	
	int encoder_start;
	int encoder_end;
	int encoder_difference;
	
	boolean tote_up;
	boolean tote_down;
	boolean arm_out;
	boolean arm_in;
	boolean claw_safety;
	boolean cam_change;
	boolean drive_change;

    boolean arm7;
    boolean arm8;
    boolean arm9;
    boolean arm10;
    boolean arm11;
    boolean arm12;
	
	double speed;
	
	double claw_y;
	double claw_x;
	double speed_control;
	
	int controler = 1;
	int session;
    Image frame;
    
    
    public Robot() {
        library = new Data(this);
    	
        baseControl = new Joystick(1);
        clawControl = new Joystick(0);
    }
    public boolean [] getArmValues(){
        return new boolean[]  {arm7,arm8,arm9,arm10,arm11,arm12};
    }
    public void robotInit(){
        library.getSensor().init_CTalons(library.getSensor().CTalon1);
        library.getSensor().init_CTalons(library.getSensor().CTalon2);
    }
    /**
     * Drive left & right motors for 2 seconds then stop
     */
    public void autonomous() {
    	library.getAuto().autonomous();
    }

    /**
     * Runs the motors with arcade steering.
     */
    public void operatorControl() {
        double deceleration = 0.01;
        while (isOperatorControl() && isEnabled()) {
        	//System.out.println(CTalon1.getEncPosition());
        	//System.out.print(" " + CTalon2.getEncPosition());
        	System.out.println(library.getSensor().getEncoderPositionC2());
        	
        	Left_x = baseControl.getRawAxis(0);
        	Left_y = baseControl.getRawAxis(1);
        	Right_x = baseControl.getRawAxis(4);
        	Right_y = baseControl.getRawAxis(5);
        	speed_control = clawControl.getRawAxis(3);
        	claw_y = clawControl.getRawAxis(1);
        	claw_x = clawControl.getRawAxis(2);
        	
        	speed_x = Left_x/2.0; 
        	speed_y = Left_y/2.0;
        	R_speed_x = Right_x/2.0;
        	
        	claw_safety = clawControl.getRawButton(1);
        	drive_change = clawControl.getRawButton(2);
        	tote_up = clawControl.getRawButton(5);
        	tote_down = clawControl.getRawButton(3);
        	arm_out = clawControl.getRawButton(6);
        	arm_in = clawControl.getRawButton(4);
        	cam_change = clawControl.getRawButton(2);
            arm7 = clawControl.getRawButton(7);
            arm8 = clawControl.getRawButton(8);
            arm9 = clawControl.getRawButton(9);
            arm10 = clawControl.getRawButton(10);
            arm11 = clawControl.getRawButton(11);
            arm12 = clawControl.getRawButton(12);

           	server = CameraServer.getInstance();
           	server.setQuality(50);   
       		server.startAutomaticCapture("cam1");
        	
       		if (controler == 1 && drive_change) {
       			controler = 2;
       		}
       		if (controler == 2 && drive_change) {
       			controler = 1;
       		}
       		
        	if (Right_x>0) {
        		speed_x += Right_x;
        	}
        	if (Right_x<0) {
        		speed_x -= Right_x;
        	}
        	
        	if (Left_y == 0 && speed_y > 0) {
        		speed_y -= deceleration;
        	}		
        	if (Left_x == 0 && speed_x > 0) {
        		speed_x -= deceleration;
        	}

        	library.getControls().moveBase(controler);
        	library.getControls().moveTote();
        	library.getControls().moveArm();
        	Timer.delay(0.05);
        }
        	
        }
        //NIVision.IMAQdxStopAcquisition(session);

    /**
     * Runs during test mode
     */
    public void test() {
    	long sec = (long) (15 * Math.pow(10, 9));
    	long start = System.nanoTime();
    	int counter = 0;
    	while(System.nanoTime() - start < sec){
    		library.getControls().talonSet(0.0, 0.0, 0.0, 0.0);
    		counter++;
    	}
    	System.out.println(counter);
    }
}