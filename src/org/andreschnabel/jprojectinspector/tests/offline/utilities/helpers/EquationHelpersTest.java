package org.andreschnabel.jprojectinspector.tests.offline.utilities.helpers;

import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.helpers.EquationHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EquationHelpersTest {
	@Test
	public void testParseEquation() throws Exception {
		Map<String, Object> bindings = new HashMap<String, Object>();
		bindings.put("x", 10);
		bindings.put("y", 20);
		bindings.put("z", 3);
		Object result = EquationHelpers.parseEquation(bindings, "(x+y)/z");
		Assert.assertEquals(10.0, (Double)result, TestCommon.DELTA);
	}

	@Test
	public void testValidateEquation() throws Exception {
		List<String> varNames = Arrays.asList(new String[] {"x", "y", "mass", "velocity"});
		Assert.assertTrue(EquationHelpers.validateEquation(varNames, "x+y"));
		Assert.assertTrue(EquationHelpers.validateEquation(varNames, "(x+y)/(mass*velocity)"));

		Assert.assertFalse(EquationHelpers.validateEquation(varNames, "x+y+z"));
		Assert.assertFalse(EquationHelpers.validateEquation(varNames, "(x+y)/(mass*velocityz)"));
	}
}
