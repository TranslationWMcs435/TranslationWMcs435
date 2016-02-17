public class Action {
    /*
    component id
    component name
    content description
    text
    class name
    type of action (CLICK,LONG_CLICK,SWIPE,TYPE)
    x-location, y-location
    width
    height
    isClickable
    */

    private String id;
    private String name;
    private String description;
    private String text;
    private String class_name;
    private String type_action;
    private String x_loc;
    private String y_loc;
    private String width;
    private String height;
    private String is_clickable;
	
    public String tostring() {
        return ("Got a string, so is an object. ID is: " + id + " " + width);
    }
    
    public String[] getItems(){
    	String[] toreturn = new String[11];
    	toreturn[0] = id;
    	toreturn[1] = name;
    	toreturn[2] = description;
    	toreturn[3] = text;
    	toreturn[4] = class_name;
    	toreturn[5] = type_action;
    	toreturn[6] = x_loc;
    	toreturn[7] = y_loc;
    	toreturn[8] = width;
    	toreturn[9] = height;
    	toreturn[10] = is_clickable;
    	return toreturn;
    }
    
    /*
     * Make a series of strings that correlate to espresso code.
     */
    public String[] makeSelf(){
    	String[] toreturn = new String [1];
    	return toreturn;
    }
    
}