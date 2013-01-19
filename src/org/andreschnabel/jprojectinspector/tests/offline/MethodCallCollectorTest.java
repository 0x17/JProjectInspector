package org.andreschnabel.jprojectinspector.tests.offline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.List;

import org.andreschnabel.jprojectinspector.model.coverage.Method;
import org.andreschnabel.jprojectinspector.model.coverage.MethodIndex;
import org.andreschnabel.jprojectinspector.parsers.coverage.MethodCallCollector;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Test;

public class MethodCallCollectorTest {

	@Test
	public void testCollectMethodCallsForSrcStr() throws Exception {
		MethodIndex mi = new MethodIndex();
		String srcStr = "float x = obj.sin(12f);";
		Method m = new Method("float", "somepackage", "SomeClass", "sin", new String[]{"float"});
		mi.methods.add(m);
		List<Method> calls = MethodCallCollector.collectMethodCallsForSrcStr(srcStr, mi);
		assertEquals(1, calls.size());
		assertEquals(m, calls.get(0));
	}

	@Test
	public void testTryFindMatch() {
		Method m = new Method("int", "somepackage", "SomeClass", "sin", new String[]{"float"});
		MethodIndex mi = new MethodIndex();
		mi.methods.add(m);
		Method match = MethodCallCollector.tryFindMatch("obj", "sin", "float x", mi);
		assertNotNull(match);
		assertEquals(m, match);
	}

	@Test
	public void testCollectMethodCallsForTestFiles() {
	}

	@Test
	public void testCollectTestFiles() throws Exception {
		File[] expFiles = new File[]{
				new File("testdata/PointsTest.java")
		};
		List<File> actualFiles = MethodCallCollector.collectTestFiles(new File("testdata"));
		AssertHelpers.arrayEqualsLstOrderInsensitive(expFiles, actualFiles);
	}

}
