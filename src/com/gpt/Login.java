package com.gpt;

import com.tools.TextFileReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.security.Key;
import java.util.Scanner;

public class Login {

    private String userEmail;
    private String password;

    public static void login(WebDriver driver) {

        // Step 1: We confirm that we are at the start page
        confirmStartPage(driver);
        // Step 2: We click on the login option
        clickStartLogin(driver);
        // Step 3: We confirm that we are in the credentials page 1
        confirmCredentialsPage1(driver);
        // Step 4: Enter credentials on first page
        credentialsPage1(driver);
        // Step 5: We confirm that we are in the credentials page 2
        confirmCredentialsPage2(driver);
        // Step 6: Enter password on second page and login
        credentialsPage2(driver);
        // Step 7: Confirm main page
        confirmMainPage(driver);

    }

    private static String getUserEmail() {
        String credentials = TextFileReader.readFileContents("/home/edual/GitHub/ChatAPI2/bin/credentials.txt");
        return credentials.split("\n")[0];
    }

    private static String getPassword() {
        String credentials = TextFileReader.readFileContents("/home/edual/GitHub/ChatAPI2/bin/credentials.txt");
        return credentials.split("\n")[1];
    }

    private static void confirmStartPage(WebDriver driver) {
        int maxRefreshTime = 3;
        WebDriverWait wait = new WebDriverWait(driver, 5); // Maximum wait time of 5 seconds
        while (maxRefreshTime > 0) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div > div")));
                System.out.println("Starting page was loaded!");
                return;
            } catch (TimeoutException e) {
                System.out.println("...");
                maxRefreshTime--;
                driver.navigate().refresh();
            }
        }
        System.out.println("Could not load starting page!");
        System.exit(1);
    }

    private static void clickStartLogin(WebDriver driver) {
        // 3-step process to find the login button in any browser instance
        try {
            // Find the parent element x1
            WebElement x1 = driver.findElement(By.cssSelector("body > div > div"));
            // Element inside x1
            WebElement x2 = x1.findElement(By.xpath("./*[last()]"));
            // Element inside x2
            WebElement x3 = x2.findElement(By.cssSelector("div > div > button"));
            // Clicking login button
            x3.click();
            System.out.println("Navigating to credentials page 1!");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("Element not found.");
            System.exit(1);
        }
    }

    private static void confirmCredentialsPage1(WebDriver driver) {
        int maxRefreshTime = 3;
        WebDriverWait wait = new WebDriverWait(driver, 5); // Maximum wait time of 5 seconds
        while (maxRefreshTime > 0) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input")));
                System.out.println("Credentials page 1 was loaded!");
                return;
            } catch (TimeoutException e) {
                System.out.println("...");
                maxRefreshTime--;
                driver.navigate().refresh();
            }
        }
        System.out.println("Could not load credentials page 1!");
        System.exit(1);
    }

    private static void credentialsPage1(WebDriver driver) {
        WebElement inputEmail = driver.findElement(By.cssSelector("input.input"));
        String userEmail = getUserEmail();
        inputEmail.sendKeys(userEmail + Keys.ENTER);
        System.out.println("Navigating to credentials page 2!");
    }

    private static void confirmCredentialsPage2(WebDriver driver) {
        int maxRefreshTime = 3;
        WebDriverWait wait = new WebDriverWait(driver, 5); // Maximum wait time of 5 seconds
        while (maxRefreshTime > 0) {
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input.input")));
                System.out.println("Credentials page 2 was loaded!");
                return;
            } catch (TimeoutException e) {
                System.out.println("...");
                maxRefreshTime--;
                driver.navigate().refresh();
            }
        }
        System.out.println("Could not load credentials page 2!");
        System.exit(1);
    }

    private static void credentialsPage2(WebDriver driver) {
        WebElement inputPassword = driver.findElement(By.cssSelector("input.input"));
        String password = getPassword();
        inputPassword.sendKeys(password + Keys.ENTER);
        System.out.println("Logging in!");
    }

    private static void confirmMainPage(WebDriver driver) {
        int maxRefreshTime = 3;
        WebDriverWait wait = new WebDriverWait(driver, 5); // Maximum wait time of 5 seconds
        while (maxRefreshTime > 0) {
            try {
                WebElement endPart = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".justify-end")));
                System.out.println("Main page was loaded!");
                WebElement endBtn = endPart.findElement(By.cssSelector("button"));
                endBtn.click();
                return;
            } catch (TimeoutException e) {
                System.out.println("...");
                maxRefreshTime--;
                driver.navigate().refresh();
            }
        }
        System.out.println("Could not load main page!");
        System.exit(1);
    }

}
