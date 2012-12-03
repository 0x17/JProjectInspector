package org.andreschnabel.jprojectinspector.testprevalence;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.Helpers;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

public class JavaProjectCollector {
	
	private RepositoryService repoService;

	public JavaProjectCollector() throws Exception {
		GitHubClient client = new GitHubClient();
		String n = Helpers.prompt("Name");
		String p = Helpers.prompt("Password");
		client.setCredentials(n, p);
		repoService = new RepositoryService(client);
	}
	
	public List<Project> collectProjects() throws Exception {
		List<Project> result = new LinkedList<Project>();
		//result.add(new Project("0x17", "ProjectInspector"));
		
		/*String reposStr2 = Helpers.loadUrlIntoStr("https://api.github.com/repositories");
		System.out.println(reposStr2);
		System.exit(0);*/
		
		String reposStr = Helpers.readEntireFile(new File("test.json"));
		Pattern p = Pattern.compile("\"html_url\": \"(.*)\"");
		Pattern projUriPattern = Pattern.compile("https://github.com/(.*)/(.*)");
		Matcher m = p.matcher(reposStr);
		while(m.find()) {
			String uri = m.group(1);
			Matcher uriMatcher = projUriPattern.matcher(uri);			
			if(uriMatcher.matches()) {
				String owner = uriMatcher.group(1);
				String repoName = uriMatcher.group(2);
				Project proj = new Project(owner, repoName);
				System.out.println("Found project = " + proj);
				if(isJavaProject(proj))	 {
					System.out.println("Fancy... its Java!");
					result.add(proj);
				}
			}
		}
		
		return result;
	}
	
	private boolean isJavaProject(Project p) throws Exception {
		Repository repo = repoService.getRepository(p.owner, p.repoName);
		Map<String, Long> langs = repoService.getLanguages(repo);
		return langs.containsKey("Java") && langs.get("Java") > 0;
	}
	
}


