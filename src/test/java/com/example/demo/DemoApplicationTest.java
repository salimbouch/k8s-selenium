package com.example.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DemoApplicationTest {
    private WebDriver driver;

    @BeforeMethod
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
    }

    @Test
    public void testButtons() {
        String baseUrl = System.getenv("APP_URL");
        driver.get(baseUrl);

        WebElement button1 = driver.findElement(By.xpath("//button[text()='Button 1']"));
        button1.click();
        WebElement output = driver.findElement(By.id("output"));
        Assert.assertEquals(output.getText(), "Button 1 was pressed");

        WebElement button2 = driver.findElement(By.xpath("//button[text()='Button 2']"));
        button2.click();
        Assert.assertEquals(output.getText(), "Button 2 was pressed");

        WebElement button3 = driver.findElement(By.xpath("//button[text()='Button 3']"));
        button3.click();
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl + "/page2");

        WebElement returnButton = driver.findElement(By.xpath("//button[text()='Return to Home']"));
        returnButton.click();
        Assert.assertEquals(driver.getCurrentUrl(), baseUrl);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}