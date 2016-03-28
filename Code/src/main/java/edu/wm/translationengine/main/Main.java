package edu.wm.translationengine.main;

import edu.wm.translationengine.classes.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wm.translationengine.espresso.EspressoTranslator;
import edu.wm.translationengine.robotium.RobotiumTranslator;
import edu.wm.translationengine.appium.*;
import edu.wm.translationengine.trans.Translator;
import edu.wm.translationengine.uiautomator.UiAutomatorTranslator;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	private static TestCase tc = null;
	/*
	 * Produce a TestCase to later be run into the espresso (or appium) translator.
	 * Deprecated as of 3/3/2016, new version feeds in a filename.
	 */
    public static TestCase parse() throws IOException {
    	TestCase list_of_actions = null;
    	//If there's a need to get rid of hard coding, this is where to do it.
    	//I put the file right next to this one. But there will probably be an I/O folder later.
        InputStream stream = new FileInputStream("./IO/gmdice_simple.txt");
        Reader reader = new InputStreamReader(stream, "UTF-8");
        try{ 
            Gson gson = new GsonBuilder().create();
            list_of_actions = gson.fromJson(reader,TestCase.class);
            return list_of_actions;
        } catch (Exception e){
        	System.out.println("Parsing failed. Returning null.");
            return list_of_actions;
        }
    }
    
	/*
	 * Produce a TestCase to later be run into the espresso (or appium) translator.
	 */
    public static TestCase parse(String filename) throws IOException {
    	TestCase list_of_actions = null;
    	//If there's a need to get rid of hard coding, this is where to do it.
    	//I put the file right next to this one. But there will probably be an I/O folder later.
        InputStream stream = new FileInputStream(filename);
        Reader reader = new InputStreamReader(stream, "UTF-8");
        try{ 
            Gson gson = new GsonBuilder().create();
            list_of_actions = gson.fromJson(reader,TestCase.class);
            return list_of_actions;
        } catch (Exception e){
        	System.out.println("Parsing failed. Returning null.");
            return list_of_actions;
        }
    }
	
	/*
	 * pretty much calls parse.
	 */
	public static void main(String [] args){
		Translator et = null;
		//Espresso == 0, Appium == 1. Others to follow.
		int environment_switch = 0;
		//Whether to print to the output file or not.
		int to_print = 0;
		//File name for printing.
		String filename = "./IO/gmdice_simple_2.txt";
		//
		String outname = "./IO/TestFile.java";
		
		if(args.length > 0){
			environment_switch = Integer.parseInt(args[0]);
			to_print = Integer.parseInt(args[1]);
			if(args.length > 2){
				filename = args[2];
				if(args.length > 3){
					outname = args[3];
				}
			}
		}else{
			Scanner user_input = new Scanner( System.in );
			System.out.println("What environment are you using? Espresso (0), Appium (1), UiAutomator (2), or Robotium (3)?");
			environment_switch = Integer.parseInt(user_input.next());
			if(environment_switch == 1){
				System.out.println("\nWould you like a .java file (0) or a server (1)?");
				to_print = Integer.parseInt(user_input.next());
			}else{
				to_print = 0;
			}
		}
		

		
        String toreturn = "Fail case";
        try {
            tc = parse(filename);
            switch (environment_switch){
	            case 0:
		            et = new EspressoTranslator();
		            break;
	            case 1:
	            	//Have a translator for Appium.
	            	et = new AppiumTranslator();
	            	break;
	            case 2:
	            	et = new UiAutomatorTranslator();
	            case 3:
	            	et = new RobotiumTranslator();
            }
            et.setFile(outname);
            et.steps_iterator(tc);
            toreturn = "Successfully parsed";
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Either print to a file or start up a server.
        switch(to_print){
        	case 0:
        		//Use the print-to-file function to make the tester go in a file.
				try {
					et.writeToFile();
					et.closeFile();
				} catch (IOException e) {
					// Had to have catch.
					e.printStackTrace();
				}
        		break;
        	case 1:
        		AppiumLive al = new AppiumLive();
			try {
				al.start(tc);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AppiumException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        		break;
        }
        
        
        System.out.printf(toreturn);
		
		return;
	}
}
