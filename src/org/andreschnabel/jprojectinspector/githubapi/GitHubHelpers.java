package org.andreschnabel.jprojectinspector.githubapi;

import org.andreschnabel.pecker.helpers.Helpers;
import org.eclipse.egit.github.core.client.GitHubClient;

/**
 * Hilfsfunktionen für die GitHub-Web-API.
 */
public class GitHubHelpers {

	private static GitHubClient ghc = null;

	public static GitHubClient authenticate() throws Exception {
		if(ghc == null) {
			ghc = new GitHubClient();
			String user = Helpers.prompt("Username");
			String pw = Helpers.promptPw("Password");
			ghc.setCredentials(user, pw);
		}

		return ghc;
	}

}
