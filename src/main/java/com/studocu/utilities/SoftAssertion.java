package com.studocu.utilities;

import org.testng.asserts.SoftAssert;

public class SoftAssertion {
	private static SoftAssert softAssert;

	public SoftAssertion() {
		softAssert = new SoftAssert();
	}

	/**
	 * If expected and actual values are not matched then register soft failure,
	 * else nothing
	 * 
	 * @param expected
	 *            - expected value
	 * @param actual
	 *            - actual value
	 * @param message
	 *            - message to display if expected and actual values are not matched
	 */
	public static void assertEquals(String expected, String actual, String message) {
		softAssert.assertEquals(actual, expected, message);
	}

	/**
	 * If boolean state is false then register soft failure, else nothing
	 * 
	 * @param state
	 *            - actual value
	 * @param message
	 *            - message to display boolean value is false
	 */
	public static void assertTrue(boolean state, String message) {
		if (state) {
			if (message.contains("not")) {
				message = message.replace("not", "");
			}
			Reporter.logger("SOFT ASSERTION PASS: " + message);
		} else
			Reporter.logger("SOFT ASSERTION FAIL>>>>> " + message);

		softAssert.assertTrue(state, message);
	}

	/**
	 * Throw all failed soft assertions and stops program execution
	 */
	public static void assertAllSoftAssertions() {
		softAssert.assertAll();
	}

}
