package org.andreschnabel.jprojectinspector.parsers.coverage;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.coverage.Method;
import org.andreschnabel.jprojectinspector.parsers.ParserHelpers;
import org.junit.Before;
import org.junit.Test;

public class MethodIndexBuilderTest {
	@Test
	public void testBuildIndexForProject() {
	}

	@Test
	public void testParseFile() {
	}

	@Test
	public void testParseSrcStr() throws Exception {
		List<Method> methods = MethodIndexBuilder.parseSrcStr("package testpackage;\npublic class TestClass {\nfloat sin(float x) {}\n}");
		assertEquals(1, methods.size());
		assertEquals(new Method("float", "testpackage", "TestClass", "sin", new String[]{"float"}), methods.get(0));
	}

	@Test
	public void testExtractMethods() throws Exception {
		String packName = MethodIndexBuilder.determinePackageNameForSrcStr("package somepack;");
		String code = "package somepack;\n"
				+"public class SomeClass {\n"
				+"public static void main(String[] args)\n"
				+"{ System.out.println(\"Hello\"); } }";
		MethodIndexBuilder.extractMethods(ParserHelpers.parserForStr(code), packName);
	}

	@Test
	public void testDeterminePackageNameForFile() {
	}

	@Test
	public void testDeterminePackageNameForSrcStr() throws Exception {
		assertEquals("somepack", MethodIndexBuilder.determinePackageNameForSrcStr("package somepack;"));
	}

}
