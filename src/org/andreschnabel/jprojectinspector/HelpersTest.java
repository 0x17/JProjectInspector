package org.andreschnabel.jprojectinspector;

import static org.junit.Assert.*;

import org.junit.Test;

public class HelpersTest {

	@Test
	public void testCapitalize() {
		assertEquals("Bernd", Helpers.capitalize("bernd"));
		assertEquals("Bernd", Helpers.capitalize("Bernd"));		
	}

}
