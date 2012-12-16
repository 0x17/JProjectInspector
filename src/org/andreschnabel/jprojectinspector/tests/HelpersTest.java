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
	
	@Test
	public void testCountOccurencesOfWord() {
		assertEquals(4, Helpers.countOccurencesOfWord("dewiofjwoiefjewofjblablablaeiofjiowejfbla", "bla"));
	}
	
	@Test
	public void testCountOccurencesOfWords() {
		assertEquals(4, Helpers.countOccurencesOfWords("asiodjiosjdoaisdjiaosdjasiodjblabluppblasjidojsdiobla", new String[] {"bla", "blupp"}));
	}

}
