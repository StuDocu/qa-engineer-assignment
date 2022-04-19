package com.studocu.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.studocu.utilities.DriverWait;


public class GlobalMenu extends BasePage {
	@Override
	public void isPageDisplayed() {
		DriverWait.WebDriverWait.waitForElementToBeClickable(header, 120);
		Assert.assertTrue(DriverWait.isElementDisplayed(header),
				"Either user is unable to login application or home page is not displayed");
	}

	@FindBy(xpath = "//*[contains(text(),'The awesome Q/A tool')]")
	WebElement header;

}