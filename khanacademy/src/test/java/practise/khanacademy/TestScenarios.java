package practise.khanacademy;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import practise.khanacademy.Capability;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static java.time.Duration.ofSeconds;

public class TestScenarios extends Capability {

	AndroidDriver<AndroidElement> driver;

	@BeforeTest
	public void beforetest() throws IOException, InterruptedException {

		 Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(2000);


	}
	@AfterClass
	public void afterclass() throws IOException, InterruptedException {

		service.stop();

	}

	@Test(enabled = true)
	// Verify signin
	public void tc1() throws InterruptedException, IOException {
		 service = startserver();
		driver = capability(appPackage, appActivity, deviceName, chromedriverExecutable);
		driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);

		Thread.sleep(20000);

		// dismiss the first warning
		driver.findElement(By.xpath("//*[@text='Dismiss']")).click();
		Thread.sleep(5000);
		// dismiss the covid warning
		driver.findElement(By.xpath("//*[@text='Dismiss']")).click();

		Thread.sleep(10000);

		// click on sign in
		driver.findElement(By.xpath("//*[@text='Sign in']")).click();
		Thread.sleep(5000);

		// click on actual sign in
		driver.findElement(By.xpath("//*[@text='Sign in']")).click();
		Thread.sleep(5000);

		// enter username,password
		driver.findElement(MobileBy.AccessibilityId("Enter an e-mail address or username"))
				.sendKeys("marasund@in.ibm.com");
		driver.findElement(MobileBy.AccessibilityId("Password")).sendKeys("Ibm12345$");
		Thread.sleep(5000);

		driver.findElement(MobileBy.AccessibilityId("Sign in")).click();
		Thread.sleep(10000);

		String Actual = (driver.findElements(MobileBy.className("android.widget.TextView"))).get(1).getText();
		System.out.println(Actual);
		String Expected = "Need to add a class?";
		Assert.assertEquals(Actual, Expected);

	}

	@Test(enabled = true)
	// join class -verify error with invalid class code
	public void tc2() throws InterruptedException, IOException {

		driver.findElement(By.xpath("//*[@text='Join class']")).click();
		driver.findElement(MobileBy.AccessibilityId("e.g. ABC123 or teacher@example.com")).sendKeys("Mobile123");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@text='ADD']")).click();
		driver.findElement(By.xpath("//*[@text='ADD']")).click();
		Thread.sleep(5000);
		String actualError = driver.findElement(MobileBy.id("android:id/message")).getText();
		System.out.println(actualError);
		String expectedError = "Please enter a valid class code or email address.";
		Assert.assertEquals(actualError, expectedError);
		driver.findElement(By.xpath("//*[@text='OK']")).click();
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		Thread.sleep(2000);
	}

	@Test(enabled = true)
	// Manage teachers - add and remove teacher mail id
	public void tc3() throws InterruptedException, IOException {
		driver.findElementByAccessibilityId("Settings").click();
		driver.findElement(By.xpath("//*[@text='Manage teachers']")).click();
		driver.findElement(By.xpath("//*[@text='Add teacher']")).click();

		driver.findElement(MobileBy.AccessibilityId("e.g. ABC123 or teacher@example.com")).sendKeys("maha@gmail.com");
		Thread.sleep(3000);
		driver.findElement(By.xpath("//*[@text='ADD']")).click();
		driver.findElement(By.xpath("//*[@text='ADD']")).click();

		String alerttitle = driver.findElement(MobileBy.id("android:id/alertTitle")).getText();
		System.out.println(alerttitle);
		String expectedtitle = "Teacher added";
		Assert.assertEquals(alerttitle, expectedtitle);
		String alertmsg = driver.findElement(MobileBy.id("android:id/message")).getText();
		System.out.println(alertmsg);
		String expectedmsg = "You've added maha@gmail.com to your account - once they add you to a class, you'll see it in the app!";
		Assert.assertEquals(alertmsg, expectedmsg);

		driver.findElement(MobileBy.id("android:id/button1")).click();
		Thread.sleep(2000);
		driver.findElement(MobileBy.AccessibilityId("Delete")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//*[@text='REMOVE']")).click();

		String actualtext = driver.findElements(MobileBy.className("android.widget.TextView")).get(1).getText();
		System.out.println(actualtext);
		String Expectedtext = "No teachers";
		Assert.assertEquals(actualtext, Expectedtext);
		Thread.sleep(2000);
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.pressKey(new KeyEvent(AndroidKey.BACK));

		Thread.sleep(2000);

	}

	@Test(enabled = true)
	// Search for course and assert one of the course available
	public void tc4() throws InterruptedException, IOException {
		driver.findElement(MobileBy.AccessibilityId("Search tab")).click();
		driver.findElement(By.xpath("//*[@text='Math']")).click();
		driver.findElement(By.xpath("//*[@text='Class 10 (Foundation)']")).click();
		driver.findElement(By.xpath("//*[@text='Number systems']")).click();
		Thread.sleep(5000);

		String actualcourse = driver.findElements(MobileBy.className("android.widget.TextView")).get(1).getText();
		System.out.println(actualcourse);
		String Expectedcourse = "The fundamental theorem of arithmetic";
		Assert.assertEquals(actualcourse, Expectedcourse);

		driver.findElement(MobileBy.id("org.khanacademy.android:id/tab_bar_button_home")).click();
		Thread.sleep(2000);

		String Actual = (driver.findElements(MobileBy.className("android.widget.TextView"))).get(1).getText();
		System.out.println(Actual);
		String Expected = "Need to add a class?";
		Assert.assertEquals(Actual, Expected);

	}

	@Test(enabled = true)
	// enable download from book marks

	public void tc5() throws InterruptedException, IOException {
		driver.findElement(MobileBy.AccessibilityId("Bookmarks tab")).click();
		driver.findElement(By.xpath("//*[@text='Download settings']")).click();
	

		Thread.sleep(5000);

		driver.findElements(MobileBy.className("android.widget.Switch")).get(0).click();
		Thread.sleep(3000);
		
		Assert.assertEquals("ON", driver.findElementsByClassName("android.widget.Switch").get(0).getText());
		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		driver.findElement(MobileBy.id("org.khanacademy.android:id/tab_bar_button_home")).click();
		Thread.sleep(2000);
		

	}

	@Test(enabled = false)
	public void tc6() throws InterruptedException, IOException {
		driver.findElementByAccessibilityId("Settings").click();

		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Terms of service\"))").click();

		Thread.sleep(10000);
		// to move from native app to web in hybride apps use context
		Set<String> contextNames = driver.getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); // prints out something like NATIVE_APP \n WEBVIEW_1
		}
		// NATIVE_APP
		// WEBVIEW_chrome
		// WEBVIEW_org.khanacademy.android
		// switch to web app
		driver.context("WEBVIEW_chrome");
		Thread.sleep(20000);
		assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Khan Academy Terms of Service')]")).isDisplayed());

		driver.pressKey(new KeyEvent(AndroidKey.BACK));
		Thread.sleep(5000);
		driver.context("NATIVE_APP");
	}
}
