package org.andreschnabel.jprojectinspector.evaluation.runners;

import org.andreschnabel.jprojectinspector.evaluation.MetricsCollector;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IndexedTransform;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CollectMetricsForProjectsInResponsesRunner {

	public static void main(String[] args) throws Exception {
		List<ProjectWithResults> projectWithResultsList = collectMetrics(Arrays.asList(new String[]{"loc"}));
		ProjectWithResults.toCsv(projectWithResultsList).save(new File("MetricsForProjects.csv"));
	}

	public static List<ProjectWithResults> collectMetrics(final List<String> metricNames) throws Exception {
		final String[] metricNamesArray = metricNames.toArray(new String[]{});
		final List<Project> projects = Project.projectListFromCsv(CsvHelpers.parseCsv("data/KandidatenProjekteUmfrage1.csv"));
		List<Double[]> resultsList = Func.map(new Transform<Project, Double[]>() {
			@Override
			public Double[] invoke(Project p) {
				return MetricsCollector.gatherMetricsForProject(metricNames, p);
			}
		},projects);
		return Func.mapi(new IndexedTransform<Double[], ProjectWithResults>() {
			@Override
			public ProjectWithResults invoke(int i, Double[] results) {
				return new ProjectWithResults(projects.get(i), metricNamesArray, results);
			}
		}, resultsList);
	}

}
