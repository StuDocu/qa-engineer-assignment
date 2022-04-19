@test
Feature: Test Feature

  Scenario: Validate Side Bar Text
    Given user validates side bar text

  Scenario: Validate Buttons on page
    Given user validates sort questions button
    Given user validates remove questions button
    Given user validates create question button

  Scenario: Validate Default Created Questions
    Given user validates default question displayed under Created Questions section

  Scenario Outline: Create Questions
    Given user creates question "<question>", "<answer>"
    And user clicks on create question button
    And user validates the count in the default text displayed in the top left corner

    Examples: 
      | Sno | question                  | answer               |
      |   1 | where to add the question | In the textbox below |
      |   2 | is the question added     | Yes                  |
      |   3 | test1                     | answer1              |
      |   4 | abc                       | def                  |
      |   5 | Points to Remember        | To be added in notes |
      
    Scenario Outline: Create Questions - Negative Test Cases
    Given user saves the number of questions before creating new questions
    Given user creates question "<question>", "<answer>"
    And user clicks on create question button
    And user validates question is not created

    Examples: 
      | Sno | question                  | answer |
      |   1 | where to add the question |        |
      |   2 |                           |        |
      
      Scenario: Validate Sort Functionality
    Given user clicks on Sort Questions Button
    Then user validates sort functionality
    
     Scenario: Validate Remove Functionality
    Given user clicks on Remove Questions Button
    Then user validates remove functionality
    
    