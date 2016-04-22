package edu.wm.translationengine.main;

//import edu.semeru.wm.qextractor.processors.SourceCodeProcessor;
import edu.wm.translationengine.classes.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wm.translationengine.espresso.EspressoTranslator;
import edu.wm.translationengine.espresso.EspressoChecker;
import edu.wm.translationengine.robotium.RobotiumChecker;
import edu.wm.translationengine.robotium.RobotiumTranslator;
import edu.wm.translationengine.appium.*;
import edu.wm.translationengine.trans.AbstractChecker;
import edu.wm.translationengine.trans.Translator;
import edu.wm.translationengine.uiautomator.UiAutomatorTranslator;
import edu.wm.translationengine.uiautomator.UiAutomatorChecker;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import org.eclipse.core.internal.resources.Folder;


public class Main {
	private static TestCase tc = null;
    
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
     * Calls to make a server. Only goes off w/ Appium selection.
     */
	private static void appiumServer(String filename){
		try {
			tc = parse(filename);
			AppiumLive al = new AppiumLive();
			al.start(tc);
		} 
		//TODO: Handle some of these exceptions, maybe.
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AppiumException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
	/*
	 * pretty much calls parse.
	 */
	public static void main(String [] args){
		String[] instructs;
		final Charset cs = Charset.defaultCharset();
		Translator et = null;
		AbstractChecker checker = null;
//		SourceCodeProcessor processor = null;
		
		//Espresso == 0, Appium == 1. Others to follow.
		String environment_switch = "0";
		//Whether to print to the output file or not.
		String to_print = "0";
		//File name for printing.
		String filename = "./IO/mileage_simple_type.txt";
		//
		String outname = "./IO/TestFile.java";
		
		if(args.length > 0){
			if (args[0].contains(".txt")){
				Path file = FileSystems.getDefault().getPath("IO", args[0]);
				List<String> fileArray;
				try {
					fileArray = Files.readAllLines(file, cs);
					//fileArray = Files.readAllLines(file);
					//The lazy way of making args continue to work in future bits.
					instructs = fileArray.get(0).split(" ");
					System.out.println(fileArray.get(0));
					//System.out.println(instructs[0] + instructs[1]);
				} catch (IOException e) {
					System.out.println("Given file name does not exist.");
					instructs = args;
					e.printStackTrace();
				}
			}else{
				instructs = args;
			}
			environment_switch = instructs[0];
			to_print = instructs[1];
			//Optional args for filenames. Input first, then output.
			if(instructs.length > 2){
				filename = instructs[2];
				if(instructs.length > 3){
					outname = instructs[3];
				}
			}
			
			
		}else{
			Scanner user_input = new Scanner( System.in );
			System.out.println("What environment are you using? Espresso (0), Appium (1), UiAutomator (2), or Robotium (3)?");
			environment_switch = user_input.next();
			if(environment_switch.equals("1") || environment_switch.equals("Appium")){
				System.out.println("\nWould you like a .java file (0) or a server (1)?");
				to_print = user_input.next();
				if (to_print.equals("1") || to_print.equals("live")){
					//Server means we want to do something totally different.
					appiumServer(filename);
					return;
				}
			}else{
				to_print = "0";
			}
			System.out.println("What is your input file?");
			filename = user_input.next();
			System.out.println("What is your output file?");
			outname = user_input.next();
		}
		
        String toreturn = "Fail case";
        try {
            tc = parse(filename);
            switch (environment_switch){
	            case "0":
	            case "Espresso":
	            	checker = new EspressoChecker();
	            	if (checker.runCheck(tc)){
	            		et = new EspressoTranslator();
	            		//processor = new SourceCodeProcessor(); //Turn into EspressoCodeProcessor, etc.
	            	}else{
	            		System.out.println("Error in JSON file, not accepted by checker.");
	            		return;
	            	}
		            break;
	            case "1":
	            case "Appium":
	            	//Add checker for Appium.
	            	checker = new AppiumChecker();
	            	if (checker.runCheck(tc)){
	            		et = new AppiumTranslator();
	            		//processor = new SourceCodeProcessor();
	            	}else{
	            		System.out.println("Error in JSON file, not accepted by checker.");
	            		return;
	            	}
	            	break;
	            case "2":
	            case "UIAutomator":
	            	checker = new UiAutomatorChecker();
	            	if (checker.runCheck(tc)){
	            		et = new UiAutomatorTranslator();
	            		//processor = new SourceCodeProcessor();
	            	}else{
	            		System.out.println("Error in JSON file, not accepted by checker.");
	            		return;
	            	}
	            	break;
	            case "3":
	            case "Robotium":
	            	checker = new RobotiumChecker();
	            	if (checker.runCheck(tc)){
	            		et = new RobotiumTranslator();
	            		//processor = new SourceCodeProcessor();
	            	}else{
	            		System.out.println("Error in JSON file, not accepted by checker.");
	            		return;
	            	}
	            	break;
            }
            et.setFile(outname);
            et.steps_iterator(tc);
            toreturn = "Successfully parsed";
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //Either print to a file or start up a server.
        switch(to_print){
        	case "0":
        	case "file":
        		//Use the print-to-file function to make the tester go in a file.
				try {
					et.writeToFile();
					et.closeFile();
				} catch (IOException e) {
					// Had to have catch.
					e.printStackTrace();
				}
        		break;
        	case "1":
        	case "live":
        		//Handled higher up, inside a function. I don't want to make et if it isn't gonna get used.
        		break;
        }
        
        System.out.printf(toreturn);
        
        /*
         * This is where we add the stuff for AST parsing.
         */
        File outfile = new File(outname);
        File binaries = new File("/libs4ast/temp.txt");
        String binariesFolder = binaries.getAbsolutePath().replace("/temp.txt", "");
        //processor.processFolder(outfile.getAbsolutePath(), binariesFolder);
        
		
		return;
	}
}
