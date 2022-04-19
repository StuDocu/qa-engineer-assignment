package com.studocu.runner;


import com.studocu.base.CucumberFeatureManager;

import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		dryRun = false, 
		glue = { "com.studocu.stepdefinitions" },
		features = "src/test/resources/features",
		tags= {"@test"},
		plugin = {
				"json:target/cucumber-reports/cucumber-html-report.json", 
				"usage:target/cucumber-reports/cucumber-usage.json",
				"rerun:target/cucumber-reports/failedscenarios.txt" })

public class TestRunner extends CucumberFeatureManager{

}


