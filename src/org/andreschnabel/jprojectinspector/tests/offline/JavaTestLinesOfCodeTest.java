package org.andreschnabel.jprojectinspector.tests.offline;

import org.andreschnabel.jprojectinspector.tests.TestCommon;
import org.andreschnabel.jprojectinspector.metrics.javaspecific.JavaTestLinesOfCode;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class JavaTestLinesOfCodeTest {

	@Test
	public void testCountTestLocOfDir() throws Exception {
		int locOfDir = JavaTestLinesOfCode.countJavaTestLocOfDir(new File(TestCommon.TEST_SRC_DIRECTORY));
		assertEquals(11, locOfDir);
	}

}
