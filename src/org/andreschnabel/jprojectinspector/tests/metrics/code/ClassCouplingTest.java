package org.andreschnabel.jprojectinspector.tests.metrics.code;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.andreschnabel.jprojectinspector.metrics.code.ClassCoupling;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.Helpers;
import org.junit.Before;
import org.junit.Test;

public class ClassCouplingTest {
	
	private ClassCoupling cc;

	@Before
	public void setUp() throws Exception {
		this.cc = new ClassCoupling();
	}
	
	@Test
	public void testCodeOfClassInSourceStr() throws Exception {
		String testSrc = Helpers.readEntireFile(new File(TestCommon.TEST_SRC_FILENAME));
		String actualClassStr = cc.getCodeOfClassInSrcStr("Point3D", testSrc);		
		String expectedClassStr = "protectedintz;publicPoint3D(intx,inty){this(x,y,0);}publicPoint3D(intx,inty,intz){this.x=x;this.y=y;this.z=z;}";
		assertEquals(expectedClassStr, Helpers.removeAllWhitespace(actualClassStr));
	}
	
	@Test
	public void testListClassNamesInFile() throws Exception {
		List<String> actualClassLst = cc.listClassNamesInFile(new File(TestCommon.TEST_SRC_FILENAME), new HashMap<String, File>());
		Helpers.assertListEquals(new String[] {"Point2D", "Point3D", "Position2D", "Position3D"}, actualClassLst);
	}
	
	@Test
	public void testListClassNamesInProject() throws Exception {
		List<String> actualClassLst = cc.listClassNamesInProject(new File(TestCommon.TEST_SRC_DIRECTORY), new HashMap<String, File>());
		Helpers.assertListEquals(new String[] {"Point2D", "Point3D", "Position2D", "Position3D"}, actualClassLst);
	}
	
	@Test
	public void testReferencedClasses() throws Exception {
		Map<String, File> classInFile = new HashMap<String, File>();
		cc.listClassNamesInProject(new File(TestCommon.TEST_SRC_DIRECTORY), classInFile);
		List<String> actualRefClsLst = cc.referencedClasses("Position2D", classInFile);
		List<String> expectedRefClsLst = new LinkedList<String>();
		expectedRefClsLst.add("Point2D");
		assertEquals(expectedRefClsLst, actualRefClsLst);
	}

	@Test
	public void testGetAverageCoupling() throws Exception {
		float actualAvgCoupling = cc.getAverageCoupling(new File(TestCommon.TEST_SRC_DIRECTORY));
		float expectedAvgCoupling = 2.0f/4.0f;
		assertEquals(expectedAvgCoupling, actualAvgCoupling, TestCommon.DELTA);
	}
}