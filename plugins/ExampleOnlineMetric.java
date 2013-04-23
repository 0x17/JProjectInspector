import org.andreschnabel.jprojectinspector.metrics.IOnlineMetric;

import org.andreschnabel.jprojectinspector.model.Project;

import org.andreschnabel.jprojectinspector.scrapers.UserScraper;

public class ExampleOnlineMetric implements IOnlineMetric {

	public String getName() { return "exonmetric"; }
	public String getDescription() { return "Number of followers of user maintaining project. Example metric implemented as plugin."; }
	
	public Category getCategory() { return Category.Scraping; }

	public float measure(Project p) throws Exception {
		return UserScraper.scrapeUser(p.owner).followers.size();
	}

}