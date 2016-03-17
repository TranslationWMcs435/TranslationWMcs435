package edu.wm.translationengine.trans;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import edu.wm.translationengine.classes.TestCase;
import edu.wm.translationengine.espresso.EspressoFileModifier;
import edu.wm.translationengine.espresso.EspressoFunctions;

/*
 * A superclass with some nice, generic implementations. Some functions should be the same, after all.
 */

public abstract class GenericTranslator implements Translator {
	//private EspressoFunctions ef;
	public static File fout;
	protected FileOutputStream fos;
	public static BufferedWriter bw;
	public static ArrayList<String> toWrite;
	public EspressoFileModifier fm;

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
		fm = new EspressoFileModifier();
		fm.setupFileImports();
	}
	
	
	public void steps_iterator(TestCase tc) throws IOException {
		// Should use the SupportedCommands interface as it iterates through the steps.

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
	
	public void setFile(String f) throws IOException {
		// Need to set the file from outside. Deletes old option for output file. Hopefully not a problem.
		bw.close();
		fout.delete();
		fout = new File(f);
		fos = new FileOutputStream(fout);
		bw = new BufferedWriter(new OutputStreamWriter(fos));
		toWrite = new ArrayList<String>();
		fm.setupFileImports();
		
	}

}
