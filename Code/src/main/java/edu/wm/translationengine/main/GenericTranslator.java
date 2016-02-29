package edu.wm.translationengine.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.espresso.EspressoFunctions;

/*
 * A superclass with some nice, generic implementations. Some functions should be the same, after all.
 */

public abstract class GenericTranslator implements Translator {
	//private EspressoFunctions ef;
	public static File fout;
	protected FileOutputStream fos;
	protected BufferedWriter bw;
	public static ArrayList<String> toWrite;

	/**
	 * Constructor for the EspressoTranslator class.
	 * @throws IOException 
	 */
	public GenericTranslator() throws IOException{
		//ef = new EspressoFunctions();
		fout = new File("TestFile.java");
		fos = new FileOutputStream(fout);
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		toWrite = new ArrayList<String>();
		setupFile();
	}
	
	
	public void steps_iterator(TestCase tc) throws IOException {
		// TODO Auto-generated method stub

	}

	public void writeToFile(ArrayList<String> al) throws IOException {
		// TODO Auto-generated method stub

	}

	public void setupFile() throws IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Closes the BufferedWriter
	 * @throws IOException
	 */
	public void closeFile() throws IOException{
		bw.close();
	}
	
	public void setFile(File f) throws IOException {
		// Need to set the file from outside.
		fout = f;
		
	}

}
