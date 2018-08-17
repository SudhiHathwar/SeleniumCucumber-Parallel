package com.test.Utils;


import com.aventstack.extentreports.ExtentTest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentTestManager {

    static Map<Long, ExtentTest> extentPool = new ConcurrentHashMap<>();

    public static synchronized void setExtentTest ( ExtentTest extentTest ) {
        extentPool.put(Thread.currentThread().getId(), extentTest);
    }

    public static synchronized ExtentTest getTest () {
        return extentPool.get(Thread.currentThread().getId());
    }
}

