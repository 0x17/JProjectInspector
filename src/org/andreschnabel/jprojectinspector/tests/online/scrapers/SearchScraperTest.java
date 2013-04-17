package org.andreschnabel.jprojectinspector.tests.online.scrapers;

import java.util.List;

import junit.framework.Assert;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.SearchScraper;
import org.junit.Test;

public class SearchScraperTest {
	
	@Test
	public void testSearchByStars() throws Exception {
		String lang = "Java";
		List<Project> results = SearchScraper.searchByStars(lang, 500, 1);
		Assert.assertNotNull(results);
		Assert.assertFalse(results.isEmpty());
	}
	
	@Test
	public void testSearchByForks() throws Exception {
		String lang = "Java";
		List<Project> results = SearchScraper.searchByForks(lang, 500, 1);
		Assert.assertNotNull(results);
		Assert.assertFalse(results.isEmpty());
	}

}
