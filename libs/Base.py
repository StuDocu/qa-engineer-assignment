from playwright.sync_api import sync_playwright

from vars import configs


class Base:
    browser = None
    context = None
    page = None
    pwSync = None

    @staticmethod
    def launch_browser(browser_name=configs.browser_name,
                       record_video=configs.record_video,
                       headless=configs.headless,
                       slowmo=configs.slowmo):
        print("Opening Browser : " + browser_name)

        Base.pwSync = sync_playwright().start()  # creating sync obj
        if browser_name.lower() == 'chromium':
            Base.browser = Base.pwSync.chromium.launch(headless=headless, slow_mo=slowmo)
        elif browser_name.lower() == 'firefox':
            Base.browser = Base.pwSync.firefox.launch(headless=headless, slow_mo=slowmo)
        else:
            Base.browser = Base.pwSync.webkit.launch(headless=headless, slow_mo=slowmo)
        if record_video:
            Base.context = Base.browser.new_context(
                viewport={"width": 1375, "height": 885},
                record_video_dir="../reports/Videos/",
                record_video_size={"width": 640, "height": 480}
            )
        else:
            Base.context = Base.browser.new_context()

    @staticmethod
    def open_application(url=configs.base_url):
        Base.page = Base.context.new_page()
        Base.page.goto(url)

    @staticmethod
    def get_page_object():
        return Base.page

    @staticmethod
    def take_screenshot():
        return Base.page.screenshot(path="Reports/screenshot.png")

    @staticmethod
    def close_application():
        Base.page.close()

    @staticmethod
    def close_browser():
        Base.context.close()
        Base.browser.close()
        Base.pwSync.stop()
