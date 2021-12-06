
## Task ##

We would like you to implement a test automation solution for GitHub Gists API. Before the implementation, please familiarize yourself with official GitHub documentation as a single source of functional requirements for API behavior. Below you will find a list of technical requirements for the assignment. We expect you to test authorized/unauthorized scenarios where applicable.

## Technical Requirements ##

1. Programming language:
    * Java
    * Python
2. Test approach: you are free to choose between traditional xUnit test code style or BDD (Gherkin-based) testing frameworks
3. Minimum required list of scenarios to be tested:
    * Non-functional attributes:
        * [Gists accessibility][gists-auth]
        * [Rate limiting quotas][rate-limiting]
    * Functional behavior of Gists:
        * [Creating a Gist][gists-create]
        * [Reading a Gist][gists-read]
        * [Deleting a Gist][gists-delete]
        * Listing gists for a user ([authenticated][gists-all] & [unauthenticated][gists-all-public])
4. Tests should verify the functionality of the endpoint, not simply assert against a response code
5. Project instructions should specify:
    * Overview of the language / framework used
    * How to set up dependencies and environment for the project
    * How to execute the tests from command line

### Useful information ###

* Gists API Documentation: [GitHub Gists][github-gists]
* For [authorization][github-oauth2] you can [generate][github-tokens] Personal OAuth token with `gists` scope

### Grading Criteria ###

You will be assessed on the following criteria:

* Project structure and approach
* Code readability and style
* Ease of tests setup and execution
* Self-documentation of the tests
* Logical reasoning behind test scenarios
* Bonus points for providing a setup script or dockerized environment

_Thanks in advance for your time and interest in Picnic!_

[github-labels]: https://help.github.com/articles/about-labels
[github-gists]: https://developer.github.com/v3/gists/
[github-tokens]: https://github.blog/2013-05-16-personal-api-tokens/
[github-oauth2]: https://developer.github.com/v3/#oauth2-token-sent-in-a-header
[gists-auth]: https://developer.github.com/v3/gists/#authentication
[gists-all]: https://developer.github.com/v3/gists/#list-gists-for-the-authenticated-user
[gists-all-public]: https://developer.github.com/v3/gists/#list-gists-for-a-user
[gists-read]: https://developer.github.com/v3/gists/#get-a-gist
[gists-create]: https://developer.github.com/v3/gists/#create-a-gist
[gists-delete]: https://developer.github.com/v3/gists/#delete-a-gist
[rate-limiting]: https://developer.github.com/v3/rate_limit/

--------------------------------------------------------------------------------------------------------------

                             Project Instructions:
-Language used: Java

-Project: Maven based project for API Test Automation of ‘Github’s Gist APIs’ using ‘Rest-Assured’ libraries.

-Framework Design: TestNG

-Pre-Requisites: Java (1.8) & Maven installed with ‘Environment Variables’ properly set as below (download links provided 
 at the end of the document)
     a.	JAVA_HOME = ‘location of jdk’
     b.	MAVEN_HOME = ‘location of apache maven folder’
     c.	M2_HOME = ‘location of apache maven folder’
     d.	Also, ‘Path’ variable under ‘System Variable’ should include ‘bin’ path of jdk & maven folders.

-Overview of project structure:
    ‘src/main/java’ folder contains following packages-
       a. ‘com.constants’ package contains all the endpoints for Gists/RateLimti APIs & all the rate limits values as per rules
           in ‘EndPoint.java’ & ‘RateLimits.java’ files respectively.
       b. ‘com.helpers’ package contains service classes for all applicable API operations for both APIs
       c. ‘com.utils’ package contains ‘JsonUtility’, ‘DateTimeUtility’, ‘ReportingUtility’ files & ‘ConfigManager’ file which
           reads data from ‘config.properties’ file
             
   ‘resources’ folder contains following-
        a. ‘config.properties’ file which has base_url, header value & personal authentication credentials.
        b. ‘testData’ files for POST API calls.
        
   ‘src/test/java’ folder contains following package-
        a. ‘tests’ has functional test like Creating Gist, Reading Gist, Deleting Gist, Listing Gist & non-functional tests like 
            Gists Accessibility & Rate Limit Quotas.

-Setting up dependencies using pom.xml file:
   Following dependencies & plugins were added in pom.xml (download links provided at the end of the document)
      a. For Rest-Assured -> io.rest-assured
      b. For testNG -> org.testng
      c. For Json Parsing -> com.fasterxml.jackson.core
      d. For Reporting -> com.aventstack
      e. Plugins for command line execution -> maven-compiler-plugin & maven-surefire-plugin
      
-Command Line Tests Execution: To execute tests from command line, navigate to the project path in ‘Command Prompt’ & run command 
 ‘mvn clean test - DsuiteXmlFile=testng.xml’. This will all the test cases mentioned in ‘testing.xml’ file (ignore
  initial  warnings, sometimes it takes longer time in initial setup, have patience, it will execute successfully).
 
-Test Execution:
    Tests can be executed in following ways-
      a. To run individual test case, click on Run just below @Test tag
      b. To run individual test file, right click on desired test file & click Run as -> TestNG test
      c. To run all the test cases, right click testing.xml file & click Run as -> TestNG test
      d. To run test cases from command line, navigate to the project path in ‘Command Prompt’ & 
         run command ‘mvn clean test -DsuiteXmlFile=testng.xml’. This will all the test cases mentioned in ‘testing.xml’ file
      e. To run test cases from .bat file, click on ‘Test_SetUp_Script’ bat file.
      f. Individual test file can also be run from testing.xml by keeping only that particular test file name under 
         ‘classes’ tag
         
-Reporting:
      a. ‘ITestListener’ interface has been implemented by ‘ReportUtility’ class. It generates report under 
          ‘test-output/Report/$(timestamp of execution)’ folder. This is called ‘ExtentReport’
      b. Also, there are few default testNG reports being generated like emailable-report.html, index.html & 
         ‘Github Gists API Test Automation’ (suite name from testing.xml file) folder
         
   Note: 
   1. ‘Extent Report’ will always be generated irrespective of execution done from any of the above mentioned ways 
       but default testNG reports will only be generated when execution is done from IDE tool (individually or 
       through testing.xml file).
   2. testNg reports are always overrided after every execution while older ExtentReport is deleted at the start 
      of every execution. If we want to save every report, we can remove ‘deleteDirectory()’ function call from ‘onStart()’ 
      method under ‘ReportUtility.java’ file.

-Download links:
   a. Java - https://www.oracle.com/java/technologies/downloads/
   b. Maven - https://maven.apache.org/download.cgi
   c. Maven Dependencies/Plugins - https://mvnrepository.com/
   
   

                
               



