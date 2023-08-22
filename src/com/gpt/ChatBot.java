package com.gpt;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Scanner;

import static com.tools.TextFormatter.formatText;

public class ChatBot {

    private WebDriver driver;

    public ChatBot() {
        driverSetUp();
    }

    private void driverSetUp() {
        System.setProperty("webdriver.chrome.driver", "/home/edual/Apps/chromedriver_linux64/chromedriver");
        ChromeOptions chromeOptions = new ChromeOptions();
        // Set the User-Agent header
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36";
        chromeOptions.addArguments("user-agent=" + userAgent);
        // Create a Chrome driver instance with the modified options
        this.driver = new ChromeDriver(chromeOptions);

        this.driver.get("https://chat.openai.com/auth/login");
        // Login process
        Login.login(this.driver);
    }

    public String processPrompt(String promptInput) throws InterruptedException {
        sendPrompt(promptInput);
        String output = getPrompt();
        return output;
    }

    private void sendPrompt(String promptInput) {
        WebElement textInput = this.driver.findElement(By.cssSelector("textarea"));
        // Alternative to sending prompt (Does not activate bot checker)
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript(
                "var textInput = document.querySelector(\"textarea\");" +
                        "var sendBtn = textInput.parentElement.querySelector(\"button\");" +
                        "textInput.value = \"" + promptInput + "\";" +
                        "var inputEvent1 = new Event('input', { bubbles: true });" +
                        "textInput.dispatchEvent(inputEvent1);" +
                        "setTimeout(function() {" +
                        "    sendBtn.click();" +
                        "}, 1000);"
        );

        // Sending prompt
//        WebElement sendBtn = textInput.findElement(By.xpath("./parent::*/button"));
//        sendBtn.click();
    }

    private String getPrompt() throws InterruptedException {
        verifyCaptcha();
        this.waitForTextGeneration();
        List<WebElement> textHistory = getTextboxHistory();
        WebElement outputPrompt = textHistory.get(textHistory.size() - 1); // Retrieving last msg Box
        String outputText = outputPrompt.getText();
        return outputText;
    }

    private List<WebElement> getTextboxHistory() {
        List<WebElement> textHistory = this.driver.findElements(By.cssSelector("div.group.text-token-text-primary"));
        return textHistory;
    }

    private void waitForTextGeneration() throws InterruptedException {
        // Trying to get the element first
        int maxTries = 30;
        WebElement regenBox = null;
        while(maxTries > 0) {
            regenBox = this.getRegenBox();
            if (regenBox != null) {
                if (regenBox.getText().equals("Regenerate")) {
                    System.out.println("Message received!");
                    break;
                }
                System.out.print(".");
            }
            maxTries--;
            Thread.sleep(500);  // Waiting 1 sec before trying again
        }

        if (maxTries == 0) {
            System.out.println("Could not retrieve response!");
            System.exit(1);
        }

    }

    private void verifyCaptcha() throws InterruptedException {
        int maxTries = 8;
        while (maxTries > 0) {
            try {
                WebElement capthaIdentifier = driver.findElement(By.cssSelector("[aria-hidden=\"false\"]"));
                System.out.println("Captcha was found!");
                System.out.println("Must pass test before continuing.");
                passCaptcha();
                return;

            } catch (Exception e) {
                Thread.sleep(500);
                maxTries--;
            }
        }
    }

    private void passCaptcha() {
        while (true) {
            // Keep checking for Captcha until completion
            try {
                WebElement capthaIdentifier = driver.findElement(By.cssSelector("[aria-hidden=\"false\"]"));
                Thread.sleep(500);
            }
            catch (Exception e) {
                return;
            }
        }
    }

    private WebElement getRegenBox() {

        // Steps for getting the Regeneration box element
        try {

            WebElement x1 = driver.findElement(By.cssSelector("body > div > div"));

            WebElement x2 = x1.findElement(By.xpath("./*[last()]"));

            WebElement x3 = x2.findElement(By.cssSelector("div > main > div"));

            WebElement x4 = x3.findElement(By.xpath("./*[last()]"));

            WebElement x5 = x4.findElement(By.cssSelector("form > div > div > div"));

            WebElement x6 = x5.findElement(By.xpath("./*[last()]"));

            WebElement x7 = x6.findElement(By.cssSelector("div > button > div"));

            return x7;

        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("RegenBox still not found");
            return null;
        }

    }

    public void promptTester() throws InterruptedException {
        String inputPrompt = "";
        String result = "";
        String cleanResult = "";
        System.out.println("\n------------------");
        System.out.println("  ChatBot Tester");
        System.out.println("------------------\n");
        String keepGoing = "y";
        while (keepGoing.equals("y")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter prompt: ");
            inputPrompt = scanner.nextLine();
            result = processPrompt(inputPrompt);
            cleanResult = formatText(result, 60);
            System.out.println(cleanResult);
            System.out.print("Continue?(y/n): ");
            keepGoing = scanner.nextLine();
        }
    }


}
