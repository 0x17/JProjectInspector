package org.andreschnabel.jprojectinspector.testprevalence;

import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.Helpers;

import com.google.gson.Gson;

public class ProjectCollector {
	@SuppressWarnings("unused")
	private class Repository {		
		public String type, owner, username, created, created_at, description, pushed, name, language;
		public int followers, forks, size, watchers;
		public boolean fork;
	}
	
	private class RepoSearch {
		public List<Repository> repositories;
	}
	
	public class ProjectList {
		public String keyword;
		public List<Project> projects;
		public ProjectList(String keyword, List<Project> projects) {
			this.keyword = keyword;
			this.projects = projects;
		}
	}
	
	public ProjectList collectProjects(String keyword, int numPages) throws Exception {
		List<Project> result = new LinkedList<Project>();
		Gson gson = new Gson();
		
		System.out.println("Collecting non-forked java projects for keyword " + keyword);
		
		for(int curPage=1; curPage<=numPages; curPage++) {
			System.out.println("Page " + curPage);
			String repoSearchUri = "https://api.github.com/legacy/repos/search/" + keyword + "?start_page=" + curPage;
			String reposStr = Helpers.loadUrlIntoStr(repoSearchUri);
			
			RepoSearch search = gson.fromJson(reposStr, RepoSearch.class);			
			if(search.repositories.size() == 0) break;
			
			for(Repository r : search.repositories) {
				if(r.language != null && isLanguageSupported(r.language) && r.fork == false) {
					Project np = new Project(r.owner, r.name);
					if(!result.contains(np)) {// no duplicates
						result.add(np);
						System.out.println("Added " + np.toId());
					}
				}
			}
		}
		
		return new ProjectList(keyword, result);
	}
	
	private boolean isLanguageSupported(String lang) {
		return Helpers.equalsOneOf(lang, UnitTestDetector.getSupportedLangs());
	}

	public ProjectList collectProjects(String[] keywords, int numPages) throws Exception {
		StringBuilder keywordStr = new StringBuilder();
		for(int i=0; i<keywords.length; i++)
			keywordStr.append((i == 0 ? "" : ",") + keywords[i]);
		
		List<Project> flatLst = new LinkedList<Project>();
		for(int i=0; i<keywords.length; i++) {
			ProjectList lst = collectProjects(keywords[i], numPages);
			flatLst.addAll(lst.projects);			
		}
		
		return new ProjectList(keywordStr.toString(), flatLst);
	}
	
}


