package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.evaluation.survey.MetricsCollector;
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
		List<Float[]> resultsList = Func.map(new Transform<Project, Float[]>() {
			@Override
			public Float[] invoke(Project p) {
				return MetricsCollector.gatherMetricsForProject(metricNames, p);
			}
		},projects);
		List<ProjectWithResults> projectWithResultsList = Func.mapi(new IndexedTransform<Float[], ProjectWithResults>() {
			@Override
			public ProjectWithResults invoke(int i, Float[] results) {
				return new ProjectWithResults(projects.get(i), metricNamesArray, results);
			}
		}, resultsList);
		return projectWithResultsList;
	}

}
