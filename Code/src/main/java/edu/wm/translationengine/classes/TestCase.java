/**
 * TestCase.java
 * 
 * Created on Feb 17, 2016, 2:37:51 PM
 *
 */
package edu.wm.translationengine.classes;

import java.util.ArrayList;
import java.util.List;

/**
 * {Insert class description here}
 *
 * @author Carlos Bernal
 * @since Feb 17, 2016
 */
public class TestCase {

    private String appName;
    private String packageName;
    private String mainActivity;
    private List<StepTestCase> stepTestCases = new ArrayList<StepTestCase>();

    /**
     * @return the stepTestCases
     */
    public List<StepTestCase> getSteps() {
        return stepTestCases;
    }

    /**
     * @param stepTestCases
     *            the stepTestCases to set
     */
    public void setSteps(List<StepTestCase> stepTestCases) {
        this.stepTestCases = stepTestCases;
    }

    /**
     * @return the appName
     */
    public String getAppName() {
        return appName;
    }

    /**
     * @param appName
     *            the appName to set
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName
     *            the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the mainActivity
     */
    public String getMainActivity() {
        return mainActivity;
    }

    /**
     * @param mainActivity
     *            the mainActivity to set
     */
    public void setMainActivity(String mainActivity) {
        this.mainActivity = mainActivity;
    }
}
