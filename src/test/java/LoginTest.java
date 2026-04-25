package com.devops.lab;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {

    @Test
    void test_login_with_incorrect_credentials() {

        System.setProperty("webdriver.chrome.driver", "/usr/bin/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            driver.get("http://103.139.122.250/");

            driver.findElement(By.name("email")).sendKeys("qasimsb@malik.com");
            driver.findElement(By.name("password")).sendKeys("abcdefg");
            driver.findElement(By.id("m_login_signin_submit")).click();

            WebElement error = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//*[contains(.,'Incorrect') or contains(.,'invalid')]")
                    )
            );

            String errorText = error.getText();

            assertTrue(
                    errorText.contains("Incorrect") || errorText.contains("invalid")
            );

        } finally {
            driver.quit();
        }
    }
}