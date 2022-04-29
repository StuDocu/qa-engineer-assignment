package com.studocu.stepdefinitions;

import java.util.Properties;

import com.studocu.page.BasePage;
import com.studocu.utilities.Reporter;
import com.studocu.utilities.SoftAssertion;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks {
	Properties prop = new Properties();
	
	/**
	 * Called before every scenario
	 * @param scenario - scenario instance
	 */
	@Before
	public void beforeScenario(Scenario scenario) {
		BasePage.scenario = scenario;
		new SoftAssertion();
		Reporter.logger("Starting Scenario: " + scenario.getName() + "\n");
	}
	@BeforeStep
	public void beforeStep(Scenario scenario) {
		BasePage.scenario = scenario;
	}


	/**
	 * Called after every step, take screenshot after step
	 * 
	 * @param scenario - scenario instance
	 */
	@AfterStep
	public void afterStep(Scenario scenario) {
		Reporter.logger("Scenario: " + scenario.getName() + " has status " + scenario.getStatus());
	}

	/**
	 * Called after every scenario, 
	 * 
	 * @param scenario - scenario instance
	 */
	@After
	public void afterScenario(Scenario scenario) {
		Reporter.logger(
				"Closing all soft assertions, verify above logs for 'SOFT ASSERTION FAIL>>>>>' if this step fails");
	}

	
}
