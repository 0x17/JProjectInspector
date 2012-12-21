package org.andreschnabel.jprojectinspector.utilities.helpers;

import static org.junit.Assert.*;

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
	
	@Test
	public void testRemoveComments() {
		String text = "Das hier soll  nachher ein schöner Text werden!";
		assertEquals(text, StringHelpers.removeComments("Das hier soll /* blabla bla bla sdök033490fk4f+ bla */ nachher ein schöner Text // blabla \nwerden!"));
		
		String src = "/*** This class contains OpenGL specific texture information.* * @author dennis.ippel**/";
		assertEquals("", StringHelpers.removeComments(src));
		
		src = "/**\n"+
		 "* This class contains OpenGL specific texture information.\n"+
		 "* \n"+
		 "* @author dennis.ippel\n"+
		 "*\n" +
		 "*/\n";
		assertEquals("", StringHelpers.removeComments(src).trim());
		
	}
	
	@Test
	public void testRemoveStrings() {
		assertEquals("Bernd sagt: ", StringHelpers.removeStrings("Bernd sagt: \"Lauer meer.\""));
		assertEquals("", StringHelpers.removeStrings("\"\"\"\""));
		assertEquals("  ", StringHelpers.removeStrings(" \"\" "));
	}
	
}
