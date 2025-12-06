package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage {

    WebDriver driver;
    private WebDriverWait wait;

    // Page Elements
    private By jewelryPageLink = By.cssSelector("li.has_submenu > a[href='https://www.israelcart.com/product-category/jewelry/']");
    private By bookPageLink = By.cssSelector("li.has_submenu > a[href='https://www.israelcart.com/product-category/books/']");
    private By cosmeticsPageLink = By.cssSelector("li.has_submenu > a[href='https://www.israelcart.com/product-category/cosmetics/']");

    private By loginLink = By.cssSelector(".account.out");
    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Action Methods
    public  ProductPage goToJewelryPage(){
        WebElement jewelryCategoryLink = wait.until(ExpectedConditions.elementToBeClickable(jewelryPageLink));
        jewelryCategoryLink.click();
        return new ProductPage(driver);
    }
    public  ProductPage goToBookPage(){
        WebElement bookCategoryLink = wait.until(ExpectedConditions.elementToBeClickable(bookPageLink));
        bookCategoryLink.click();
        return new ProductPage(driver);
    }

    public  ProductPage goToCosmeticsPage(){
        WebElement cosmeticsCategoryLink = wait.until(ExpectedConditions.elementToBeClickable(cosmeticsPageLink));
        cosmeticsCategoryLink.click();
        return new ProductPage(driver);
    }
    public  RegistrationPage goToLoginPage(){
        WebElement loginPageLink = wait.until(ExpectedConditions.elementToBeClickable(loginLink));
        loginPageLink.click();
        return new RegistrationPage(driver);
    }
}
