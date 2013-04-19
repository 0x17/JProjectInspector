package org.andreschnabel.jprojectinspector;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class Config {
	private static final String CONFIG_FILENAME = "jprojinspector.cfg";

	public static String BASE_URL = "https://github.com/";
	public static String DEST_BASE;
	public static String GIT_PATH;
	public static String PERL_PATH;
	public static String CLOC_PATH;

	static {
		try {
			initializePaths();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean initializePaths() throws Exception {
		if(FileHelpers.exists(CONFIG_FILENAME)) {
			loadSettingsFromDisk();
			return false;
		}

		CLOC_PATH = "libs" + File.separator + "cloc.pl";

		Map<String, String>  envVars = System.getenv();

		if(!Helpers.runningOnUnix()) {
			String pathVar = envVars.get("Path");
			String[] pathEntries = pathVar.split(";");
			for(String entry : pathEntries) {
				if(entry.toLowerCase().contains("git")) {
					GIT_PATH = entry + File.separator + "git" + Helpers.executableExtension();
				} else if(entry.toLowerCase().contains("perl")) {
					PERL_PATH = entry + File.separator +"perl" + Helpers.executableExtension();
				}
			}

			DEST_BASE = envVars.get("TMP");
		} else {
			CLOC_PATH = "/usr/local/bin/cloc";
			GIT_PATH = Helpers.unixType("git");
			PERL_PATH = Helpers.unixType("perl");
			DEST_BASE = "/tmp/";
		}

		return GIT_PATH == null || PERL_PATH == null || DEST_BASE == null;
	}

	private static void loadSettingsFromDisk() throws Exception {
		String[] lines = FileHelpers.readEntireFile(new File(CONFIG_FILENAME)).split("\n");
		BASE_URL = lines[0];
		DEST_BASE = lines[1];
		GIT_PATH = lines[2];
		PERL_PATH = lines[3];
		CLOC_PATH = lines[4];
	}

	public static void persist() throws IOException {
		FileHelpers.writeStrToFile(BASE_URL+"\n"+DEST_BASE+"\n"+GIT_PATH+"\n"+PERL_PATH+"\n"+CLOC_PATH, CONFIG_FILENAME);
	}
}
