package org.andreschnabel.jprojectinspector.tests.online.scrapers;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.PopularRepoScraper;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PopularRepoScraperTest {

	@Test
	public void testScrapeRepos() throws Exception {
		List<Project> repos = PopularRepoScraper.scrapeRepos("<li class=\"public source\">\n" +
				"  <ul class=\"repo-stats\">\n" +
				"      <li>JavaScript</li>\n" +
				"    <li class=\"stargazers\">\n" +
				"      <a href=\"/twitter/bootstrap/stargazers\" title=\"Stargazers\" class=\"\">\n" +
				"        <span class=\"mini-icon mini-icon-star\"></span> 48,318\n" +
				"      </a>\n" +
				"    </li>\n" +
				"    <li class=\"forks\">\n" +
				"      <a href=\"/twitter/bootstrap/network\" title=\"Forks\">\n" +
				"        <span class=\"mini-icon mini-icon-fork\"></span> 14,455\n" +
				"      </a>\n" +
				"    </li>\n" +
				"  </ul>\n" +
				"\n" +
				"  <h3>\n" +
				"    <span class=\"mega-icon mega-icon-public-repo\"></span>\n" +
				"    <a href=\"/twitter/bootstrap\">bootstrap</a>\n" +
				"  </h3>\n" +
				"\n" +
				"\n" +
				"    <div class=\"body\">\n" +
				"        <p class=\"description\">\n" +
				"          Sleek, intuitive, and powerful front-end framework for faster and easier web development.\n" +
				"        </p>\n" +
				"\n" +
				"        <p class=\"updated-at\">Last updated <time class=\"js-relative-date\" datetime=\"2013-04-11T20:18:31-07:00\" title=\"2013-04-11 20:18:31\">April 11, 2013</time></p>\n" +
				"\n" +
				"      <div class=\"participation-graph disabled\">\n" +
				"        <canvas class=\"bars\" data-color-all=\"#F5F5F5\" data-color-owner=\"#DFDFDF\" data-source=\"/twitter/bootstrap/graphs/owner_participation\" height=\"80\" width=\"640\"></canvas>\n" +
				"      </div>\n" +
				"    </div><!-- /.body -->\n" +
				"</li>");
		AssertHelpers.arrayEqualsLstOrderInsensitive(new Project[] {new Project("twitter", "bootstrap")}, repos);
	}

	@Test
	public void testScrapePopularForked() throws Exception {
		List<Project> popForked = PopularRepoScraper.scrapePopularForked();
		AssertHelpers.listNotEmpty(popForked);
		Assert.assertEquals(new Project("twitter", "bootstrap"), popForked.get(0));
	}

	@Test
	public void testScrapePopularStarred() throws Exception {
		List<Project> popStarred = PopularRepoScraper.scrapePopularStarred();
		AssertHelpers.listNotEmpty(popStarred);
		Assert.assertEquals(new Project("twitter", "bootstrap"), popStarred.get(0));
	}

	@Test
	public void testScrapeInteresting() throws Exception {
		List<Project> interesting = PopularRepoScraper.scrapeInteresting();
		AssertHelpers.listNotEmpty(interesting);
		Assert.assertEquals(new Project("twitter", "bootstrap"), interesting.get(0));
	}

}
