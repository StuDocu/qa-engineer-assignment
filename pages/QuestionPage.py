from faker import Faker
from playwright.sync_api import expect

from libs.Base import Base


class QuestionPage(Base):
    def __init__(self):
        self.sidebar_locator = 'div.sidebar'
        self.question_count = 1
        self.remove_questions_button_selector = 'button:has-text("Remove questions")'
        self.sort_button_selector = 'button.btn-primary:has-text("Sort questions")'
        self.deleted_questions_alert_message = 'No questions yet :-('
        self.deleted_questions_alert = f'div.alert.alert-danger'
        self.question_input_selector = '#question'
        self.answer_input_selector = '#answer'
        self.create_question_button_selector = 'button:has-text("Create question")'
        self.questions_selector = 'li.list-group-item.question'
        self.header = 'header.header'
        self.questions_title = 'h2.tooltipped-title__title'
        self.answers_title = '.tooltipped-title__tooltip'

    def check_sidebar_text(self):
        count = self.question_count
        if self.question_count == 0:
            sidebar_text = f'Here you can find no questions. Feel free to create your own questions!'
        elif self.question_count == 1:
            sidebar_text = f'Here you can find 1 question. Feel free to create your own questions!'
        else:
            sidebar_text = f'Here you can find {count} questions. Feel free to create your own questions!'
        expect(self.page.locator(self.sidebar_locator)).to_contain_text([sidebar_text])

    def remove_questions(self):
        self.page.locator(self.remove_questions_button_selector).click()
        self.question_count = 0

    def check_questions_removed(self):
        expect(self.page.locator(self.deleted_questions_alert)).to_have_text(self.deleted_questions_alert_message)
        self.check_sidebar_text()

    def fill_question(self, question: str):
        expect(self.page.locator(self.question_input_selector)).to_have_attribute("required", "")
        self.page.locator(self.question_input_selector).fill(question)

    def fill_answer(self, answer: str):
        expect(self.page.locator(self.question_input_selector)).to_have_attribute("required", "")
        self.page.locator(self.answer_input_selector).fill(answer)

    def add_question(self, success: bool = True):
        self.page.locator(self.create_question_button_selector).click()
        if success:
            self.question_count += 1

    def check_questions_count(self):
        count = self.question_count
        expect(self.page.locator(self.questions_selector)).to_have_count(count)

    def create_question_and_answer(self, count):
        fake = Faker()
        for i in range(count):
            self.fill_question(fake.text())
            self.fill_answer(fake.text())
            self.add_question()

    def sort_questions(self):
        self.page.locator(self.sort_button_selector).click()

    def check_question_order(self, index, question):
        expect(self.page.locator(self.questions_selector).nth(index).locator('.question__question')).to_have_text(
            question)

    def check_answer(self, index, answer):
        locator = self.page.locator(self.questions_selector).nth(index)
        self.check_answer_visibility(index=index, visible=False)
        self.click_on_question(index)
        self.check_answer_visibility(index=index, visible=True)
        expect(locator.locator('.question__answer')).to_have_text(answer)

    def check_answer_visibility(self, index, visible=True):
        locator = self.page.locator(self.questions_selector).nth(index).locator('.question__answer')
        if visible:
            expect(locator).to_be_visible()
        else:
            expect(locator).to_be_hidden()

    def click_on_question(self, index):
        self.page.locator(self.questions_selector).nth(index).locator('.question__question').click()

    def clear_fields(self, question: bool = True, answer: bool = True):
        if question:
            self.page.locator(self.question_input_selector).select_text()
            self.page.keyboard.press("Backspace")
        if answer:
            self.page.locator(self.question_input_selector).select_text()
            self.page.keyboard.press("Backspace")

    def check_static_texts(self):
        question_locator = self.page.locator('.questions')
        question_maker_locator = self.page.locator('.question-maker')

        expect(self.page.locator(self.header).locator('h1')).to_have_text('The awesome Q/A tool')

        expect(question_locator.locator(self.questions_title)).to_have_text('Created questions')
        expect(question_locator.locator(self.answers_title)).to_be_hidden()
        question_locator.locator(self.questions_title).hover()
        expect(question_locator.locator(self.answers_title)).to_be_visible()
        expect(question_locator.locator(self.answers_title)).to_have_text(
            'Here you can find the created questions and their answers.')

        expect(question_maker_locator.locator(self.questions_title)).to_have_text('Create a new question')
        expect(question_maker_locator.locator(self.answers_title)).to_be_hidden()
        question_maker_locator.locator(self.questions_title).hover()
        expect(question_maker_locator.locator(self.answers_title)).to_be_visible()
        expect(question_maker_locator.locator(self.answers_title)).to_have_text(
            'Here you can create new questions and their answers.')

    def check_buttons_removed(self,exist:bool= False):
        if exist:
            expect(self.page.locator(self.remove_questions_button_selector)).to_have_count(1)
            expect(self.page.locator(self.sort_button_selector)).to_have_count(1)
        else:
            expect(self.page.locator(self.remove_questions_button_selector)).to_have_count(0)
            expect(self.page.locator(self.sort_button_selector)).to_have_count(0)