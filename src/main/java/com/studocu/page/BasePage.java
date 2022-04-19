package com.studocu.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.studocu.base.DriverManager;
import com.studocu.utilities.Reporter;

import io.cucumber.java.Scenario;

public abstract class BasePage {
	protected  WebDriver driver;
	public Reporter report;
	public static Scenario scenario;

	public BasePage(){
		this.driver=DriverManager.getDriver();
		PageFactory.initElements(driver, this);
		report=new Reporter(scenario);
	}	
	
	public abstract void isPageDisplayed();
}
