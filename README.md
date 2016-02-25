# TranslationEngine

Team name: Translation Team
Team members: Nathan Chen, Avon Davey, Mark Hutchens, Tho Tran

<hr>

TranslationEngine automatically generates test cases for Android's user interface (UI) testing frameworks. It parses UI events defined in a JSON file and converts them into test cases.

Testing software is an important task of software engineering because it assures the quality of the systems that users will use. Moreover, there are different metrics that help to identify whether a software is ready to go to production after executing different tests. In particular for Android applications there are several testing frameworks (e.g. Robotium, Robolectric) that help generate test cases in order to improve the quality of Android applications.

The goal of this project is to generate a translation engine that will take in a JSON file with GUI events and parse those events into different testing frameworks.

## Supported frameworks
TranslationEngine currently supports the following testing frameworks: Espresso, Appium

## Dependencies
 - Selenium Client & WebDriver 2.52.0 (Java)
 - Gson 2.2.4
 - Appium Java-Client 3.3.0
 - Appium-Support 1.0.5
 - JUnit 4.11
 - Apache Maven 4

## Structure
The project is structured into three different parts: JSON Parser, Translation Engine, and Execution Engine

1. The JSON Parser takes in a JSON file. This file contains GUI event commands in the form '&lt;component, action&gt;'. The JSON Parser then parses this file and stores them internally in TestCase,  StepTestCase, and TestCase objects as actions. 
2. The Translation Engine converts actions into commands specific to different testing frameworks. 
3. Finally, the Execution Engine outputs test cases from those commands. It can:
	- Output a Java source file, which can be directly executed (available for Espresso and Appium)
	- Execute those commands directly on a physical Android device or emulator (Appium only)
