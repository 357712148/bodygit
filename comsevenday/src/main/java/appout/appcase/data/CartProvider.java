package appout.appcase.data;

import appout.utils.GetByLocator;
import appout.utils.WaitUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.WebElement;

/**
 * @author liwen
 * @Title: CartProvider
 * @Description: this is
 * @date 2019/11/23 / 10:57
 */
public class CartProvider {
    private GetByLocator getByLocator;
    private WebElement element = null;

    /**
     * 构造函数
     */
    public CartProvider() {
        this.getByLocator = new GetByLocator("loginElement.properties");
    }

    /**
     * 点击购物车
     */
    public void clickCart(AndroidDriver<AndroidElement> driver) {
        WaitUtil.waitWebElement(driver, getByLocator.getLocatorApp("clickCart"), "点击购物车");
        element = driver.findElement(getByLocator.getLocatorApp("clickCart"));
        element.click();
    }
}
