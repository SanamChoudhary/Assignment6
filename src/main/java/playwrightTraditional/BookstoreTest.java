package playwrightTraditional;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

//run mvn clean before testing
public class BookstoreTest {
    @Test
    public void testBookstorePurchasePath() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless("CI".equals(System.getenv("GITHUB_ACTIONS"))));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions().setRecordVideoDir(Paths.get("videos/")).setRecordVideoSize(1280, 720));
            //Page page = context.newPage();
            Page page1 = context.newPage();
            page1.navigate("https://depaul.bncollege.com/");
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");
            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
            page1.locator(".facet__list.js-facet-list.js-facet-top-values > li:nth-child(3) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
            page1.locator("#facet-Color > .facet__values > .facet__list > li > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
            page1.locator("#facet-price > .facet__values > .facet__list > li:nth-child(2) > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").click();
            page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();


            //First Assertions
            assertThat(page1.getByLabel("main").getByRole(AriaRole.HEADING).first()).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
            assertThat(page1.getByLabel("main")).containsText("sku 668972707");
            assertThat(page1.getByLabel("main")).containsText("$164.98");
            assertThat(page1.getByLabel("main")).containsText("Adaptive noise cancelling allows awareness of environment when gaming on the go. Light weight, durable, water resist. USB-C dongle for low latency connection < than 30ms.");

            System.out.println("Earbuds Test Passed");

            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();

            //Second Assertions
            assertThat(page1.locator("#headerDesktopView")).containsText("Cart 1 items");
            assertThat(page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items"))).isVisible();

            System.out.println("Add Earbuds to Cart Test Passed");

            //Third Assertions
            page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
            assertThat(page1.getByLabel("main")).containsText("Your Shopping Cart");
            assertThat(page1.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Your Shopping Cart(1 Item)"))).isVisible();

            System.out.println("Inside Cart-Page Test Passed");

            //Fourth Assertions
            assertThat(page1.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
            assertThat(page1.getByLabel("main")).containsText("$164.98");
            assertThat(page1.getByLabel("main")).containsText("(1 Item)");

            System.out.println("Cart Details Test Passed");

            page1.locator(".checkmark").first().click();

            //Fifth Assertions
            assertThat(page1.getByLabel("main")).containsText("Subtotal $164.98");
            assertThat(page1.getByLabel("main")).containsText("Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00");
            assertThat(page1.locator(".js-cart-totals > div:nth-child(2)")).isVisible();
            assertThat(page1.getByLabel("main")).containsText("Taxes TBD");

            System.out.println("Cart Price Details Test Passed");

            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).click();
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Enter Promo Code")).fill("TEST");
            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply Promo Code")).click();

            //Sixth Assertion
            assertThat(page1.locator("#js-voucher-result")).containsText("The coupon code entered is not valid.");

            System.out.println("Wrong Promo Code Test Passed");

            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();

            //Seventh Assertion
            //assertThat(page1.getByLabel("main")).containsText("Create Account");
            assertThat(page1.getByLabel("main")).containsText("Create Account Easy access to your purchase and rental history Faster checkout on future orders Get special offers and promotions throughout the yearStudents: Use .edu email for a personalized experience Create Account or Proceed As Guest");

            System.out.println("Create Account Test Passed");

            page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();

            //Assertion 8
            assertThat(page1.getByLabel("main")).containsText("Contact Information");

            System.out.println("Contact Information Test Passed");
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).click();
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("Sanam");
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).click();
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Choudhary");
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).click();
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("sanamc541@gmail.com");
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).click();
            page1.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("8474701503");

            //Assertion 9
            assertThat(page1.getByLabel("main")).containsText("Order Subtotal $164.98");
            assertThat(page1.getByLabel("main")).containsText("Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00");
            assertThat(page1.locator(".bned-order-summary-container.bned-step-summary-inner-container > .bned-order-summary-order-totals > .subtotals > div:nth-child(2)")).isVisible();
            assertThat(page1.getByLabel("main")).containsText("Tax TBD");
            assertThat(page1.getByLabel("main")).containsText("Total $167.98 167.98 $");

            System.out.println("Payment Information Test Passed");

            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

            //Assertion 10
            assertThat(page1.getByLabel("main")).containsText("Full Name Sanam Choudhary Email Address sanamc541@gmail.com Phone Number +18474701503");
            assertThat(page1.locator("#bnedPickupPersonForm")).containsText("DePaul University Loop Campus & SAIC");
            assertThat(page1.locator(".sub-check").first()).isVisible();
            assertThat(page1.locator("#bnedPickupPersonForm")).containsText("I'll pick them up");
            assertThat(page1.getByLabel("main")).containsText("Order Subtotal $164.98");
            assertThat(page1.locator(".bned-order-summary-container.bned-step-summary-inner-container > .bned-order-summary-order-totals > .subtotals > div:nth-child(2)")).isVisible();
            assertThat(page1.getByLabel("main")).containsText("Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00");
            assertThat(page1.getByLabel("main")).containsText("Tax TBD");
            assertThat(page1.getByLabel("main")).containsText("Total $167.98 167.98 $");
            assertThat(page1.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
            assertThat(page1.getByText("$164.98").nth(3)).isVisible();

            System.out.println("Checkout Screen Test Passed");

            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();

            //Assertion 11
            assertThat(page1.getByLabel("main")).containsText("Order Subtotal $164.98");
            assertThat(page1.getByLabel("main")).containsText("Handling To support the bookstore's ability to provide a best-in-class online and campus bookstore experience, and to offset the rising costs of goods and services, an online handling fee of $3.00 per transaction is charged. This fee offsets additional expenses including fulfillment, distribution, operational optimization, and personalized service. No minimum purchase required. $3.00");
            assertThat(page1.getByLabel("main")).containsText("Tax $17.22");
            assertThat(page1.getByLabel("main")).containsText("Total $185.20 185.2 $");
            assertThat(page1.locator(".bned-order-summary-container.bned-step-summary-inner-container > .bned-order-summary-order-totals > .subtotals > div:nth-child(2)")).isVisible();
            assertThat(page1.getByLabel("main")).containsText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black");
            assertThat(page1.getByText("$164.98").nth(3)).isVisible();

            System.out.println("Credit Card Page Test Passed");

            page1.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();
            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();

            //Assertion 12
            assertThat(page1.getByRole(AriaRole.ALERT)).containsText("Product has been removed from your cart.");

            System.out.println("Deleted Cart Test Passed");
        }
    }
}
