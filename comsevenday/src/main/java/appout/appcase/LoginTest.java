package appout.appcase;

import appout.base.DriverBase;
import appout.utils.LogUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author liwen
 * @Title: LoginTest
 * @Description: 用例区域
 * @date 2019/11/21 / 10:06
 */
public class LoginTest {


    static AndroidDriver<AndroidElement> driver;

    /**
     * 初始化运行类
     *
     * @param udid
     * @param port
     * @throws Exception
     */
    @BeforeClass
    @Parameters({"udid", "port"})
    public void BeforeClass(String udid, String port) {
        Reporter.log("步骤1：启动appium与应用", true);
        LogUtil.info("---这是设备ID号-->" + udid);
        LogUtil.info("--这是运行端口--->" + port);
        //通过路径获取包名与APP_ACTIVITY
        String apk = "C:\\Users\\liwen\\Downloads\\pinduoduov4.76.0_downcc.com.apk";
        driver = DriverBase.initDriver(port, udid, apk, true);
        driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    @Test
    public void T001() {
        LogUtil.info("启动");
        driver.findElement(By.id("com.xunmeng.pinduoduo:id/bo0")).click();

    }

}
