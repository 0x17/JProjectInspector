import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;

import org.andreschnabel.jprojectinspector.model.Project;

import org.andreschnabel.jprojectinspector.scrapers.UserScraper;

public class ExampleOnlineMetric implements OnlineMetric {

	public String getName() { return "exoffmetric"; }
	public String getDescription() { return "Number of followers of user maintaining project. Example metric implemented as plugin."; }

	public float measure(Project p) throws Exception {
		return UserScraper.scrapeUser(p.owner).followers.size();
	}

}