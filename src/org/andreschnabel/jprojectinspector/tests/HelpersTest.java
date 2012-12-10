package org.andreschnabel.jprojectinspector.tests;

import static org.junit.Assert.*;

import org.andreschnabel.jprojectinspector.utilities.Helpers;
import org.junit.Test;

public class HelpersTest {

	@Test
	public void testCapitalize() {
		assertEquals("Bernd", Helpers.capitalize("bernd"));
		assertEquals("Bernd", Helpers.capitalize("Bernd"));		
	}

}
