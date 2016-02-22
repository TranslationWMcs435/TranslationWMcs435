import classes.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import espresso.EspressoTranslator;

import java.io.*;


public class Main {
	private static TestCase tc = null;
	/*
	 * Produce a TestCase to later be run into the espresso (or appium) translator.
	 */
    public static TestCase parse() throws IOException {
    	TestCase list_of_actions = null;
    	//If there's a need to get rid of hard coding, this is where to do it.
        InputStream stream = new FileInputStream("./gmdice_simple.txt");
        System.out.println(stream);
        Reader reader = new InputStreamReader(stream, "UTF-8");
        try{ 
            Gson gson = new GsonBuilder().create();
            list_of_actions = gson.fromJson(reader,TestCase.class);
            System.out.println("Parsing... Succeeds?");
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
		
        String toreturn = "Fail case";
        try {
            tc = parse();
            
            EspressoTranslator et = new EspressoTranslator();
            et.steps_iterator(tc);
            
            toreturn = "Successfully parsed";
            System.out.println(tc.getAppName());
            System.out.println(tc.getPackageName());
            System.out.println(tc.getSteps().get(0).getAction());
            System.out.println(tc.getSteps().get(0).getComponent().getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.printf(toreturn);
		
		return;
	}
}
