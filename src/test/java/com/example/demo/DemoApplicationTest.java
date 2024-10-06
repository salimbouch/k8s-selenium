package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.URL;

public class DemoApplicationTest {
    private WebDriver driver;
    private String baseUrl;

    @BeforeMethod
    public void setup() throws Exception {
        String hubUrl = System.getProperty("HUB_URL", "http://selenium-hub:4444/wd/hub");
        baseUrl = System.getProperty("APP_URL", "http://web-app:8080");
        String browser = System.getenv("BROWSER");

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                driver = new RemoteWebDriver(new URL(hubUrl), chromeOptions);
                break;
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new RemoteWebDriver(new URL(hubUrl), firefoxOptions);
                break;
            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new RemoteWebDriver(new URL(hubUrl), edgeOptions);
                break;
            default:
                throw new IllegalArgumentException("Invalid browser specified: " + browser);
        }
    }

    @Test
    public void testButton1() {
        driver.get(baseUrl);
        WebElement button1 = driver.findElement(By.xpath("//button[text()='Button 1']"));
        button1.click();
        WebElement output = driver.findElement(By.id("output"));
        Assert.assertEquals(output.getText(), "Button 1 was pressed");
    }

    @Test
    public void testButton2() {
        driver.get(baseUrl);
        WebElement button2 = driver.findElement(By.xpath("//button[text()='Button 2']"));
        button2.click();
        WebElement output = driver.findElement(By.id("output"));
        Assert.assertEquals(output.getText(), "Button ? was pressed");
    }

    @Test
    public void testButton3Navigation() {
        driver.get(baseUrl);
        WebElement button3 = driver.findElement(By.xpath("//button[text()='Button 3']"));
        button3.click();
        Assert.assertTrue(driver.getCurrentUrl().startsWith(baseUrl + "/page2"),
                "Expected URL to start with " + baseUrl + "/page2, but was " + driver.getCurrentUrl());
    }

    @Test
    public void testReturnToHome() {
        driver.get(baseUrl + "/page2");
        WebElement returnButton = driver.findElement(By.xpath("//button[text()='Return to Home']"));
        returnButton.click();
        Assert.assertTrue(driver.getCurrentUrl().equals(baseUrl) || driver.getCurrentUrl().equals(baseUrl + "/"),
                "Expected URL to be " + baseUrl + " or " + baseUrl + "/, but was " + driver.getCurrentUrl());
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}