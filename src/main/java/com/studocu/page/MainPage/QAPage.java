package com.studocu.page.MainPage;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.studocu.base.DriverManager;
import com.studocu.page.BasePage;
import com.studocu.utilities.DriverWait;
import com.studocu.utilities.Reporter;
import com.studocu.utilities.SoftAssertion;

public class QAPage extends BasePage {

	JavascriptExecutor js = (JavascriptExecutor) driver;
	public static int count = 0;

	public void isPageDisplayed() {
		assertTrue(DriverWait.isElementDisplayed(header), "Page is not loaded successfully");
	}
	
	@FindBy(xpath = "//*[contains(text(),'The awesome Q/A tool')]")
	WebElement header;

	@FindBy(xpath = "//*[contains(text(),'Sort questions')]")
	WebElement sortQuestionsBtn;
	
	@FindBy(xpath = "//*[contains(text(),'Remove questions')]")
	WebElement removeQuestionsBtn;
	
	public List<WebElement> sortQuestionsBtnList() {
		return driver.findElements(By.xpath("//*[contains(text(),'Sort questions')]"));
	}
	
	public List<WebElement> removeQuestionsBtnList() {
		return driver.findElements(By.xpath("//*[contains(text(),'Remove questions')]"));
	}
	
	@FindBy(xpath = "//*[contains(text(),'Create question')]")
	WebElement createQuestionsBtn;
	
	public List<WebElement> createQuestionsBtnList() {
		return driver.findElements(By.xpath("//*[contains(text(),'Create question')]"));
	}

	public List<WebElement> headerList() {
		return driver.findElements(By.xpath("//*[contains(text(),'The awesome Q/A tool')]"));
	}
	
	public List<WebElement> createNewQuestionList() {
		return driver.findElements(By.xpath("//*[contains(text(),'Create a new question')]"));
	}
	
	@FindBy(xpath = "//*[contains(text(),'Question')]/following-sibling::input")
	WebElement questionTextBox;
	
	@FindBy(xpath = "//*[contains(text(),'Answer')]/following-sibling::textarea")
	WebElement answerTextBox;
	
	public List<WebElement> questionTextBoxList() {
		return driver.findElements(By.xpath("//*[contains(text(),'Question')]/following-sibling::input"));
	}
	
	public List<WebElement> answerTextBoxList() {
		return driver.findElements(By.xpath("//*[contains(text(),'Answer')]/following-sibling::textarea"));
	}

	@FindBy(xpath = "//div[@class='sidebar']")
	WebElement sideBar;	
	
	public List<WebElement> createdQuestionsHeaderList() {
		return driver.findElements(By.xpath("//*[contains(text(),'Created questions')]"));
	}
	
	public List<WebElement> createdQuestionsList() {
		return driver.findElements(By.xpath("//*[@class='list-group list-group-flush']//li"));
	}
	
	public List<WebElement> questionsList(int i) {
		return driver.findElements(By.xpath("(//*[@class='list-group list-group-flush']//li)["+i+"]/div"));
	}
	
	public List<WebElement> answersList(int i) {
		return driver.findElements(By.xpath("(//*[@class='list-group list-group-flush']//li)["+i+"]/p"));
	}
	
	public List<WebElement> removeQuestionsValidationList() {
		return driver.findElements(By.xpath("//*[contains(text(),'No questions yet')]"));
	}
	
	public void validateSideBarText() {
		String sideBarText = sideBar.getText();
		String expectedText = DriverManager.propFile.getProperty("sideBarExpectedText");
		SoftAssertion.assertTrue(sideBarText.equalsIgnoreCase(expectedText), "Side Bar Text "+sideBarText+" is not displayed as expected text "+expectedText+"");
	}
	
	public void validatesortQuestionsButton() {
		SoftAssertion.assertTrue(sortQuestionsBtnList().size()!=0, "Sort Questions Button is not displayed");
	}
	
	public void validateRemoveQuestionButton() {
		SoftAssertion.assertTrue(removeQuestionsBtnList().size()!=0, "Remove Questions Button is not displayed");
	}
	
	public void validateCreateQuestionButton() {
		SoftAssertion.assertTrue(createQuestionsBtnList().size()!=0, "Create Questions Button is not displayed");
	}
	
