package org.andreschnabel.jprojectinspector;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

public final class Config {	
	public final static String DEST_BASE_UNIX = "/tmp/";
	public final static String DEST_BASE_WIN = "D:\\Temp\\";
	public final static String DEST_BASE = Helpers.runningOnUnix() ? DEST_BASE_UNIX : DEST_BASE_WIN;
	public final static String BASE_URL = "https://github.com/";
}
