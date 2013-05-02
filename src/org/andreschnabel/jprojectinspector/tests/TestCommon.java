package org.andreschnabel.jprojectinspector.tests;

import org.andreschnabel.jprojectinspector.model.Project;

import java.io.File;

public final class TestCommon {
	public static final String MAIN_DIR = System.getProperty("user.dir"); 
	public static final String TEST_SRC_DIRECTORY = "dummydata";
	public static final String TEST_SRC_FILENAME = "dummydata"+File.separator+"Points.java";
	public static final String TEST_SRC_TEST_FILENAME = "dummydata"+File.separator+"PointsTest.java";
	public static final Project THIS_PROJECT = new Project("0x17", "JProjectInspector");

	public static final double DELTA = 0.00001f;
}
