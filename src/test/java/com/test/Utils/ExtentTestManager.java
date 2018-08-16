package com.test.Utils;


import com.aventstack.extentreports.ExtentTest;

public class ExtentTestManager {

    private static ThreadLocal<ExtentTest> extentPool = new ThreadLocal<ExtentTest>();

    public static void setExtentTest ( ExtentTest extentTest ) {
        extentPool.set(extentTest);
    }

    public static ExtentTest getTest () {
        return extentPool.get();
    }
}

