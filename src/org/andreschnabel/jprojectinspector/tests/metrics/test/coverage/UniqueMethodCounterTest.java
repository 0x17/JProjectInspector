package org.andreschnabel.jprojectinspector.tests.metrics.test.coverage;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.UniqueMethodCounter;
import org.junit.Before;
import org.junit.Test;

public class UniqueMethodCounterTest {

	private UniqueMethodCounter umc;

	@Before
	public void setUp() throws Exception {
		this.umc = new UniqueMethodCounter();
	}

	@Test
	public void testDetermineUniqueMethodsInProject() throws Exception {
		List<String> projectMethodNames = new LinkedList<String>();
		umc.determineUniqueMethodsInProject(new File("testdata"), projectMethodNames);
		assertEquals(5, projectMethodNames.size());
		String[] expectedMethods = {"Point2D", "Point3D", "Position2D", "getLocation", "Position3D"};
		for(int i=0; i<expectedMethods.length; i++) {
			assertEquals(expectedMethods[i], projectMethodNames.get(i));
		}
	}

	@Test
	public void testDetermineUniqueMethodsInSrcStr() {
		List<String> projectMethodNames = new LinkedList<String>();
		umc.determineUniqueMethodsInSrcStr("public static void main(String[] args) {}", projectMethodNames);
		assertEquals(1, projectMethodNames.size());
		assertEquals("main", projectMethodNames.get(0));
	}

}
