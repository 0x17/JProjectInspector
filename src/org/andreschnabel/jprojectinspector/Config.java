package org.andreschnabel.jprojectinspector;

import org.andreschnabel.pecker.helpers.FileHelpers;
import org.andreschnabel.pecker.helpers.Helpers;

import java.io.File;
import java.util.Map;

/**
 * Konfiguration von JProjectInspector.
 */
public final class Config {
	private static final String CONFIG_FILENAME = "jprojinspector.cfg";

	/**
	 * URL von GitHub-Seite.
	 */
	public static String BASE_URL = "https://github.com/";
	/**
	 * Verzeichnis zum temporären Klonen von Repositories für die Bestimmung von Offline-Metriken.
	 */
	public static String DEST_BASE;
	/**
	 * Pfad zur ausführbaren Binärdatei von Git.
	 */
	public static String GIT_PATH;
	/**
	 * Pfad zum Perl-Interpreter.
	 */
	public static String PERL_PATH;
	/**
	 * Pfad zum Cloc-Perl-Skript.
	 */
	public static String CLOC_PATH;

	static {
		try {
			initializePathsFailed();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Versuche Pfade zu initialisieren.
	 * @return true, gdw. etwas schief gelaufen ist.
	 * @throws Exception
	 */
	public static boolean initializePathsFailed() throws Exception {
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

			DEST_BASE = envVars.get("TMP") + File.separator;
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

	/**
	 * Speicher gewählte Einstellungen.
	 * @throws Exception
	 */
	public static void persist() throws Exception {
		persist(new File(Config.CONFIG_FILENAME));
	}

	/**
	 * Speicher gewählte Einstellungen in bestimmte Datei.
	 * @param cfgFile Name der Einstellungsdatei.
	 * @throws Exception
	 */
	public static void persist(File cfgFile) throws Exception {
		FileHelpers.writeStrToFile(BASE_URL+"\n"+DEST_BASE+"\n"+GIT_PATH+"\n"+PERL_PATH+"\n"+CLOC_PATH, cfgFile);
	}

	/**
	 * Bestimme absoluten Pfad zu Cloc.
	 * @return absoluter Pfad zu Cloc.
	 */
	public static String absoluteClocPath() {
		return new File(CLOC_PATH).getAbsolutePath();
	}
}
