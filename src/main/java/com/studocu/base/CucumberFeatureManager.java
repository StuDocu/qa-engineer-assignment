package com.studocu.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;


/**
 * Base for cucumber runner
 * Picks up all options which are mentioned in cucumber runner file and run scenarios based on tag notations given in runner file
 * Customize cucumber report after completion of execution
 *
 */
public class CucumberFeatureManager extends DriverManager{
	private TestNGCucumberRunner testNGCucumberRunner;

	@BeforeClass(alwaysRun=true)
	public void setUpClass() throws ConfigurationException, IOException {
		testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper   featureWrapper) throws Throwable {
		testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
	}

	@DataProvider
	public Object[][] scenarios() {
		if (testNGCucumberRunner == null) {
            return new Object[0][0];
        }
        return testNGCucumberRunner.provideScenarios();
	}

	@AfterClass
	public void tearDownClass() {
		testNGCucumberRunner.finish();
        File reportOutputDirectory = new File("target/cucumber-reports/");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("target/cucumber-reports/cucumber-html-report.json");

        String projectName = "QA Assessment";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        
        configuration.addClassifications("Project URL", propFile.getProperty(currentTestEnv+"URL"));
        configuration.addClassifications("Browser", propFile.getProperty("browser"));
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.setTrendsStatsFile(new File("target/test-classes/featurebuildtrends.json"));
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
	}
}
