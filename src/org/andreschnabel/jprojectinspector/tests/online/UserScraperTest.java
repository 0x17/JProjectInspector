package org.andreschnabel.jprojectinspector.tests.online;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.UserData;
import org.andreschnabel.jprojectinspector.scrapers.UserScraper;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

public class UserScraperTest {
	@Test
	public void testScrapeUser() throws Exception {
		Project[] projs = new Project[] {
			new Project("0x17", "KosuFSharp"),
			new Project("0x17", "JProjectInspector"),
			new Project("0x17", "KCImageCollector"),
			new Project("0x17", "DeathJam"),
			new Project("0x17", "UnfollowDetectorDroid")
		};

		String[] followers = new String[] {"uniphil", "bradjonesca", "flgr", "jlnr"};
		String[] following = new String[] {"chrisforbes", "Spaxe", "stuarthalloway", "ntherning", "flgr", "renaudbedard", "jlnr", "unclebob", "exDreamDuck", "seanpaultaylor", "badlogic", "rbecher", "lsinger"};

		String name = "0x17";
		String date = "Apr 18, 2012";

		int numStarred = 19;

		UserData ud = UserScraper.scrapeUser(name);

		Assert.assertEquals(numStarred, ud.numStarredProjects);
		AssertHelpers.arrayEqualsLstOrderInsensitive(followers, ud.followers);
		AssertHelpers.arrayEqualsLstOrderInsensitive(following, ud.following);
		Assert.assertEquals(name, ud.name);
		Assert.assertEquals(date, ud.joinDate);
		AssertHelpers.arrayEqualsLstOrderInsensitive(projs, ud.projects);
	}

	@Test
	public void testScrapeNames() throws Exception {

	}

	@Test
	public void testScrapeFollowing() throws Exception {

	}

	@Test
	public void testScrapeFollowers() throws Exception {

	}

	@Test
	public void testScrapeNumStarredProjects() throws Exception {
		Assert.assertEquals(19, UserScraper.scrapeNumStarredProjects("<li>\n" +
				"              <a href=\"/stars\">\n" +
				"                <strong>19</strong>\n" +
				"                <span>starred</span>\n" +
				"              </a>\n" +
				"          </li>"));
	}

	@Test
	public void testScrapeProjects() throws Exception {
		AssertHelpers.arrayEqualsLstOrderInsensitive(
				new Project[] {new Project("0x17", "KosuFSharp")},
				UserScraper.scrapeProjects("\n" +
						"<li class=\"public source\">\n" +
						"  <ul class=\"repo-stats\">\n" +
						"      <li>F#</li>\n" +
						"    <li class=\"stargazers\">\n" +
						"      <a href=\"/0x17/KosuFSharp/stargazers\" title=\"Stargazers\" class=\"\">\n" +
						"        <span class=\"mini-icon mini-icon-star\"></span> 1\n" +
						"      </a>\n" +
						"    </li>\n" +
						"    <li class=\"forks\">\n" +
						"      <a href=\"/0x17/KosuFSharp/network\" title=\"Forks\">\n" +
						"        <span class=\"mini-icon mini-icon-fork\"></span> 0\n" +
						"      </a>\n" +
						"    </li>\n" +
						"  </ul>\n" +
						"\n" +
						"  <h3>\n" +
						"    <span class=\"mega-icon mega-icon-public-repo\"></span>\n" +
						"    <a href=\"/0x17/KosuFSharp\">KosuFSharp</a>\n" +
						"  </h3>\n" +
						"\n" +
						"\n" +
						"    <div class=\"body\">\n" +
						"        <p class=\"description\">\n" +
						"          Sample game using Kosu written in F#.\n" +
						"        </p>\n" +
						"\n" +
						"        <p class=\"updated-at\">Last updated <time class=\"js-relative-date\" datetime=\"2013-03-27T03:19:35-07:00\" title=\"2013-03-27 03:19:35\">March 27, 2013</time></p>\n" +
						"\n" +
						"      <div class=\"participation-graph disabled\">\n" +
						"        <canvas class=\"bars\" data-color-all=\"#F5F5F5\" data-color-owner=\"#DFDFDF\" data-source=\"/0x17/KosuFSharp/graphs/owner_participation\" height=\"80\" width=\"640\"></canvas>\n" +
						"      </div>\n" +
						"    </div><!-- /.body -->\n" +
						"</li>"));

	}

	@Test
	public void testScrapeJoinDate() throws Exception {

	}
}