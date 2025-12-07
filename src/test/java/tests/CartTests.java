package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CartItem;
import pages.CartPage;
import pages.HomePage;
import pages.ProductPage;
import utils.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CartTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductPage jewelryPage;
    private ProductPage bookPage;
    private ProductPage cosmeticsPage;
    private CartPage cartPage;

    @BeforeClass
    public void setUpClass() {
        System.out.println("=== Starting Cart Tests ===");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.israelcart.com");
        homePage = new HomePage(driver);

    }

    @BeforeMethod
    public void setup() {
        System.out.println("=== Starting Cart Test ===");
    }

    @Test(priority = 1, description = "add a jewelery item with quantity 2 to the cart")
    public void addJewelryToCart() {
        jewelryPage = homePage.goToJewelryPage();
        String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        ScreenshotUtils.takeScreenshot(driver, "beforeChange_" + time + ".png");
        jewelryPage.addProductToCart( 2);
        ScreenshotUtils.takeScreenshot(driver, "afterChange"+time+".png");

    }
//"div[data-product_id='49559']",
    @Test(priority = 2, description = " add a book to the cart")
    public void addBookToCart() {
        bookPage = homePage.goToBookPage();
        bookPage.addProductToCart( 1);

    }
//"div[data-product_id='25640']",

    @Test(priority = 3, description = " add a cosmetics item to the cart")
    public void addCosmeticsToCart() {
        cosmeticsPage = homePage.goToCosmeticsPage();
        cosmeticsPage.addProductToCart(1);

    }
    //"div[data-product_id='17837']",

    @Test(priority = 4, description = "Go to cart after adding items")
    public void goToCartAfterAddingItems() {
        cartPage = cosmeticsPage.goToCart();
    }

    @Test(priority = 5, description = "Validate cart data and export to Excel")
    public void validateCartContent() {

        List<CartItem> items = cartPage.getCartItems();

        boolean sizeCorrect = items.size() == 3;
        boolean foundTwoQuantity = items.stream().anyMatch(i -> i.getQuantity() == 2);

        double totalCalculated = 0.0;
        for (CartItem item : items) {
            totalCalculated += item.getPrice();
        }

        double cartTotalDisplayed = cartPage.getTotalPrice();

        boolean totalCorrect = Math.abs(cartTotalDisplayed - totalCalculated) < 0.01;


        Assert.assertTrue(sizeCorrect, "Cart should contain 3 items");
        Assert.assertTrue(foundTwoQuantity, "One item must have quantity 2");
        Assert.assertTrue(totalCorrect, "Cart total does not match sum of items");
        System.out.println("Total calculated: " + totalCalculated);
        System.out.println("Total displayed: " + cartTotalDisplayed);

        String summary = "Items count correct: " + (sizeCorrect ? "PASS" : "FAIL") +
                ", Quantity correct: " + (foundTwoQuantity ? "PASS" : "FAIL") +
                ", Total correct: " + (totalCorrect ? "PASS" : "FAIL");

        ExcelUtils.writeCartToExcel(items, summary);

        System.out.println("Excel report generated successfully!");
    }


    @AfterMethod
    public void afterMethod() {
        System.out.println("=== Complete Cart Test ===");
    }

    @AfterClass
    public void tearDownClass() {

        if(driver != null) {
            driver.quit();
        }
        System.out.println("=== Cart Tests Completed ===");
    }
}
