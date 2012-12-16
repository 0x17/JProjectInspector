package org.andreschnabel.jprojectinspector.runners;

import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.PopularRepoScraper;

public class PopularRepoScraperRunner {
	
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
