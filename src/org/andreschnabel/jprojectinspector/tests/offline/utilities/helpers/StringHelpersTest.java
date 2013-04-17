package org.andreschnabel.jprojectinspector.tests.offline.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.helpers.JavaSourceHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StringHelpersTest {
	
	@Test
	public void testStrContainsOneOf() {
		Assert.assertTrue(StringHelpers.containsOneOf("dies ist ein test", "ein"));
		Assert.assertFalse(StringHelpers.containsOneOf("dies ist ein test", ":D"));
	}

	@Test
	public void testCapitalize() {
		Assert.assertEquals("Bernd", StringHelpers.capitalizeFirstLetter("bernd"));
		assertEquals("Bernd", StringHelpers.capitalizeFirstLetter("Bernd"));
	}

	@Test
	public void testCountOccurencesOfWord() {
		assertEquals(4, StringHelpers.countOccurencesOfWord("dewiofjwoiefjewofjblablablaeiofjiowejfbla", "bla"));
	}

	@Test
	public void testCountOccurencesOfWords() {
		assertEquals(4, StringHelpers.countOccurencesOfWords("asiodjiosjdoaisdjiaosdjasiodjblabluppblasjidojsdiobla", new String[]{"bla", "blupp"}));
	}

	@Test
	public void testRemoveComments() {
		String text = "Das hier soll  nachher ein schöner Text werden!";
		assertEquals(text, JavaSourceHelpers.removeComments("Das hier soll /* blabla bla bla sdök033490fk4f+ bla */ nachher ein schöner Text // blabla \nwerden!"));

		String src = "/*** This class contains OpenGL specific texture information.* * @author dennis.ippel**/";
		assertEquals("", JavaSourceHelpers.removeComments(src));

		src = "/**\n" +
				"* This class contains OpenGL specific texture information.\n" +
				"* \n" +
				"* @author dennis.ippel\n" +
				"*\n" +
				"*/\n";
		assertEquals("", JavaSourceHelpers.removeComments(src).trim());

	}

	@Test
	public void testRemoveStrings() {
		assertEquals("Bernd sagt: ", JavaSourceHelpers.removeStrings("Bernd sagt: \"Lauer meer.\""));
		assertEquals("", JavaSourceHelpers.removeStrings("\"\"\"\""));
		assertEquals("  ", JavaSourceHelpers.removeStrings(" \"\" "));
	}

	@Test
	public void testSplitMethodBodies() throws Exception {
		String src = "Das hier sollte nicht stören, oder?\n"
				+ "public static void main(String[] args) {Lach doch mal { Berndstyle } xD} "
				+ "das hier sollte auch egal sein";
		List<String> methodBodies = JavaSourceHelpers.splitMethodBodies(src);
		assertEquals(1, methodBodies.size());
		assertEquals("Lach doch mal { Berndstyle } xD", methodBodies.get(0));
	}

	@Test
	public void testExtractFieldDeclarations() throws Exception {
		String src = "package blap.sdjkkjfnef;\n"
				+ "\tpublic class Spurdo {\n"
				+ "\t\tint zahl = 3;\n"
				+ "de.uni-hannover.se.KlassenName obj;\n}\n";
		Map<String, String> expectedFields = new HashMap<String, String>();
		expectedFields.put("zahl", "int");
		expectedFields.put("obj", "de.uni-hannover.se.KlassenName");
		assertEquals(expectedFields, JavaSourceHelpers.extractFieldDeclarations(src));
	}

}
