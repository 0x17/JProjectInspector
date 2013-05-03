package org.andreschnabel.jprojectinspector.tests.offline.metrics.javaspecific;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.smells.JavaTestSmellDetector;
import org.junit.Test;

import static org.junit.Assert.fail;

public class JavaTestSmellDetectorTest {

	@Test
	public void testMeasure() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testListTests() {
		fail("Split java source into test methods strings '@Test public void ...() {}'");
	}
	
	@Test
	public void testIsTestAssertionless() {
		String src = "@Test\npublic void noAssertions() {\nfail(\"some text\");\n}";
		Assert.assertTrue(JavaTestSmellDetector.isTestAssertionless(src));
		src = "@Test\npublic void withAssrt() {\nAssert.assertEquals(1, 1);\n}";
		Assert.assertFalse(JavaTestSmellDetector.isTestAssertionless(src));
	}
	
	@Test
	public void testContainsAssertionRoulette() {
		String src = "";
		//Assert.assertTrue(JavaTestSmellDetector.containsAssertionRoulette(src));
		src = "";
		//Assert.assertFalse(JavaTestSmellDetector.containsAssertionRoulette(src));
	}
	
	@Test
	public void testContainsDuplicateCode() {
	}
	
	@Test
	public void testIsEmpty() {
	}
	
	@Test
	public void testIsForTestersOnly() {
	}
	
	@Test
	public void testIsGeneralFixture() {
	}
	
	@Test
	public void testIsIndentedTest() {
	}
	
	@Test
	public void testIsIndirectTest() {
	}
	
	@Test
	public void testContainsMisteryGuest() {
	}
	
	@Test
	public void isVerboseTest() {
	}

}
