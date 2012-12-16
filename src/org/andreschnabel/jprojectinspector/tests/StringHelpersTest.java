package org.andreschnabel.jprojectinspector.tests;

import static org.junit.Assert.*;

import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;
import org.junit.Test;

public class StringHelpersTest {

	@Test
	public void testCapitalize() {
		assertEquals("Bernd", StringHelpers.capitalize("bernd"));
		assertEquals("Bernd", StringHelpers.capitalize("Bernd"));		
	}
	
	@Test
	public void testCountOccurencesOfWord() {
		assertEquals(4, StringHelpers.countOccurencesOfWord("dewiofjwoiefjewofjblablablaeiofjiowejfbla", "bla"));
	}
	
	@Test
	public void testCountOccurencesOfWords() {
		assertEquals(4, StringHelpers.countOccurencesOfWords("asiodjiosjdoaisdjiaosdjasiodjblabluppblasjidojsdiobla", new String[] {"bla", "blupp"}));
	}

}
