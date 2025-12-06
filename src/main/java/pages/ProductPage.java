package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductPage {
    WebDriver driver;
    private WebDriverWait wait;

    By item = By.cssSelector(".item");
    By quantitySelector = By.cssSelector("span[data-qty_plus]");
    By addToCartSelector = By.cssSelector(".button_default.button_add");
    By wasAddedBannerSelector = By.cssSelector(".on_added_to_cart.show");
    //By miniCartCounterSelector = By.cssSelector(".header-mobile .mini_cart__count");
    By miniCartLinkSelector = By.cssSelector(".header-desktop .your-cart.mini-cart__trigger" + ", .header-mobile .your-cart.mini-cart__trigger");
    //By homePageLinkSelector = By.cssSelector("a[href='https://www.israelcart.com/']");


    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void addProductToCart(int quantity) {
//        int initialCount = 0;
//        try {
//            WebElement counter = wait.until(ExpectedConditions.visibilityOfElementLocated(miniCartCounterSelector));
//            initialCount = Integer.parseInt(counter.getText().trim());
//        } catch (Exception ignored) {
//            initialCount = 0;
//        }
//        System.out.println("Initial cart count: " + initialCount);

//        By product = By.cssSelector(productCssSelector);


        List<WebElement> products = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(item)
        );

        WebElement productElement = products.stream()
                .filter(p -> p.findElements(addToCartSelector).size() > 0)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No item with add-to-cart button found"));

//        WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(item));

        System.out.println("productElement: " + productElement.getText());

        Actions actions = new Actions(driver);
        actions.moveToElement(productElement).perform();
        System.out.println("mouse hover productElement: " + productElement.getText());

        if (quantity > 1) {
            WebElement quantityButton = productElement.findElement(quantitySelector);
            wait.until(ExpectedConditions.elementToBeClickable(quantityButton));
            System.out.println("quantityButton: " + quantityButton.getText());

            for (int i = 0; i < quantity - 1; i++) {
                quantityButton.click();
                System.out.println("Clicked quantity " + (i + 2));
            }
        }


        WebElement addToCartButton = productElement.findElement(addToCartSelector);
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        System.out.println("addToCartButton: " + addToCartButton.getText());
        addToCartButton.click();


        WebElement wasAddedBanner = wait.until(ExpectedConditions.visibilityOfElementLocated(wasAddedBannerSelector));
        System.out.println("wasAddedBanner: " + wasAddedBanner.getText());

//        int expectedCount = initialCount + 1;
//        wait.until(driver -> {
//            try {
//                System.out.println(expectedCount);
//                WebElement counter = wait.until(ExpectedConditions.visibilityOfElementLocated(miniCartCounterSelector));
//                int value = Integer.parseInt(counter.getText().trim());
//                System.out.println("value of counter: " + value);
//                return value == expectedCount;
//            } catch (Exception e) {
//                return false; // אם אין קאונטר עדיין
//            }
//        });
//
//        System.out.println("Cart counter updated to: " + expectedCount);
    }

    public CartPage goToCart() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(wasAddedBannerSelector));
        WebElement cartLink = wait.until(ExpectedConditions.elementToBeClickable(miniCartLinkSelector));
        System.out.println("cartLink: " + cartLink.getText());
        cartLink.click();
        return new CartPage(driver);
    }
}

