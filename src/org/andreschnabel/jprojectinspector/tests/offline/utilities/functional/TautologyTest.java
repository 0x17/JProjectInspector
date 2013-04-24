package org.andreschnabel.jprojectinspector.tests.offline.utilities.functional;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.utilities.functional.Tautology;
import org.junit.Test;

public class TautologyTest {
	@Test
	public void testInvoke() throws Exception {
		Assert.assertTrue(new Tautology<String>().invoke("anything"));
		Assert.assertTrue(new Tautology<Object>().invoke(new Object()));
		Assert.assertTrue(new Tautology<Object>().invoke(null));
	}
}
