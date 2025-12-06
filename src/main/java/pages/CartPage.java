package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage {
    WebDriver driver;
    private WebDriverWait wait;

    private WebElement cart;

    private By cartWrapper = By.cssSelector(
            ".header-mobile .mini-cart__wrapper" + " , .header-desktop .mini-cart__wrapper"
    );
    private By cartItemsContainer = By.cssSelector(".mini-cart__items.thin-scrollbar");
    private By singleCartItem = By.cssSelector(".cart-item");
    private By itemName = By.cssSelector(".cart-item__title > a");
    private By itemQuantity = By.cssSelector("input[data-qty_input]");
    private By itemPrice = By.cssSelector(".cart-item__price .sale_price .price_amount, " +
            ".cart-item__price .regular_price .price_amount");
    private By totalPrice = By.cssSelector(".subtotal__price__main.green .price_amount");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        cart = wait.until(ExpectedConditions.visibilityOfElementLocated(cartWrapper));

    }


    public List<CartItem> getCartItems() {
        WebElement container = cart.findElement(cartItemsContainer);
        List<WebElement> items = container.findElements(singleCartItem);

        System.out.println("items: " + items.size());
        List<CartItem> itemsList = new ArrayList<>();

        for (WebElement item : items) {
            String name = item.findElement(itemName).getText().trim();

            String qtyText = item.findElement(itemQuantity).getAttribute("value").trim();
            int qty = Integer.parseInt(qtyText);

            String priceText = item.findElement(itemPrice).getText().trim();
            double price = Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));

            itemsList.add(new CartItem(name, qty, price));
            System.out.println("item: " + name);
        }

        return itemsList;
    }

    public Double getTotalPrice() {
        WebElement totalCartPrice = cart.findElement(totalPrice);
        System.out.println("total: " + totalCartPrice.getText());
        String text = totalCartPrice.getText().replaceAll("[^0-9.]", "");
        return Double.parseDouble(text);
    }
}