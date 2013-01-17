package org.andreschnabel.jprojectinspector.parsers.coverage;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.coverage.Method;
import org.junit.Before;
import org.junit.Test;

public class MethodIndexBuilderTest {

	private MethodIndexBuilder mib;

	@Before
	public void setUp() throws Exception {
		this.mib = new MethodIndexBuilder();
	}

	@Test
	public void testBuildIndexForProject() {
	}

	@Test
	public void testParseFile() {
	}

	@Test
	public void testParseSrcStr() throws Exception {
		List<Method> methods = mib.parseSrcStr("package testpackage;\npublic class TestClass {\nfloat sin(float x) {}\n}");
		assertEquals(1, methods.size());
		assertEquals(new Method("float", "testpackage", "TestClass", "sin", new String[]{"float"}), methods.get(0));
	}

	@Test
	public void testExtractMethods() {
	}

	@Test
	public void testDeterminePackageNameForFile() {
	}

	@Test
	public void testDeterminePackageNameForSrcStr() {
	}

}
