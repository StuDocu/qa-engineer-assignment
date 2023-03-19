import os

from robot.libraries.BuiltIn import BuiltIn

bi = BuiltIn()
base_url = os.environ.get('PROJECT_BASE_URL', '127.0.0.1:8000')
browser_name = os.environ.get('BROWSER_NAME', 'chromium')
record_video = os.environ.get('RECORD_VIDEO', False)
slowmo = os.environ.get('SLOWMO', None)
headless = os.environ.get('HEADLESS', True)

try:
    base_url = bi.get_variable_value('${base_url}', base_url)
    browser_name = bi.get_variable_value('${browser_name}', browser_name)
    record_video = bi.get_variable_value('${record_video}', record_video)
    slowmo = bi.get_variable_value('${slowmo}', slowmo)
    headless = bi.get_variable_value('${headless}', headless)

except Exception as e:
    bi.log_to_console(str(e))
