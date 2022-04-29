package com.studocu.utilities;

import java.time.Duration;

import org.openqa.selenium.By;
//import org.apache.log4j.Logger;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import com.google.common.base.Function;
import com.studocu.base.DriverManager;

public class DriverWait {
	private static WebDriver driver = DriverManager.getDriver();
	//private static Logger logger = Logger.getLogger(DriverWait.class);

	public enum TimeUnit {
		HALFSECOND(0.5), ONESECOND(1), TWOSECONDS(2), FIVESECONDS(5), TENSECONDS(10), TWENTYSECONDS(20),
		THIRTYSECONDS(30), FOURTYECONDS(40), FIFTYECONDS(50), ONEMINUTE(60), TWOMINUTES(120), THREEMINUTES(180),
		FIVEMINUTES(300);

		private double time;

		private TimeUnit(double time) {
			this.time = time;
		}

		public double getTime() {
			return time;
		}
	}

	/**
	 * This method makes web driver to wait more than 2 mints for web element to
	 * display, if web element is not visible or not displayed after 2mints then
	 * method return false otherwise return true
	 * 
	 * @param element - element to display in UAT
	 * @return boolean
	 */
	public static boolean isElementDisplayed(WebElement element) {
		Exception exception = null;
		for (int itrCount = 1; itrCount <= 120; itrCount++) {
			try {
				element.isDisplayed();
				return true;
			} catch (NoSuchElementException noSuch) {
				exception = noSuch;
			} catch (StaleElementReferenceException stale) {
				exception = stale;
			}
			customSleep(TimeUnit.ONESECOND);
		}
		Assert.assertTrue(false, element +" is not displayed for 2 mins, execution is stopped");
		return false;
	}

	/**
	 * Makes web driver to wait for element is enabled, if element is enabled in 2
	 * mints then return false
	 * 
	 * @param element - element to display in UAT
	 * @return boolean
	 */
	public static boolean isElementEnabled(WebElement element) {
		if (isElementDisplayed(element)) {
			int itrCount = 0;
			do {
				if (element.isEnabled()) {
					return true;
				}
				customSleep(TimeUnit.ONESECOND);
				itrCount++;
			} while (itrCount <= 30);
		}
		return false;
	}

	/**
	 * Make thread to sleep for specified time
	 * 
	 * @param waitTime - wait time in long
	 */
	public static void customSleep(TimeUnit waitTime) {
		try {
			Thread.sleep((long) (1000 * (waitTime.getTime())));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static class WebDriverWait {
		static org.openqa.selenium.support.ui.WebDriverWait wait;

		/**
		 * Initialize WebDriverWait
		 * 
		 * @param time - wait time
		 */
		private static org.openqa.selenium.support.ui.WebDriverWait webDriverWait(long... timeInSecs) {
			if (timeInSecs.length > 0) {
				return new org.openqa.selenium.support.ui.WebDriverWait(driver, timeInSecs[0]);
			} else {
				return new org.openqa.selenium.support.ui.WebDriverWait(driver, 30);
			}
		}

		/**
		 * Make web driver to wait till element state to be enabled to click on it
		 * 
		 * @param element - web element
		 * @param time    - wait time
		 */
		public static void waitForElementToBeClickable(WebElement element, long... timeInSecs) {
			wait = webDriverWait(timeInSecs);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}

		/**
		 * Make web driver to wait current URL contains specified text
		 * 
		 * @param text - text to search in URL
		 * @param time - wait time
		 */
		public static void waitForTextInURL(String text, long... timeInSecs) {
			wait = webDriverWait(timeInSecs);
			wait.until(ExpectedConditions.urlContains(text));
		}

		public static void waitForTextInField(WebElement element, String text, long... timeInSecs) {
			wait = webDriverWait(timeInSecs);
			wait.until(ExpectedConditions.textToBePresentInElement(element, text));
		}

		/**
		 * Make web driver to wait till element to be visible on the page
		 * 
		 * @param element - web element
		 * @param time    - wait time
		 */
		public static void waitForElementToBeVisible(WebElement element, long... timeInSecs) {
			wait = webDriverWait(timeInSecs);
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		
		public static void waitForElementToBeVisibleBy(By byElement, long... timeInSecs) {
			wait = webDriverWait(timeInSecs);
			wait.until(ExpectedConditions.visibilityOfElementLocated(byElement));
		}
	}

	public static class FluentWait {
		private static org.openqa.selenium.support.ui.FluentWait<WebDriver> wait;

		private static org.openqa.selenium.support.ui.FluentWait<WebDriver> waitIntervel(long... waitTime) {
			wait = new org.openqa.selenium.support.ui.FluentWait<WebDriver>(driver);
			if (waitTime.length > 0) {
				wait.withTimeout(Duration.ofSeconds(waitTime[0]));
			} else {
				wait.withTimeout(Duration.ofSeconds(60));
			}
			wait.pollingEvery(Duration.ofSeconds(2));
			wait.ignoring(Exception.class);
			return wait;
		}

		/**
		 * This method is used to wait for the element till specified time and to return
		 * status of element
		 * 
		 * @param element  - Provide locator of element.
		 * @param waitTime - Provide timeout in seconds.
		 * @return
		 */
		public static boolean waitForElementToDisplay(WebElement element, long... waitTime) {
			wait = waitIntervel(waitTime);
			Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver input) {
					try {
						return element.isDisplayed();
					} catch (NoSuchElementException noSuch) {
						return false;
					} catch (ElementNotVisibleException notVisible) {
						return false;
					}
				}
			};
			return wait.until(function);
		}

		/**
		 * This method is used to wait for the element to be clicked till specified time
		 * and till clicked element is invisible
		 * 
		 * @param element  - Provide locator of element.
		 * @param waitTime - Provide timeout in seconds.
		 * 
		 * @return
		 */
		public static void waitForElementToClick(WebElement element, long... waitTime) {
			wait = waitIntervel(waitTime);
			Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
				@Override
				public Boolean apply(WebDriver input) {
					try {
						element.click();
						return true;
					} catch (NoSuchElementException soSuch) {
						return false;
					} catch (WebDriverException webDriverError) {
						return false;
					}
				}
			};
			wait.until(function);
		}
	}

}
