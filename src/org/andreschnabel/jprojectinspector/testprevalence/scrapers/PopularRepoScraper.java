package org.andreschnabel.jprojectinspector.testprevalence.scrapers;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.andreschnabel.jprojectinspector.Helpers;
import org.andreschnabel.jprojectinspector.testprevalence.model.Project;

public class PopularRepoScraper {
	
	private final static String POPULAR_FORKED_URL = "https://github.com/popular/forked";
	private final static String POPULAR_STARRED_URL = "https://github.com/popular/starred";
	private final static String INTERESTING_URL = "https://github.com/repositories";
	
	private static List<Project> scrapeRepos(String htmlStr) {
		List<Project> projects = new LinkedList<Project>();
		String regex = "<a href=\"/([a-zA-Z0-9]*)/([a-zA-Z0-9]*)\">[a-zA-Z0-9]*</a>";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(htmlStr);
		
		while(m.find()) {
			if(m.groupCount() == 2) {
				Project np = new Project(m.group(1), m.group(2));
				projects.add(np);
			}
		}
		
		return projects;
	}
		
	public List<Project> scrapePopularForked() throws Exception {
		return scrapeRepos(Helpers.loadUrlIntoStr(POPULAR_FORKED_URL));
	}
	
	public List<Project> scrapePopularStarred() throws Exception {
		return scrapeRepos(Helpers.loadUrlIntoStr(POPULAR_STARRED_URL));
	}
	
	public List<Project> scrapeInteresting() throws Exception {
		return scrapeRepos(Helpers.loadUrlIntoStr(INTERESTING_URL));
	}

	public static void main(String[] args) throws Exception {
		PopularRepoScraper prs = new PopularRepoScraper();
		List<Project>	popularForked = prs.scrapePopularForked(),
							popularStarred = prs.scrapePopularStarred(),
							interesting = prs.scrapeInteresting();
		System.out.println("Popular forked:");
		printProjLst(popularForked);
		System.out.println("Popular starred:");
		printProjLst(popularStarred);
		System.out.println("Interesting:");
		printProjLst(interesting);
	}
	
	private static void printProjLst(List<Project> lst) {
		for(Project p : lst) {
			System.out.println(p);
		}
	}

}
