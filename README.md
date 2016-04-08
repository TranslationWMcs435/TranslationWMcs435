# TranslationEngine

<p>Team name: Translation Team</p>
<p>Team members: Nathan Chen, Avon Davey, Mark Hutchens, Tho Tran</p>

<hr>

<p>
TranslationEngine automatically generates test cases for Android's graphical user interface (GUI) testing frameworks. It parses GUI events defined in a JSON file and converts them into test cases.
</p>

<p>
Testing software is an important task of software engineering because it assures the quality of the systems that users will use. Moreover, there are different metrics that help to identify whether a software is ready to go to production after executing different tests. In particular for Android applications there are several testing frameworks (e.g. Robotium, Robolectric) that help generate test cases in order to improve the quality of Android applications.
</p>

<p>
Therefore, the goal of this project is to generate a translation engine that will take in a JSON file with GUI events and parse those events into different testing frameworks.
</p>

## Supported frameworks
TranslationEngine currently supports the following testing frameworks: Appium, Espresso, Robolectric (soon), Robotium, UIAutomator

## Dependencies
 - Selenium Client & WebDriver 2.52.0 (Java)
 - Gson 2.2.4
 - Appium Java-Client 3.3.0
 - Appium-Support 1.0.5
 - JUnit 4.11
 - Apache Maven 4

## Structure
The project is structured into three different parts: JSON Parser, Translation Engine, and Execution Engine

1. The JSON Parser takes in a JSON file. This file contains GUI event commands in the form '&lt;component, action&gt;'. The JSON Parser then parses this file and stores them internally in TestCase,  StepTestCase, and Component objects as actions. 
2. The Translation Engine converts actions into commands specific to different testing frameworks. 
3. Finally, the Execution Engine outputs test cases from those commands. It can:
	- Output a Java source file, which can be directly executed (available for Espresso and Appium)
	- Execute those commands directly on a physical Android device or emulator (Appium only)

## Extension
To extend the project to work with another framework, you mostly need to implement a translation engine, which you can base off of preexisting abstract classes in the .trans package. Then you must link that engine up to the Main class with with a couple lines of code.

The major function in your engine will be Translator.steps_iterator(TestCase). This function should iterate through the TestCase that has been created from the user's JSON file. Your Translator should pass the actions to a Functions class, which decides between all the actions a user inputs, and places code corresponding to those actions in the toWrite attribute of your Translator.

Then you tell the Translator to print out toWrite in Main (that code's already there).

In Main, you need to add lines in three places. First, import your package up top. Second, add your new classname to the switch statement that currently (4/7/2016) has "Espresso" and "Appium" and such. Put yours at the end, and follow the pattern. The third place is if you have a live mode, in which case you will need to add code to the next switch statement, or handle it more cleverly above. Live mode is more difficult to support overall, since each framework differs more wildly.

And that is how you make a file-printing extension. I can't give you much more advice on making the Functions class, other than that switch statements are where it's at.