	public void validateCreatedQuestions() {
		int createdQuestionsSize = createdQuestionsList().size();
		assertTrue(createdQuestionsSize==1, "Default Question is not displayed by default");
		
		String defaultQuestion = questionsList(1).get(0).getText().toString().trim();
		questionsList(1).get(0).click();
		String defaultAnswer = answersList(1).get(0).getText().toString().trim();
		
		SoftAssertion.assertTrue(defaultQuestion.equals("How to add a question?"), "Default Question is not displayed as expected");
		SoftAssertion.assertTrue(defaultAnswer.equals("Just use the form below!"), "Default Answer is not displayed as expected");
	}
	
	public void addQuestions(String question,String answer) {
		if(questionTextBoxList().size()!=0) {
			questionTextBox.click();
			questionTextBox.sendKeys(question);
		}else {
			assertTrue(questionTextBoxList().size()!=0, "Question Textbox is not displayed");
		}
		if(answerTextBoxList().size()!=0) {
			answerTextBox.click();
			answerTextBox.sendKeys(answer);
		}else {
			assertTrue(answerTextBoxList().size()!=0, "Answer Textbox is not displayed");
		}
		
		Reporter.logger("Question and Answer added sucessfully!!!");
	}
	
	public void clickCreateQuestion() {
		if(createQuestionsBtnList().size()!=0) {
			createQuestionsBtn.click();
		}else {
			SoftAssertion.assertTrue(createQuestionsBtnList().size()!=0, "Create Question Button is not displayed");
		}
	}
	
	public void validateSort() {
		int createdQuestionsSize = createdQuestionsList().size();
		List<String> questionsList = new ArrayList<String>();
		questionsList = getQuestionsList(createdQuestionsSize);
		Reporter.logger("Questions before Sort: "+questionsList+"");
		Collections.sort(questionsList, String.CASE_INSENSITIVE_ORDER);
		
		
		clickSortQuestion();
		List<String> sortedQuestionsList = new ArrayList<String>();
		int sortedQuestionsSize = createdQuestionsList().size();
		sortedQuestionsList = getQuestionsList(sortedQuestionsSize);
		Reporter.logger("Questions after Sort: "+sortedQuestionsList+"");
		
		SoftAssertion.assertTrue(questionsList.equals(sortedQuestionsList), "Sort is not successful");
	}
	
	public List<String> getQuestionsList(int createdQuestionsSize) {
		List<String> questionsList = new ArrayList<String>();
		for(int i=0 ;i<createdQuestionsSize;i++) {
			String question = questionsList(i+1).get(0).getText().toString().trim();
			questionsList.add(question);
		}
		return questionsList;
	}
	
	public void clickSortQuestion() {
		if(sortQuestionsBtnList().size()!=0) {
			sortQuestionsBtn.click();
		}else {
			SoftAssertion.assertTrue(sortQuestionsBtnList().size()!=0, "Sort Question Button is not displayed");
		}
	}
	
	public void clickRemoveQuestion() {
		if(removeQuestionsBtnList().size()!=0) {
			removeQuestionsBtn.click();
		}else {
			SoftAssertion.assertTrue(removeQuestionsBtnList().size()!=0, "Remove Question Button is not displayed");
		}
	}
	
	public void validateRemoveQuestions() {
		int size = removeQuestionsValidationList().size();
		SoftAssertion.assertTrue(size!=0, "Remove Questions functionality is not working as expected");
	}
	
	public void validateCountInDefaultMessageAfterAddition() {
		String sideBarText = sideBar.getText();
		String expectedText = DriverManager.propFile.getProperty("sideBarExpectedText");
		int size = createdQuestionsList().size();
		String count = Integer.toString(size);
		int index = expectedText.indexOf("1");
		expectedText = expectedText.replace("1", count);
		if(size>1) {
			expectedText = expectedText.replaceFirst("question", "questions");
		}
		SoftAssertion.assertTrue(sideBarText.equalsIgnoreCase(expectedText), "Side Bar Text "+sideBarText+" is not displayed as expected text "+expectedText+"");
	}
	
	public void countBeforeAdding() {
		count = createdQuestionsList().size();
	}
	
	public void validateNonAddition() {
		int countAfterAdd = createdQuestionsList().size();
		assertTrue(count==countAfterAdd, "Questions are not added");
	}
}
