package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.URL;

public class DemoApplicationTest {
    private WebDriver driver;

    @BeforeMethod
    @Parameters("browser")
    public void setup(String browser) throws Exception {
        String hubUrl = System.getProperty("HUB_URL", "http://localhost:4444/wd/hub");

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            driver = new RemoteWebDriver(new URL(hubUrl), options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            driver = new RemoteWebDriver(new URL(hubUrl), options);
        } else {
            throw new IllegalArgumentException("Invalid browser specified");
        }
    }

    @Test
    public void buttonTest() {
        String baseUrl = System.getProperty("APP_URL", "http://web-app:8080");
        driver.get(baseUrl);

        WebElement button1 = driver.findElement(By.xpath("//button[text()='Button 1']"));
        button1.click();
        WebElement output = driver.findElement(By.id("output"));
        Assert.assertEquals(output.getText(), "Button ? was pressed");

        WebElement button2 = driver.findElement(By.xpath("//button[text()='Button 2']"));
        button2.click();
        Assert.assertEquals(output.getText(), "Button ? was pressed");

        WebElement button3 = driver.findElement(By.xpath("//button[text()='Button 3']"));
        button3.click();
        Assert.assertTrue(driver.getCurrentUrl().startsWith(baseUrl + "/page2"),
                "Expected URL to start with " + baseUrl + "/page2, but was " + driver.getCurrentUrl());

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