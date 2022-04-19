package com.studocu.stepdefinitions;

import com.studocu.page.MainPage.QAPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class PageSteps {

	private QAPage test;

	public PageSteps(QAPage test) {
		this.test = test;
	}

	@Given("user validates side bar text")
	public void user_validates_side_bar_text() {
	    test.validateSideBarText();
	}

	@Given("user validates sort questions button")
	public void user_validates_sort_questions_button() {
	    test.validatesortQuestionsButton();
	}

	@Given("user validates remove questions button")
	public void user_validates_remove_questions_button() {
	    test.validateRemoveQuestionButton();
	}

	@Given("user validates create question button")
	public void user_validates_create_question_button() {
	    test.validateCreateQuestionButton();
	}

	@Given("user validates default question displayed under Created Questions section")
	public void user_validates_default_question_displayed_under_Created_Questions_section() {
	    test.validateCreatedQuestions();
	}

	@Given("user creates question {string}, {string}")
	public void user_creates_question(String string, String string2) {
	    test.addQuestions(string, string2);
	}

	@Given("user clicks on create question button")
	public void user_clicks_on_create_question_button() {
	    test.clickCreateQuestion();
	}

	@Given("user clicks on Sort Questions Button")
	public void user_clicks_on_Sort_Questions_Button() {
	    test.clickSortQuestion();
	}

	@Then("user validates sort functionality")
	public void user_validates_sort_functionality() {
	    test.validateSort();
	}

	@Given("user clicks on Remove Questions Button")
	public void user_clicks_on_Remove_Questions_Button() {
	    test.clickRemoveQuestion();
	}

	@Then("user validates remove functionality")
	public void user_validates_remove_functionality() {
	    test.validateRemoveQuestions();
	}
	
	@Given("user validates the count in the default text displayed in the top left corner")
	public void user_validates_the_count_in_the_default_text_displayed_in_the_top_left_corner() {
	    test.validateCountInDefaultMessageAfterAddition();
	}
	
	@Given("user saves the number of questions before creating new questions")
	public void user_saves_the_number_of_questions_before_creating_new_questions() {
	    test.countBeforeAdding();
	}

	@Given("user validates question is not created")
	public void user_validates_question_is_not_created() {
	    test.validateNonAddition();
	}

}
