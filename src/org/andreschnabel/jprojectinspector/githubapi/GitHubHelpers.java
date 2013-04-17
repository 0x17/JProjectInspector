package org.andreschnabel.jprojectinspector.githubapi;

import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.eclipse.egit.github.core.client.GitHubClient;

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
