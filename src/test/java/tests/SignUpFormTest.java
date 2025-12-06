package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.HomePage;
import pages.RegistrationPage;

import java.lang.reflect.Method;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class SignUpFormTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private HomePage homePage;
    private RegistrationPage regPage;


    @BeforeClass
    public void setUpClass() {
        System.out.println("=== Starting Cart Tests ===");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
        driver.get("https://www.israelcart.com");
        homePage = new HomePage(driver);
    }

    @BeforeMethod
    public void beforeEachTest(Method method) {
        Test testAnnotation = method.getAnnotation(Test.class);
        if (testAnnotation != null) {
            int priority = testAnnotation.priority();
            String description = testAnnotation.description();
            System.out.println("=== Starting Test: " + method.getName()
                    + " | Priority: " + priority
                    + " | Description: " + description + " ===");
        }
    }


    @Test(priority = 1, description = "go to sign up form")
    public void goToSignUpForm() {
        regPage = homePage.goToLoginPage();
    }

    @Test(priority = 2, description = "register with all fields empty")
    public void registerWithAllFieldsEmpty() {
        regPage.submit();
        assertEquals(regPage.getFirstNameError(), "The \"First name\" field is empty");
        assertEquals(regPage.getLastNameError(), "The \"Last name\" field is empty");
        assertEquals(regPage.getPhoneError(), "Please enter a valid phone");
        assertEquals(regPage.getEmailError(), "The \"Email address\" field is empty");
        assertEquals(regPage.getPasswordError(), "The \"Password\" field is empty");

    }

    @Test(priority = 3, description = " register with only first name empty")
    public void testFirstNameEmpty() {
        regPage.enterLastName("Doe");
        regPage.enterCountry("Israel");
        regPage.enterPhone("0501234567");
        regPage.enterEmail("test@test.com");
        regPage.enterPassword("Password123");
        regPage.enterPasswordConfirm("Password123");
        regPage.submit();
        assertEquals(regPage.getFirstNameError(), "The \"First name\" field is empty");
    }

    @Test(priority = 4, description = "register with invalid phone containing letters.")
    public void testPhoneInvalidLetters() {
        regPage.enterFirstName("John");
        regPage.enterLastName("Doe");
        regPage.enterCountry("Israel");
        regPage.enterPhone("abcde");
        regPage.enterEmail("test@test.com");
        regPage.enterPassword("Password123");
        regPage.enterPasswordConfirm("Password123");
        regPage.submit();
        assertEquals(regPage.getPhoneError(), "Please enter a valid phone");
    }

    @Test(priority = 5, description = "register with invalid short phone number.")
    public void testPhoneTooShort() {
        regPage.enterFirstName("John");
        regPage.enterLastName("Doe");
        regPage.enterCountry("Israel");
        regPage.enterPhone("0501");
        regPage.enterEmail("test@test1.com");
        regPage.enterPassword("Password123");
        regPage.enterPasswordConfirm("Password123");
        regPage.submit();
        assertEquals(regPage.getPhoneError(), "Please enter a valid phone");
    }

    @Test(priority = 6, description = " register with invalid email missing @")
    public void testEmailMissingAt() {
        regPage.enterFirstName("John");
        regPage.enterLastName("Doe");
        regPage.enterCountry("Israel");
        regPage.enterPhone("0501234567");
        regPage.enterEmail("testtest1.com");
        regPage.enterPassword("Password123");
        regPage.enterPasswordConfirm("Password123");
        regPage.submit();
        assertEquals(regPage.getEmailError(), "Please enter a valid email address");
    }

    @Test(priority = 7, description = " register with invalid email missing domain")
    public void testEmailMissingDomain() {
        regPage.enterFirstName("John");
        regPage.enterLastName("Doe");
        regPage.enterCountry("Israel");
        regPage.enterPhone("0501234567");
        regPage.enterEmail("test@");
        regPage.enterPassword("Password123");
        regPage.enterPasswordConfirm("Password123");
        regPage.submit();
        assertEquals(regPage.getEmailError(), "Please enter a valid email address");
    }

    @Test(priority = 7, description = " register with invalid email with hebrew letters")
    public void testEmailWithHebrew() {
        regPage.enterFirstName("John");
        regPage.enterLastName("Doe");
        regPage.enterCountry("Israel");
        regPage.enterPhone("0501234567");
        regPage.enterEmail("מייל@domain.com");
        regPage.enterPassword("Password123");
        regPage.enterPasswordConfirm("Password123");
        regPage.submit();
        assertEquals(regPage.getEmailError(), "Please enter a valid email address");
    }


    @Test(priority = 8, description = "confirm password mismatch")
    public void testPasswordConfirmMismatch() {
        regPage.enterFirstName("John");
        regPage.enterLastName("Doe");
        regPage.enterCountry("Israel");
        regPage.enterPhone("0501234567");
        regPage.enterEmail("מייל@domain.com");
        regPage.enterPassword("Password123");
        regPage.enterPasswordConfirm("Password321");
        regPage.submit();
        Assert.assertTrue(regPage.getPasswordConfirmError().contains("match"));
    }

    @Test(priority = 9 , description = "register with valid values")
    public void testValidRegistration() {
        String oldUrl = driver.getCurrentUrl();
        regPage.enterFirstName("John");
        regPage.enterLastName("Doe");
        regPage.enterCountry("Israel");
        regPage.enterPhone("0501234567");
        regPage.enterEmail("מייל@domain.com");
        regPage.enterPassword("Password123");
        regPage.enterPasswordConfirm("Password321");
        regPage.submit();
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(oldUrl)));
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue( currentUrl.contains("my-account"));

    }

    @AfterMethod
    public void afterMethod() {

        System.out.println("=== Complete Cart Test ===");
    }
//
//    @AfterClass
//    public void tearDownClass() {
//
//        if (driver != null) {
//            driver.quit();
//        }
//        System.out.println("=== Cart Tests Completed ===");
//    }
}
