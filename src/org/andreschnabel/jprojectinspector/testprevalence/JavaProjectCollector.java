package org.andreschnabel.jprojectinspector.testprevalence;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.andreschnabel.jprojectinspector.Helpers;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

import com.google.gson.Gson;

public class JavaProjectCollector {
	
	private RepositoryService repoService;

	public JavaProjectCollector() throws Exception {
		GitHubClient client = new GitHubClient();
		String n = Helpers.prompt("Name");
		String p = Helpers.prompt("Password");
		client.setCredentials(n, p);
		repoService = new RepositoryService(client);
	}
	
	private class Repository {
		public String type, owner, username, created, created_at, description, pushed, name, language;
		public int followers, forks, size, watchers;
		public boolean fork;
	}
	
	private class RepoSearch {
		public List<Repository> repositories;
	}
	
	public List<Project> collectProjects(String keyword, int numPages) throws Exception {
		List<Project> result = new LinkedList<Project>();
		Gson gson = new Gson();
		
		for(int curPage=1; curPage<=numPages; curPage++) {
			String repoSearchUri = "https://api.github.com/legacy/repos/search/" + keyword + "?page=" + curPage;
			String reposStr = Helpers.loadUrlIntoStr(repoSearchUri);
			
			//String reposStr = Helpers.readEntireFile(new File("test.json"));
			
			RepoSearch search = gson.fromJson(reposStr, RepoSearch.class);			
			if(search.repositories.size() == 0) break;
			
			for(Repository r : search.repositories) {
				if(r.language != null && r.language.equals("Java") && r.fork == false) {
					result.add(new Project(r.owner, r.name));
				}
			}
		}
		
		return result;
	}
	
	/*private boolean isJavaProject(Project p) throws Exception {
		org.eclipse.egit.github.core.Repository repo = repoService.getRepository(p.owner, p.repoName);
		Map<String, Long> langs = repoService.getLanguages(repo);
		return langs.containsKey("Java") && langs.get("Java") > 0;
	}*/
	
}


