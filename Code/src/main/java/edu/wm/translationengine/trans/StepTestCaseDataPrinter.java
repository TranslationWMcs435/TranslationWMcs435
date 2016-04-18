package edu.wm.translationengine.trans;

import edu.wm.translationengine.classes.StepTestCase;

public class StepTestCaseDataPrinter {

	public StepTestCaseDataPrinter(){
	}
	
	public void printData(StepTestCase stc){
		System.out.println("Action: " + stc.getAction());
		System.out.println("Component:");
		System.out.println("\tdescription: " + stc.getComponent().getDescription());
		System.out.println("\theight: " + stc.getComponent().getHeight());
		System.out.println("\tid: " + stc.getComponent().getId());
		System.out.println("\tindex: " + stc.getComponent().getIndex());
		System.out.println("\tisClickable: " + stc.getComponent().isClickable());
		System.out.println("\tpositionX: " + stc.getComponent().getPositionX());
		System.out.println("\tpositionY: " + stc.getComponent().getPositionY());
		System.out.println("\ttext: " + stc.getComponent().getText());
		System.out.println("\ttype: " + stc.getComponent().getType());
		System.out.println("\twidth: " + stc.getComponent().getWidth());
	}
}