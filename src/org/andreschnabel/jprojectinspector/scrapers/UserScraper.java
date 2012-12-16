package org.andreschnabel.jprojectinspector.scrapers;

import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.StringPair;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

public class UserScraper {
	
	public class UserData {
		public String name;
		public String joinDate;
		public List<Project> projects;
		public int numStarredProjects;
		public List<String> followers;
		public List<String> following;
	}
	
	public UserData scrapeUser(String name) throws Exception {		
		String mainHtml = Helpers.loadUrlIntoStr("https://github.com/" + name);
		String followingHtml = Helpers.loadUrlIntoStr("https://github.com/"+name+"/following");
		String followersHtml = Helpers.loadUrlIntoStr("https://github.com/"+name+"/followers");
		UserData ud = new UserData();
		ud.name = name;
		ud.joinDate = scrapeJoinDate(mainHtml);
		ud.projects = scrapeProjects(mainHtml);
		ud.numStarredProjects = scrapeNumStarredProjects(mainHtml);
		ud.followers = scrapeFollowers(followersHtml, name);
		ud.following = scrapeFollowing(followingHtml, name);
		return ud;
	}
	
	private static List<String> scrapeNames(String htmlStr) throws Exception {
		String regex = "<a href=\"/([a-zA-Z0-9]*)\">[a-zA-Z0-9]*</a>";
		List<String> usernames = RegexHelpers.batchMatchOneGroup(regex, htmlStr);
		return usernames;
	}
	
	private List<String> scrapeFollowing(String htmlStr, String name) throws Exception {
		List<String> names = scrapeNames(htmlStr);
		names.remove(name);
		return names;
	}

	private List<String> scrapeFollowers(String htmlStr, String name) throws Exception {
		List<String> names = scrapeNames(htmlStr);
		names.remove(name);
		return names;
	}

	private int scrapeNumStarredProjects(String htmlStr) throws Exception {
		htmlStr = StringHelpers.removeAllWhitespace(htmlStr);
		String regex = "<li><strong>([0-9]*)</strong><span>starred</span>";
		String resStr = RegexHelpers.batchMatchOneGroup(regex, htmlStr).get(0);
		return Integer.valueOf(resStr);
	}

	private List<Project> scrapeProjects(String htmlStr) {
		htmlStr = StringHelpers.removeAllWhitespace(htmlStr);
		List<Project> projects = new LinkedList<Project>();
		
		String regex = "<spanclass=\"mega-iconmega-icon-repo-forked\"></span>"
							+"<ahref=\"/([a-zA-Z0-9]*)/([a-zA-Z0-9]*)\">[a-zA-Z0-9]*</a>";
		List<StringPair> projNames = RegexHelpers.batchMatchTwoGroups(regex, htmlStr);
		
		for(StringPair projName : projNames) {
			projects.add(new Project(projName.first, projName.second));
		}
		
		return projects;
	}

	private String scrapeJoinDate(String htmlStr) throws Exception {
		htmlStr = StringHelpers.removeWhitespace(htmlStr);
		String regex = "<span class=\"join-date\">([A-Za-z0-9, ]*)</span>";
		return RegexHelpers.batchMatchOneGroup(regex, htmlStr).get(0);
	}

}
