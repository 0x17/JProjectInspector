package org.andreschnabel.jprojectinspector.utilities;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.pecker.helpers.FileHelpers;
import org.andreschnabel.pecker.helpers.Helpers;
import org.andreschnabel.pecker.helpers.ProcessHelpers;

import java.io.File;

/**
 * Klont Projekte von GitHub.
 */
public final class ProjectDownloader {

	/**
	 * Nur statische Methoden.
	 */
	private ProjectDownloader() {}

	/**
	 * Klone gegebenes Projekt p (owner, repo).
	 * Blockiere solange.
	 * @param p zu klonendes Projekt.
	 * @return Pfad zu geklontem Projekt.
	 * @throws Exception
	 */
	public static File loadProject(Project p) throws Exception {
		if(!isProjectOnline(p)) {
			return null;
		}

		String destPath = Config.DEST_BASE + p.repoName;
		File f = new File(destPath);
		if(f.exists()) {
			return f;
		}

		ProcessHelpers.system(new File(Config.DEST_BASE), Config.GIT_PATH, "clone", "-v", "--progress", p.toUrl(), destPath);

		return f.exists() ? f : null;
	}

	/**
	 * Schaue, ob Projekt verfügbar ist.
	 * @param p zu prüfendes Projekt.
	 * @return true, gdw. Projekt online ist.
	 */
	public static boolean isProjectOnline(Project p) {
		String projHtml;
		try {
			projHtml = Helpers.loadHTMLUrlIntoStr("https://github.com/"+p.owner+"/"+p.repoName);
		} catch(Exception e) {
			return false;
		}
		return projHtml != null && !projHtml.contains("<title>Page not found &middot; GitHub</title>");
	}

	/**
	 * Schau, ob Nutzer verfügbar ist.
	 * @param user login des Nutzers.
	 * @return true, gdw. Nutzer online ist.
	 */
	public static boolean isUserOnline(String user) {
		String userHtml;
		try {
			userHtml = Helpers.loadHTMLUrlIntoStr("https://github.com/"+user);
		} catch(Exception e) {
			return false;
		}
		return userHtml != null && !userHtml.contains("<title>Page not found &middot; GitHub</title>");
	}

	/**
	 * Lösche geklontes Projekt.
	 * @param p geklontes Projekt.
	 * @throws Exception
	 */
	public static void deleteProject(Project p) throws Exception {
		String destPath = Config.DEST_BASE + p.repoName;
		//Helpers.deleteDir(new File(destPath));
		FileHelpersSpecial.rmDir(destPath);
	}
}
