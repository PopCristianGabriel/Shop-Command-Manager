package com.company;

import Controller.Controller;
import Services.FileHandler;
import Tests.TestAll;
import Tests.TestClient;
import Tests.TestShop;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, ParseException {
        String directoryPath = "F:\\ShopManager\\resources\\";
        String outputPath = "F:\\ShopManager\\comenzi.txt";
        TestAll testAll = new TestAll();
        testAll.test_all();
        Controller controller = new Controller(directoryPath,outputPath);
        controller.solve();
    }
}

