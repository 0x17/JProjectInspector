package org.andreschnabel.jprojectinspector.tests;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.andreschnabel.jprojectinspector.Globals;
import org.andreschnabel.jprojectinspector.metrics.code.ClassCoupling;
import org.andreschnabel.jprojectinspector.utilities.Helpers;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClassCouplingTest {
	
	private static final String TEST_SRC_FILENAME = "testdata/Points.java";
	private static final String TEST_SRC_DIRECTORY = "testdata";
	
	private ClassCoupling cc;

	@Before
	public void setUp() throws Exception {
		this.cc = new ClassCoupling();
	}

	@Test
	public void testReset() {
		Map<String, File> classInFile = cc.getClassInFile();
		classInFile.put("SomeClassName", new File(""));
		cc.reset();
		assertEquals(0, classInFile.size()); 
	}
	
	@Test
	public void testCodeOfClassInSourceStr() throws Exception {
		String testSrc = Helpers.readEntireFile(new File(TEST_SRC_FILENAME));
		String actualClassStr = cc.getCodeOfClassInSrcStr("Point3D", testSrc);		
		String expectedClassStr = "protectedintz;publicPoint3D(intx,inty){this(x,y,0);}publicPoint3D(intx,inty,intz){this.x=x;this.y=y;this.z=z;}";
		assertEquals(expectedClassStr, Helpers.removeAllWhitespace(actualClassStr));
	}
	
	@Test
	public void testListClassNamesInFile() throws Exception {
		List<String> actualClassLst = cc.listClassNamesInFile(new File(TEST_SRC_FILENAME));
		List<String> expectedClassLst = new LinkedList<String>();
		Collections.addAll(expectedClassLst, new String[] {"Point2D", "Point3D", "Position2D", "Position3D"});
		assertEquals(expectedClassLst, actualClassLst);
	}
	
	@Test
	public void testListClassNamesInProject() throws Exception {
		List<String> actualClassLst = cc.listClassNamesInProject(new File(TEST_SRC_DIRECTORY));
		List<String> expectedClassLst = new LinkedList<String>();
		Collections.addAll(expectedClassLst, new String[] {"Point2D", "Point3D", "Position2D", "Position3D"});
		assertEquals(expectedClassLst, actualClassLst);
	}
	
	@Test
	public void testReferencedClasses() throws Exception {
		cc.listClassNamesInProject(new File(TEST_SRC_DIRECTORY));
		List<String> actualRefClsLst = cc.referencedClasses("Position2D");
		List<String> expectedRefClsLst = new LinkedList<String>();
		expectedRefClsLst.add("Point2D");
		assertEquals(expectedRefClsLst, actualRefClsLst);
	}

	@Test
	public void testGetAverageCoupling() throws Exception {
		float actualAvgCoupling = cc.getAverageCoupling(new File(TEST_SRC_DIRECTORY));
		float expectedAvgCoupling = 2.0f/4.0f;
		assertEquals(expectedAvgCoupling, actualAvgCoupling, Globals.DELTA);
	}
}
