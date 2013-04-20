package org.andreschnabel.jprojectinspector.tests.offline.metrics.javaspecific;

import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaClassCoupling;
import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.JavaSourceHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JavaClassCouplingTest {

	@Test
	public void testCodeOfClassInSourceStr() throws Exception {
		String testSrc = FileHelpers.readEntireFile(new File(TestCommon.TEST_SRC_FILENAME));
		String actualClassStr = JavaSourceHelpers.getCodeOfClassInSrcStr("Point3D", testSrc);
		String expectedClassStr = "protectedintz;publicPoint3D(intx,inty){this(x,y,0);}publicPoint3D(intx,inty,intz){this.x=x;this.y=y;this.z=z;}";
		assertEquals(expectedClassStr, StringHelpers.removeAllWhitespace(actualClassStr));
	}

	@Test
	public void testListClassNamesInFile() throws Exception {
		List<String> actualClassLst = JavaClassCoupling.listClassNamesInFile(new File(TestCommon.TEST_SRC_FILENAME), new HashMap<String, File>());
		AssertHelpers.arrayEqualsLstOrderSensitive(new String[]{"Point2D", "Point3D", "Position2D", "Position3D"}, actualClassLst);
	}

	@Test
	public void testListClassNamesInProject() throws Exception {
		List<String> actualClassLst = JavaClassCoupling.listClassNamesInProject(new File(TestCommon.TEST_SRC_DIRECTORY), new HashMap<String, File>());
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[]{"PointsTest", "Point2D", "Point3D", "Position2D", "Position3D"}, actualClassLst);
	}

	@Test
	public void testReferencedClasses() throws Exception {
		Map<String, File> classInFile = new HashMap<String, File>();
		JavaClassCoupling.listClassNamesInProject(new File(TestCommon.TEST_SRC_DIRECTORY), classInFile);
		List<String> actualRefClsLst = JavaClassCoupling.referencedClasses("Position2D", classInFile);
		List<String> expectedRefClsLst = new LinkedList<String>();
		expectedRefClsLst.add("Point2D");
		assertEquals(expectedRefClsLst, actualRefClsLst);
	}

	@Test
	public void testGetAverageCoupling() throws Exception {
		Double actualAvgCoupling = JavaClassCoupling.getAverageCoupling(new File(TestCommon.TEST_SRC_DIRECTORY));
		Double expectedAvgCoupling = 3.0 / 5.0;
		assertEquals(expectedAvgCoupling, actualAvgCoupling, TestCommon.DELTA);
	}
}
