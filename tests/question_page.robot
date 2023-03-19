*** Settings ***
Library     pages/QuestionPage.py
Force Tags  studocu
Test Setup   setup
Test Teardown    Tear Down

*** Keywords ***
setup
    Launch Browser
    Open Application


tear down
    Close Application
    Close Browser


*** Test Cases ***
check sidebar text with one, two and no questions
    [Tags]    sidebar       questions
    Check Questions Count
    Check Sidebar Text
    Create Question And Answer    count=${2}
    Check Questions Count
    Check Sidebar Text
    Remove Questions
    Check Questions Count
    Check Sidebar Text

check sort functionality with questions that start with integer
    [Tags]    sort      questions       answers
    remove questions
    Fill Question    question=11-question
    Fill Answer    answer=11-answer
    add_question
    Fill Question    question=5-question
    Fill Answer    answer=5-answer
    add_question
    Fill Question    question=1-question
    Fill Answer    answer=1-answer
    add_question
    Check Question Order    index=0     question=11-question
    Check Question Order    index=1     question=5-question
    Check Question Order    index=2     question=1-question
    Sort Questions
    Check Question Order    index=0     question=1-question
    Check Question Order    index=1     question=5-question
    Check Question Order    index=2     question=11-question

check answer visibility before after sort with two questions open
    [Tags]    visibility    sort        answers      questions

    Fill Question    question=1-question
    Fill Answer    answer=1-answer
    add_question
    Check Answer    index=1     answer=1-answer
    Click On Question    index=0
    Sort Questions
    Check Answer Visibility    index=0      visible=${True}
    Check Answer Visibility    index=1      visible=${True}

check answer visibility after sort with one question open
    [Tags]    visibility     answers
    Fill Question    question=2-question
    Fill Answer    answer=2-answer
    add question
    Fill Question    question=1-question
    Fill Answer    answer=1-answer
    add question
    Click On Question    index=0
    Check Answer Visibility    index=0
    Sort Questions
    Check Answer Visibility    index=0      visible=${False}
    Check Answer Visibility    index=2      visible=${True}

check sort functionality with string questions
    [Tags]    sort
    Fill Question    question=a-question
    Fill Answer    answer=a-answer
    add question
    Fill Question    question=c-question
    Fill Answer    answer=c-answer
    add question
    Fill Question    question=b-question
    Fill Answer    answer=b-answer
    add question
    Sort Questions
    Check Question Order    index=1     question=b-question

check required fields
    [Tags]    required
    add question    success=${False}
    Check Questions Count
    Check Sidebar Text
    Fill Question       question=a-question
    add question    success=${False}
    Check Questions Count
    Check Sidebar Text
    Clear Fields
    Fill Answer    answer=a-answer
    add question    success=${False}
    Check Questions Count
    Check Sidebar Text

check remove button
    [Tags]    questions
    remove questions
    check questions_removed
    Check Questions Count
    check sidebar text

check questions with whitespace
    [Tags]    required      questions
    Fill Question    question=${SPACE}
    Fill Answer    answer=${SPACE}
    add question
    Click On Question    index=1
    Check Answer Visibility    index=1      visible=${True}

check static texts
    [Tags]    texts
    Check Static Texts
    Check Question Order    index=0     question=How to add a question?
    Check Answer    index=0     answer=Just use the form below!

check buttons removed
    [Tags]    sort
    Remove Questions
    Check Buttons Removed
    Create Question And Answer    count=${1}
    Check Buttons Removed       exist=${True}