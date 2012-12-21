package org.andreschnabel.jprojectinspector.utilities;

import org.andreschnabel.jprojectinspector.model.Project;

public class TestCommon {
	public static final String TEST_SRC_DIRECTORY = "testdata";
	public static final String TEST_SRC_FILENAME = "testdata/Points.java";
	public static final String TEST_SRC_TEST_FILENAME = "testdata/PointsTest.java";
	public static final Project THIS_PROJECT = new Project("0x17", "JProjectInspector");
	
	public static final float DELTA = 0.00001f;	
}
