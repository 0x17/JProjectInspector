package org.andreschnabel.jprojectinspector;

import java.io.File;

import org.andreschnabel.jprojectinspector.model.Project;

public final class TestCommon {
	public static final String MAIN_DIR = System.getProperty("user.dir"); 
	public static final String TEST_SRC_DIRECTORY = "testdata";
	public static final String TEST_SRC_FILENAME = "testdata"+File.separator+"Points.java";
	public static final String TEST_SRC_TEST_FILENAME = "testdata"+File.separator+"PointsTest.java";
	public static final Project THIS_PROJECT = new Project("0x17", "JProjectInspector");

	public static final float DELTA = 0.00001f;
}
