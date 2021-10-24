package com.utils;

import java.io.File;
import java.text.SimpleDateFormat;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/*ReportUtility class generates report under the folder
'test-output/Report/$(test execution timestamp)'*/

public class ReportUtility  implements ITestListener{
    protected static ExtentReports reports;
    protected static ExtentTest test;

    private static String resultpath = getResultPath();


    public static void deleteDirectory(File file)
    {
        
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }

    private static String getResultPath() {

        resultpath = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        if (!new File(resultpath).isDirectory()) {
            new File(resultpath);
        }
        return resultpath;
    }

    String ReportLocation = "test-output/Report/" + resultpath + "/";

    public void onTestStart(ITestResult result) {

        //test = reports.startTest(result.getMethod().getMethodName());
        test.log(LogStatus.INFO, "Test Started: "+result.getName());
  
    }

    public void onTestSuccess(ITestResult result) {
        test.log(LogStatus.PASS, "Test Successfully Finished "+result.getName());

    }

    public void onTestFailure(ITestResult result) {
        test.log(LogStatus.FAIL, "Test Failed "+result.getName());

    }

    public void onTestSkipped(ITestResult result) {
        test.log(LogStatus.SKIP, "Test Skipped "+result.getName());

    }

    public void onStart(ITestContext context) {
    	deleteDirectory(new File("test-output/Report"));
        System.out.println("ReportLocation: "+ReportLocation);
        reports = new ExtentReports(ReportLocation + "ExtentReport.html");
        test = reports.startTest("");

    }

    public void onFinish(ITestContext context) {
        reports.endTest(test);
        reports.flush();

    }


}
