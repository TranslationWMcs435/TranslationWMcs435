import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;


import java.nio.charset.StandardCharsets;


public class Main {
	/*
	 * Produce a series of actions to later be run into the espresso translator.
	 */
    public static String parse() throws IOException {
        int b = 0;
        /*FileInputStream i = new FileInputStream(f);*/
        /*InputStream stream = Main.class.getResourceAsStream("json.json");*/
        InputStream stream = new FileInputStream("./jsonsource.json");
        System.out.println(stream);
        Reader reader = new InputStreamReader(stream, "UTF-8");
        try{
            b++;  
            Gson gson = new GsonBuilder().create();
            b++;
            Action a = gson.fromJson(reader, Action.class);
            b++;

            return a.tostring();
            
        } catch (Exception e){
            return "Failed spectacularly: " + b;
        }

    }
	
	
	public static void main(String [] args){
		
        String toreturn = "Fail case";
        String testtext;
        try {
            toreturn = parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        System.out.printf(toreturn);
		
		return;
	}
}
