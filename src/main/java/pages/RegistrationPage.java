package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegistrationPage {

    private WebDriver driver;
    private WebDriverWait wait;


    private By switchToSignUp = By.cssSelector(".switch-form[data-id = \"signup\"]");
    private By firstName = By.cssSelector("#reg_firstname");
    private By lastName = By.cssSelector("#reg_lastname");
    private By country = By.cssSelector("#register-country");
    private By phone = By.cssSelector("#reg_phone");
    private By email = By.cssSelector("#reg_email");
    private By password = By.cssSelector("#reg_password");
    By passwordConfirm = By.cssSelector("#reg_password_confirm");

    private By recaptcha = By.cssSelector("#recaptcha-anchor > .recaptcha-checkbox-border");
    private By submit = By.cssSelector(".popup.popup_signup  button[type = \"submit\"]");


    By firstNameError = By.cssSelector("#reg_firstname + .error");
    By lastNameError = By.cssSelector("#reg_lastname + .error");
    By phoneError = By.cssSelector("#reg_phone + .error");
    By emailError = By.cssSelector("#reg_email + .error");
    By passwordError = By.cssSelector("#reg_password + .error");
    By passwordConfirmError = By.cssSelector("#reg_password_confirm + .error");


    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement signupButton = wait.until(ExpectedConditions.elementToBeClickable(switchToSignUp));
        signupButton.click();
    }

    public void enterFirstName(String fnInput) {
        WebElement fnField = wait.until(ExpectedConditions.visibilityOfElementLocated(firstName));
        fnField.clear();
        fnField.sendKeys(fnInput);
        System.out.println("First name entered: " + fnInput);
    }

    public void enterLastName(String lnInput) {
        WebElement lnField = wait.until(ExpectedConditions.visibilityOfElementLocated(lastName));
        lnField.clear();
        lnField.sendKeys(lnInput);
        System.out.println("Last name entered: " + lnInput);
    }

    public void enterCountry(String countryInput) {
        WebElement countryField = wait.until(ExpectedConditions.elementToBeClickable(country));
        Select countrySelect = new Select(countryField);
        countrySelect.selectByVisibleText(countryInput);
        System.out.println("Country entered: " + countryInput);
    }

    public void enterPhone(String phInput) {
        WebElement phoneField = wait.until(ExpectedConditions.visibilityOfElementLocated(phone));
        phoneField.clear();
        phoneField.sendKeys(phInput);
        System.out.println("Phone entered: " + phInput);
    }

    public void enterEmail(String emailInput) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(email));
        emailField.clear();
        emailField.sendKeys(emailInput);
        System.out.println("Email entered: " + emailInput);
    }

    public void enterPassword(String passInput) {
        WebElement passField = wait.until(ExpectedConditions.elementToBeClickable(password));
        passField.clear();
        passField.sendKeys(passInput);
        System.out.println("Password entered: " + passInput);
    }

    public void enterPasswordConfirm(String pwConfirm) {
        WebElement confirmPass = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordConfirm));
        confirmPass.clear();
        confirmPass.sendKeys(pwConfirm);
        System.out.println("Password confirmed: " + pwConfirm);

    }

    public void clickRecaptcha() {
        WebElement recaptchaCheckbox = wait.until(ExpectedConditions.elementToBeClickable(recaptcha));
        recaptchaCheckbox.click();
    }

    public void submit() {
//        clickRecaptcha();
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(submit));
        submitButton.click();
    }

    public String getFirstNameError() {
        WebElement fnErr = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameError));
        return fnErr.getText();
    }

    public String getLastNameError() {
        WebElement lnErr = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameError));
        return lnErr.getText();
    }

    public String getPhoneError() {
        WebElement phnErr = wait.until(ExpectedConditions.elementToBeClickable(phoneError));
        return phnErr.getText();
    }

    public String getEmailError() {
        WebElement emailErr = wait.until(ExpectedConditions.elementToBeClickable(emailError));
        return emailErr.getText();
    }

    public String getPasswordError() {
        WebElement passwordErr = wait.until(ExpectedConditions.elementToBeClickable(passwordError));
        return passwordErr.getText();
    }

    public String getPasswordConfirmError() {
        WebElement confirmPassErr = wait.until(ExpectedConditions.elementToBeClickable(passwordConfirmError));
        return confirmPassErr.getText();
    }

}
