package com.studocu.utilities;




import com.studocu.page.BasePage;

import io.cucumber.java.Scenario;

/**
 * Contains methods used for logging information in reports
 *
 */
public class Reporter {
	private static Scenario logger = BasePage.scenario;

	public Reporter(Scenario scenario) {
		logger = BasePage.scenario;
	}

	public static void logger(String message) {
		logger.log(message);
	}

	public void customTableHeader() {
//		String html="<table style=\"border:1px solid black:\" class=\"data-table\">"
//				+ "<tbody><tr style=\"border:1px solid black:\"><th width=\"580\">Step Verifications</th> <th width=\"70\">Status</th></tr>";
//		logger.embed(html.getBytes(), "");
		logger.log("<table style=\"border:1px solid black:\" class=\"data-table\">"
				+ "<tbody><tr style=\"border:1px solid black:\"><th width=\"580\">Step Verifications</th> <th width=\"70\">Status</th></tr>");
	}

	public void logVerifictions(String message, String status) {
		switch (status) {
		case "PASS":
			logger.log("<tr><td width=\"680\" style='text-align:left'><i>" + message + "</i></td> <td width=\"80\">"
					+ "<font color='GREEN'><i>Pass</i></font></td></tr>");
			break;
		case "FAIL":
			logger.log("<tr><td width=\"680\" style='text-align:left'><i>" + message + "</i></td> <td width=\"80\">"
					+ "<font color='RED'><i>Fail</i></font></td></tr>");
			break;
		case "DONE":
			logger.log("<tr><td width=\"680\" style='text-align:left'><i>" + message + "</i></td> <td width=\"80\">"
					+ "<font color='BLUE'><i>Done</i></font></td></tr>");
			break;
		case "BLANK":
			logger.log("<tr><td width=\"680\" style='text-align:left'><b>" + message + "</b></td> <td width=\"80\">"
					+ "</td></tr>");
			break;

		}

	}

	public void customTableFooter() {
		logger.log("</tbody><div><div/></table>");
	}
}
