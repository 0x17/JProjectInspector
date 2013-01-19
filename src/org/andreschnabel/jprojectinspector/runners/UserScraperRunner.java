package org.andreschnabel.jprojectinspector.runners;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper.UserData;

public class UserScraperRunner {

	public static void main(String[] args) throws Exception {
		UserScraper us = new UserScraper();
		UserData ud = us.scrapeUser("badlogic");
		System.out.println("Name: " + ud.name);
		System.out.println("Join date: " + ud.joinDate);
		System.out.println("Num starred projs: " + ud.numStarredProjects);
		System.out.println("Projects:");
		for(Project p : ud.projects)
			System.out.println(p);
		System.out.println("Followers:");
		for(String u : ud.followers)
			System.out.println(u);
		System.out.println("Following:");
		for(String u : ud.following)
			System.out.println(u);
	}

}
