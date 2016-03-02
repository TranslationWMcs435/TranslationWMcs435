package edu.wm.translationengine.main;

import edu.wm.translationengine.classes.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wm.translationengine.espresso.EspressoTranslator;
import edu.wm.translationengine.trans.Translator;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {
	private static TestCase tc = null;
	/*
	 * Produce a TestCase to later be run into the espresso (or appium) translator.
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
	 * pretty much calls parse.
	 */
	public static void main(String [] args){
		Translator et = null;
		//Espresso == 0, Appium == 1. Others to follow.
		int environment_switch = 0;
		//Whether to print to the output file or not.
		int to_print = 0;
		//File name for printing.
		String filename = "./IO/tester.java";
		
		if(args.length > 0){
			environment_switch = Integer.parseInt(args[0]);
			to_print = Integer.parseInt(args[1]);
		}else{
			Scanner user_input = new Scanner( System.in );
			System.out.println("What environment are you using? Espresso (0) or Appium (1)?");
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
            tc = parse();
            switch (environment_switch){
	            case 0:
		            et = new EspressoTranslator();
		            et.steps_iterator(tc);
		            break;
	            case 1:
	            	//Have a translator for Appium.
	            	
	            	break;
            }
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
        		break;
        }
        
        
        System.out.printf(toreturn);
		
		return;
	}
}
