package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.util.List;
import org.junit.Assert;

public class AssertHelpers {

	public static <T> void arrayEqualsLstOrderInsensitive(T[] expectedValues, List<T> actualValues) {
		Assert.assertEquals(expectedValues.length, actualValues.size());
		for(T val : expectedValues) {
			if(!actualValues.contains(val)) {
				Assert.fail("Value " + val + " missing!");
			}
		}
	}
	
	public static <T> void arrayEqualsLstOrderSensitive(T[] expectedValues, List<T> actualValues) {
		Assert.assertEquals(expectedValues.length, actualValues.size());
		for(int i=0; i<expectedValues.length; i++) {
			if(!expectedValues[i].equals(actualValues.get(i))) {
				Assert.fail(i + "th element not equal, is " + actualValues.get(i) + " should be " + expectedValues[i]);
			}
		}
	}

}
