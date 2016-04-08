package edu.wm.translationengine.trans;

import edu.wm.translationengine.classes.StepTestCase;

public class StepTestCaseDataPrinter {

	public StepTestCaseDataPrinter(){
	}
	
	public void printData(StepTestCase stc){
		System.out.println("Action: " + stc.getAction() + "\n");
		System.out.println("Component:\n");
		System.out.println("\tdescription: " + stc.getComponent().getDescription() + "\n");
		System.out.println("\theight: " + stc.getComponent().getHeight() + "\n");
		System.out.println("\tid: " + stc.getComponent().getId() + "\n");
		System.out.println("\tindex: " + stc.getComponent().getIndex() + "\n");
		System.out.println("\tisClickable: " + stc.getComponent().isClickable() + "\n");
		System.out.println("\tpositionX: " + stc.getComponent().getPositionX() + "\n");
		System.out.println("\tpositionY: " + stc.getComponent().getPositionY() + "\n");
		System.out.println("\ttext: " + stc.getComponent().getText() + "\n");
		System.out.println("\ttype: " + stc.getComponent().getType() + "\n");
		System.out.println("\twidth: " + stc.getComponent().getWidth() + "\n");
	}
}
