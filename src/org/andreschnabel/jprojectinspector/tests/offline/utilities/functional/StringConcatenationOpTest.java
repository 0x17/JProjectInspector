package org.andreschnabel.jprojectinspector.tests.offline.utilities.functional;

import org.andreschnabel.jprojectinspector.utilities.functional.StringConcatenationOp;
import org.junit.Assert;
import org.junit.Test;

public class StringConcatenationOpTest {
	@Test
	public void testInvoke() throws Exception {
		String result = new StringConcatenationOp(", ").invoke("This might be a test", "right?");
		Assert.assertEquals("This might be a test, right?", result);

		result = new StringConcatenationOp("").invoke("Concatenate", "This!");
		Assert.assertEquals("ConcatenateThis!", result);
	}
}
