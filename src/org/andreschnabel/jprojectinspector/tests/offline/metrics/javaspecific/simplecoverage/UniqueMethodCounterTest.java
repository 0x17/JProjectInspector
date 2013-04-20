package org.andreschnabel.jprojectinspector.tests.offline.metrics.javaspecific.simplecoverage;

import org.andreschnabel.jprojectinspector.metrics.javaspecific.simplejavacoverage.UniqueMethodCounter;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class UniqueMethodCounterTest {

	@Test
	public void testDetermineUniqueMethodsInProject() throws Exception {
		List<String> projectMethodNames = new LinkedList<String>();
		UniqueMethodCounter.determineUniqueMethodsInProject(new File("testdata"), projectMethodNames);
		assertEquals(5, projectMethodNames.size());
		String[] expectedMethods = {"Point2D", "Point3D", "Position2D", "getLocation", "Position3D"};
		for(int i = 0; i < expectedMethods.length; i++) {
			assertEquals(expectedMethods[i], projectMethodNames.get(i));
		}
	}

	@Test
	public void testDetermineUniqueMethodsInSrcStr() {
		List<String> projectMethodNames = new LinkedList<String>();
		UniqueMethodCounter.determineUniqueMethodsInSrcStr("public static void main(String[] args) {}", projectMethodNames);
		assertEquals(1, projectMethodNames.size());
		assertEquals("main", projectMethodNames.get(0));
	}

}
