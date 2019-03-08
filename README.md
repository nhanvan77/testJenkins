#  Ms Robot Project

<img src="http://msrobotassistant.com/wp-content/uploads/2017/10/52041E32-79A5-4E82-AAE0-A81092029420.jpeg"/>

This is the homepage for the Ms Robot Selenium Core libraries. These libraries contain both original code created by Ms Robot developers
and open source code from various other projects which are listed further down. With these libraries, consumers have access to extended 
functionality for creating testing suites for Web Applications.

# Ms Robot Software Inc
Ms Robot is a software and professional services company focused on software quality testing and management.  As an organization, we are dedicated to best-in-class QA tools, practices and processes. We are agile and drive continuous improvement with our customers and within our own business.

# License

## Web Application Testing

The core code uses the Java-based Selenium webdriver for GUI testing. It takes the standard Selenium-defined WebElement and divides them into more consumer-friendly Elements, whose names are commonly found in HTML DOMs. The following are Elements, and the prefixes used in the code to identify them, as well as examples using the naming conventions:

|Element|Prefix|Example|
|-------|------|-------|
|Button|btn|btnContinue|
|Checkbox|chk|chkAgreeToTermsAndConditions|
|Label|lbl|lblHeader|
|Link|lnk|lnkCancel|
|Listbox|lst|lstStates|
|Radiogroup|rad|radYesOrNo|
|Textbox|txt|txtUsername|
|Webtable|tab|tabMemberNames|
|Element (Generic Web Element)|ele|eleImage|

# HOW TO RUN MsRobot FROM MAVEN COMMAND

## Debug test from command line:

mvn clean -DforkCount=0 -Dtest=DemoSites_UnitTests#UnitTest01_selectByVisibleText -Dmaven.surefire.debug test

## Run a specific test from command line:
mvn clean -Dtest=DemoSites_UnitTests#UnitTest01_selectByVisibleText test

## Run a test class from command line:
mvn clean -Dtest=DemoSites_UnitTests test

## Debug test from eclipse Run Configuration:
clean -DforkCount=0 -Dtest=DemoSites_UnitTests#UnitTest01_selectByVisibleText -Dmaven.surefire.debug test

## Run a specific test from eclipse Run Configuration:
clean -Dtest=DemoSites_UnitTests#UnitTest01_selectByVisibleText test

## Run multiple tests from eclipse Run Configuration:
clean -Dtest=DemoSites_UnitTests#UnitTest01_selectByVisibleText+UnitTest03_UploadFile+UnitTest07_ExcelData test

## Run a group of tests from eclipse Run Configuration:
clean -Dtest=DemoSites_UnitTests test -Dgroups="smoke"

## Run a test class from eclipse Run Configuration:
clean -Dtest=DemoSites_UnitTests test

