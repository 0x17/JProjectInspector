package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.eclipse.egit.github.core.client.GitHubClient;

public class GitHelpers {
	
	private static GitHubClient ghc = null;
	
	public static GitHubClient authenticate() throws Exception {
		// Usually I try to avoid side effects but here caching is very helpful!
		if(ghc == null) {
			ghc = new GitHubClient();
			String user = Helpers.prompt("Username");
			String pw = Helpers.promptPw("Password");
			ghc.setCredentials(user, pw);
		}
		
		return ghc;
	}

}
