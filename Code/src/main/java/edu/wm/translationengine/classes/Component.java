/**
 * Component.java
 * 
 * Created on Feb 17, 2016, 2:38:18 PM
 *
 */
package edu.wm.translationengine.classes;

/**
 * {Insert class description here}
 *
 * @author Carlos Bernal
 * @since Feb 17, 2016
 */
public class Component {

    private String id;
    private String type;
    private String positionX;
    private String positionY;
    private String width;
    private String height;
    private String text;
    private String description;
    private String index;
    private boolean isClickable;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the positionX
     */
    public String getPositionX() {
        return positionX;
    }

    /**
     * @param positionX
     *            the positionX to set
     */
    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    /**
     * @return the positionY
     */
    public String getPositionY() {
        return positionY;
    }

    /**
     * @param positionY
     *            the positionY to set
     */
    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    /**
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(String heigh) {
        this.height = heigh;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the isClickable
     */
    public boolean isClickable() {
        return isClickable;
    }

    /**
     * @param isClickable
     *            the isClickable to set
     */
    public void setClickable(boolean isClickable) {
        this.isClickable = isClickable;
    }

    /**
     * @return the index
     */
    public String getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(String index) {
        this.index = index;
    }

}
