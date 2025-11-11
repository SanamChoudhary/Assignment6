package playwrightLLM;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.Test;

import java.nio.file.Paths;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class BookstoreTestLLM {
    @Test
    public void testEarbudsSearchFilterAddToCartAndRemove() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless("CI".equals(System.getenv("GITHUB_ACTIONS"))));
            BrowserContext context = browser.newContext(new Browser.NewContextOptions()
                    .setRecordVideoDir(Paths.get("videos/"))
                    .setRecordVideoSize(1280, 720));
            Page page = context.newPage();
            
            // Navigate to the bookstore
            page.navigate("https://depaul.bncollege.com/");
            System.out.println("Navigated to bookstore");
            
            // Search for earbuds
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).click();
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).fill("earbuds");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search")).press("Enter");
            System.out.println("Searched for earbuds");
            
            // Filter by color
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
            page.locator("#facet-Color > .facet__values > .facet__list > li > form > label > .facet__list__label > .facet__list__mark > .facet-unchecked > svg").first().click();
            System.out.println("Filtered by color");
            
            // Click on the JBL Quantum earbuds product
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
            System.out.println("Clicked on JBL Quantum True Wireless earbuds");
            
            // Add to cart
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Add to cart")).click();
            System.out.println("Added to cart");
            
            // Verify cart shows 1 item
            assertThat(page.locator("#headerDesktopView")).containsText("Cart 1 items");
            System.out.println("✓ Cart shows 1 item");
            
            // Navigate to cart
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
            System.out.println("Navigated to cart");
            
            // Click checkout to proceed to payment
            page.locator(".checkmark").first().click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Proceed To Checkout")).first().click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();
            
            // Fill in contact information
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("First Name (required)")).fill("Sanam");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Last Name (required)")).fill("Choudhary");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Email address (required)")).fill("sanamc541@gmail.com");
            page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Phone Number (required)")).fill("8474701503");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
            
            // Verify pricing details: subtotal, tax, and total
            assertThat(page.getByLabel("main")).containsText("Order Subtotal $164.98");
            System.out.println("✓ Subtotal is $164.98");
            
            assertThat(page.getByLabel("main")).containsText("Tax $17.22");
            System.out.println("✓ Tax is $17.22");
            
            assertThat(page.getByLabel("main")).containsText("Total $185.20");
            System.out.println("✓ Total price after taxes is $185.20");
            
            // Go back to cart to remove the item
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Back to cart")).click();
            System.out.println("Went back to cart");
            
            // Remove the item from cart
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Remove product JBL Quantum")).click();
            System.out.println("Clicked remove button");
            
            // Verify the item was removed
            assertThat(page.getByRole(AriaRole.ALERT)).containsText("Product has been removed from your cart.");
            System.out.println("✓ Item was successfully removed from cart");
            
            System.out.println("\n✓ All tests passed!");
            
            context.close();
            browser.close();
        }
    }
}
