import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class coursework {

    WebDriver driver;
    @BeforeClass(alwaysRun = true)
    public void setup() {
        WebDriverManager.edgedriver().driverVersion("95.0.1020.40").setup();
        driver = new EdgeDriver();

    }
    public void equal(String givenurl){
        String currenturl = driver.getCurrentUrl();
        Assert.assertEquals(givenurl, currenturl);
        System.out.println("Given URL = Current URL");
    }
    public void Display(String type, String xpath, String Given_Name){
        WebElement Name = driver.findElement(By.xpath(xpath));
        String Current_Name = Name.getText();
        Assert.assertEquals(Current_Name,Given_Name);
        System.out.println(type + " Displayed");
    }

    public void button_xpath(String xpaths, String Buttonname) {
        WebElement button1 = driver.findElement(By.xpath(xpaths));
        button1.click();
        System.out.println(Buttonname);
    }

    public void button_namepath(String namepath, String Buttonname) {
        WebElement button1 = driver.findElement(By.name(namepath));
        button1.click();
        System.out.println(Buttonname);
    }


    public String gettext(String xpath) {
        return driver.findElement(By.xpath(xpath)).getText();
    }


    @Test
    public void test() {

        String givenurl = "https://automationpractice.com/index.php";
        driver.get(givenurl);
        button_xpath("//*[@class=\"login\"][@rel=\"nofollow\"]","sign in button clicked");

        //1.Go to http://automationpractice.com/index.php and use the previously created
        //username/ password to log in to the system
        WebElement email = driver.findElement(By.xpath("//*[@class=\"is_required validate account_input form-control\"][@name=\"email\"]"));
        email.sendKeys("pasindugeevinda2@gmail.com");

        WebElement password = driver.findElement(By.xpath("//input[@data-validate=\"isPasswd\"][@name=\"passwd\"]"));
        password.sendKeys("pasindu123456");

        button_xpath("//*[@class=\"icon-lock left\"]", "signed in");

        //2.Verify the URL is http://automationpractice.com/index.php
        String givenurl2 = "https://automationpractice.com/index.php";
        driver.get(givenurl2);
        equal(givenurl);

        //3.Click on any product of your choice
        button_xpath("//*[@class=\"replace-2x img-responsive\"][@title=\"Blouse\"][@itemprop=\"image\"]", "photo clicked");

        //4.Verify the product name and price is similar to the product you selected
        Display("Product name", "//*[@itemprop=\"name\"][text()=\"Blouse\"]", "Blouse");

        Display("Product price","//*[@itemprop=\"price\"][text()=\"$27.00\"]", "$27.00");

        //5.Click on add to cart
        button_xpath("//*[text()=\"Add to cart\"]","added to cart");

        //6. Click on the Continue Shopping button
        WebDriverWait wait = new WebDriverWait(driver,20);
        WebElement continue_shop = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"continue btn btn-default button exclusive-medium\"]")));
        continue_shop.click();

        //7.Add another product to the cart
        button_xpath("//*[@class=\"sf-with-ul\"][@title=\"Women\"]", "Women to shopping");
        button_xpath("//*[@title=\"Add to cart\"][@data-id-product=\"1\"]", "second one add to cart");

        //8.Verify if the value of the Total Products amount equals the sum of the product prices
        //that are added to the cart
        WebElement Proceed_to_checkout = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"btn btn-default button button-medium\"][@rel=\"nofollow\"]")));
        Proceed_to_checkout.click();

        double product_1 = Double.parseDouble(driver.findElement(By.id("total_product_price_2_7_594386")).getText().replace("$", ""));
        double product_2 = Double.parseDouble(driver.findElement(By.id("total_product_price_1_1_594386")).getText().replace("$", ""));
        double Total = Math.round((product_1 + product_2)*100.0)/100.0;
        double total_prize = Double.parseDouble(driver.findElement(By.id("total_product")).getText().replace("$", ""));
        Assert.assertEquals(Total,total_prize);
        System.out.println("Products amount equals the sum of the product prices that are added to the cart");

        //9.Click on the trash can icon next to the products and remove one from the cart
        button_xpath("//*[@class=\"cart_quantity_delete\"][@id=\"2_7_0_594386\"]", "Trashed");

        //10.Verify if the total is updated after the removal of products
        //"given name" set as must be desplay item
        WebElement total = driver.findElement(By.id("product_price_1_1_594386"));
        String total1 = total.getText();
        String unit_priz = driver.findElement(By.id("total_product")).getText();
        boolean b = total1.equals(unit_priz);
        System.out.println(b);

        //11.Click on proceed to checkout
        button_xpath("//*[@title=\"Proceed to checkout\"][@class=\"button btn btn-default standard-checkout button-medium\"]", "Proceed to checkout");

        //12.Verify if your delivery address details are as same as the values you provided when creating the account
        String addressline_1 = gettext("//*[text()=\"Diwelwatta, ,Wanchawela,\"][@class=\"address_address1 address_address2\"]");
        String addressline_2 = gettext("//*[text()=\"galle, Hawaii 80120\"]").replaceAll("80120", ",");
        String addressline_3 = gettext("//*[text()=\"United States\"]");
        String addresscurrent = addressline_1 + addressline_2 + addressline_3 ;
        String addressreal = "Diwelwatta, ,Wanchawela,galle, Hawaii ,United States";
        Assert.assertEquals(addressreal, addresscurrent);
        System.out.println("delivery address details are as same as the values you provided when creating the account");

        button_xpath("//*[@type=\"submit\"][@name=\"processAddress\"]", "Proceed to checkout");

        //13. Click on the I agree checkbox
        button_namepath("cgv", "agree checkbox");

        //14. Click on Proceed to Checkout
        button_namepath("processCarrier", "Proceed to Checkout");

        //16.Click on Pay By Bank Wire
        button_xpath("//*[@class=\"bankwire\"][@title=\"Pay by bank wire\"]", "");

        //15. Verify if the URL is equaled to
        equal("https://automationpractice.com/index.php?fc=module&module=bankwire&controller=payment");

        //17. Finally, click on I confirm my order
        button_xpath("//*[@class=\"button btn btn-default button-medium\"]", "confirm my order");

        //18.Verify if the below message is displayed to ensure that the payment is a success
        //Your order on My Store is complete.
        Display("Your order on My Store is complete.", "//*[text()=\"Your order on My Store is complete.\"]", "Your order on My Store is complete.");

    }
}
