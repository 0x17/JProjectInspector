package org.andreschnabel.jprojectinspector.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.UserData;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.util.LinkedList;
import java.util.List;

/**
 * Scrape Daten von Nutzer auf GitHub.
 */
public class UserScraper {

	public static UserData scrapeUser(String name) throws Exception {
		String mainHtml = Helpers.loadUrlIntoStr("https://github.com/" + name);
		String followingHtml = Helpers.loadUrlIntoStr("https://github.com/" + name + "/following");
		String followersHtml = Helpers.loadUrlIntoStr("https://github.com/" + name + "/followers");
		String reposHtml = Helpers.loadUrlIntoStr("https://github.com/" + name + "?tab=repositories");
		UserData ud = new UserData();
		ud.name = name;
		ud.realName = scrapeRealName(mainHtml);
		ud.joinDate = scrapeJoinDate(mainHtml);
		ud.projects = scrapeProjects(reposHtml);
		ud.numStarredProjects = scrapeNumStarredProjects(mainHtml);
		ud.followers = scrapeFollowers(followersHtml, name);
		ud.following = scrapeFollowing(followingHtml, name);
		return ud;
	}

	private static String scrapeRealName(String mainHtml) {
		String regex = "<span itemprop=\"name\">(.+?)</span>";
		List<String> matches = RegexHelpers.batchMatchOneGroup(regex, mainHtml);
		if(matches.size() == 1) {
			return matches.get(0);
		}
		return null;
	}

	public static List<String> scrapeNames(String htmlStr) throws Exception {
		String regex = "<a href=\"/([a-zA-Z0-9]*)\">[a-zA-Z0-9]*</a>";
		return RegexHelpers.batchMatchOneGroup(regex, htmlStr);
	}

	public static List<String> scrapeFollowing(String htmlStr, String name) throws Exception {
		List<String> names = scrapeNames(htmlStr);
		names.remove(name);
		return names;
	}

	public static List<String> scrapeFollowers(String htmlStr, String name) throws Exception {
		List<String> names = scrapeNames(htmlStr);
		names.remove(name);
		return names;
	}

	public static int scrapeNumStarredProjects(String htmlStr) throws Exception {
		htmlStr = StringHelpers.removeAllWhitespace(htmlStr);
		String regex = "([0-9]+)</strong>\\s*?<span>starred</span>";
		List<String> matches = RegexHelpers.batchMatchOneGroup(regex, htmlStr);
		if(matches.isEmpty()) {
			return 0;
		}
		String resStr = matches.get(0);
		return Integer.valueOf(resStr);
	}

	public static List<Project> scrapeProjects(String htmlStr) {
		List<Project> projects = new LinkedList<Project>();

		String regex = "<a href=\"/([a-zA-Z0-9\\-\\_]+)/([a-zA-Z0-9\\-\\_]+)/network\"";
		List<String[]> projNames = RegexHelpers.batchMatch(regex, htmlStr);

		for(String[] projName : projNames) {
			projects.add(new Project(projName[0], projName[1]));
		}

		return projects;
	}

	public static String scrapeJoinDate(String htmlStr) throws Exception {
		htmlStr = StringHelpers.removeWhitespace(htmlStr);
		String regex = "<span class=\"join-date\">([A-Za-z0-9, ]*)</span>";
		return RegexHelpers.batchMatchOneGroup(regex, htmlStr).get(0);
	}

	public static List<Project> scrapeProjectsOfUser(String user) throws Exception {
		String reposHtml = Helpers.loadHTMLUrlIntoStrRetry("https://github.com/" + user + "?tab=repositories", 10);
		return scrapeProjects(reposHtml);
	}

	public static List<Project> scrapeProjectsOfUsers(List<String> users) throws Exception {
		List<Project> projects = new LinkedList<Project>();
		for(String user : users) {
			List<Project> up = scrapeProjectsOfUser(user);
			projects.addAll(up);
		}
		return projects;
	}
}
