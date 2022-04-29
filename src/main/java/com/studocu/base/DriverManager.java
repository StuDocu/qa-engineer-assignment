package com.studocu.base;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.studocu.support.PropertiesFile;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Manager browser launch and kill activities
 * Load property file and launch browser based on programmer choice given properties file
 * Kills browser for completion of every class execution
 *
 */
public class DriverManager {
	private static WebDriver driver;
	public static String browser;

	public static String baseDir = System.getProperty("user.dir");
	
	public static PropertiesFile propFile;
	public static String path=baseDir + "/src/main/java/com/studocu/";
	public static String currentTestEnv = "";
	@BeforeTest
    public void beforeTest() throws ConfigurationException {
        if(System.getProperty("environment") != null) {
            PropertiesFile.setProperty(path + "/config/config.properties",System.getProperty("environment"));
        }
        propFile=new PropertiesFile(path + "/config/config.properties");
    }

	
	/**
	* This function launches browser based on browser name given in config properties file.
	 * @throws IOException 
	*/
	@Parameters("TestEnv")
	@BeforeClass
	public void launchBrowser(@Optional("") String TestEnv) throws ConfigurationException, InterruptedException, IOException {
		browser = propFile.getProperty("browser");
		String currentDir = System.getProperty("user.dir");
		 ProcessBuilder processBuilder = new ProcessBuilder();
		    
		 processBuilder.directory(new File(currentDir));
		     
		 processBuilder.command("cmd.exe", "/c", "python -m http.server 8000");
		 System.out.println(processBuilder.toString());
		 
		 processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
		  final Process process = processBuilder.start();
		    
		switch (browser.toLowerCase()) {
		case "ie":
			WebDriverManager.iedriver().setup();
			driver = new InternetExplorerDriver();
			break;
		case "chrome":
			WebDriverManager.chromedriver().setup();
			String fileDownloadPath = System.getProperty("user.dir") + "/"+"externaldownload";
			File file= new File(fileDownloadPath);
			Map<String, Object> prefsMap = new HashMap<String, Object>();
			prefsMap.put("profile.default_content_settings.popups", 0);
			prefsMap.put("download.default_directory", file.getAbsoluteFile().toString());
			prefsMap.put("download.prompt_for_download", false);
			prefsMap.put("download.extensions_to_open", "xml");
			prefsMap.put("safebrowsing.enabled", true);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefsMap);
			options.addArguments("start-maximized"); // open Browser in maximized mode
			options.addArguments("disable-infobars"); // disabling infobars
			options.addArguments("--disable-extensions"); // disabling extensions
			options.addArguments("--disable-gpu"); // applicable to windows os only
			options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
			options.addArguments("--no-sandbox"); // Bypass OS security model
		    options.addArguments("ignore-certificate-errors");
//			options.addArguments("--headless");
		    options.setAcceptInsecureCerts(true);
		    options.setCapability (CapabilityType.ACCEPT_SSL_CERTS, true);
		    options.addArguments("enable-automation");
		    options.addArguments("--window-size=1920,1080");
		    options.addArguments("--no-sandbox");
		    options.addArguments("--dns-prefetch-disable");
		    options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		    
		    options.addArguments("--safebrowsing-disable-download-protection");
		    options.addArguments("safebrowsing-disable-extension-blacklist");

			driver = new ChromeDriver(options);
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			Assert.assertTrue(false, "Invalid Choice, plese give valid browser name");
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		PropertiesConfiguration conf = new PropertiesConfiguration(path + "/config/config.properties");
		if(TestEnv.length()>0) {
			currentTestEnv = TestEnv+"_";
			driver.get(propFile.getProperty(TestEnv+"_URL"));
			conf.save();
		}
		else{
			driver.get(propFile.getProperty(propFile.getProperty("env")+"URL"));
			currentTestEnv = (String) conf.getProperty("env");
		}

		System.out.println( " Current Test Environment is "+currentTestEnv);
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	/**
	* This function closes all browser drivers.
	 * @throws IOException 
	*/
	@AfterClass
	public static void closeDriver() throws IOException {
		if (driver != null) {
			try {
				driver.quit();
			} catch (NoSuchMethodError nsme) {
				nsme.printStackTrace();
			} catch (NoSuchSessionException nsse) {
				nsse.printStackTrace();
			} catch (SessionNotCreatedException snce) {
				snce.printStackTrace();
			}
			driver = null;
		}
	}
	
	/**
	* Gets driver instance
	* @return webdriver instance
	*/
	public static WebDriver getDriver() {
		return driver;
	}
}
