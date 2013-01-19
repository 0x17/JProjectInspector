package org.andreschnabel.jprojectinspector.tests;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Before;
import org.junit.Test;

public class AssertHelpersTest {

	private String[] expectedValues;
	private List<String> actualValues;

	@Before
	public void setUp() {
		expectedValues = new String[]{"some", "strings"};
		actualValues = new ArrayList<String>();
		actualValues.add("strings");
		actualValues.add("some");
	}

	@Test
	public void testArrayEqualsLstOrderInsensitiveCorrect() {
		try {
			AssertHelpers.arrayEqualsLstOrderInsensitive(expectedValues, actualValues);
		} catch(Exception e) {
			Assert.fail();
		}
	}

	@Test(expected = AssertionError.class)
	public void testArrayEqualsLstOrderInsensitiveLengthFail() {
		actualValues.add("doesntmatter");
		AssertHelpers.arrayEqualsLstOrderInsensitive(expectedValues, actualValues);
	}

	@Test(expected = AssertionError.class)
	public void testArrayEqualsLstOrderInsensitiveContentFail() {
		actualValues.clear();
		actualValues.add("some");
		actualValues.add("integers");
		AssertHelpers.arrayEqualsLstOrderInsensitive(expectedValues, actualValues);
	}

}
