package org.andreschnabel.jprojectinspector;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

public final class Config {
	public static String DEST_BASE_UNIX = "/tmp/";
	public static String DEST_BASE_WIN = "E:\\Temp\\";
	public static String DEST_BASE = Helpers.runningOnUnix() ? DEST_BASE_UNIX : DEST_BASE_WIN;
	public static String BASE_URL = "https://github.com/";
	public static String GIT_PATH;
	public static String CLOC_PATH;

	static {
		if(Helpers.runningOnUnix()) {
			GIT_PATH = "git";
			CLOC_PATH = "cloc";
		} else {
			GIT_PATH = "git";
			CLOC_PATH = "cloc";
		}
	}
}
