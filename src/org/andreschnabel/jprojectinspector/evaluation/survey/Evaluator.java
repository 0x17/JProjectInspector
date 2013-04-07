package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class Evaluator {

	public static void countCorrectPredictions() throws Exception {
		PredictionMethods measures1 = new PredictionMethods() {
			@Override
			public String getName() { return "method1"; }
			@Override
			public float testEffortPredictionMeasure(ProjectMetrics m) {
				return (float) m.testLinesOfCode / (m.linesOfCode + 1.0f);
			}
			@Override
			public float bugCountPredictionMeasure(ProjectMetrics m) {
				return 1.0f / (m.testLinesOfCode + 1.0f) * (float) m.linesOfCode;
				//return (m.linesOfCode - m.testLinesOfCode) / (m.linesOfCode + 1.0f);
			}
		};
		countCorrectPredictions(measures1);

		PredictionMethods measures2 = new PredictionMethods() {
			@Override
			public String getName() { return "method2"; }
			@Override
			public float testEffortPredictionMeasure(ProjectMetrics m) {
				return m.testLinesOfCode;
			}
			@Override
			public float bugCountPredictionMeasure(ProjectMetrics m) {
				return 1.0f / (m.testLinesOfCode + 1.0f);
			}
		};
		countCorrectPredictions(measures2);

		PredictionMethods measures3 = new PredictionMethods() {
			@Override
			public String getName() { return "method1"; }
			@Override
			public float testEffortPredictionMeasure(ProjectMetrics m) {
				if(m.testLinesOfCode == 0)
					return (float) m.testLinesOfCode / (m.linesOfCode + 1.0f);
				else
					return m.testLinesOfCode;
			}
			@Override
			public float bugCountPredictionMeasure(ProjectMetrics m) {
				if(m.testLinesOfCode == 0)
					return 1.0f / (m.testLinesOfCode + 1.0f) * (float) m.linesOfCode;
				else
					return 1.0f / m.testLinesOfCode;
			}
		};
		countCorrectPredictions(measures3);
	}

	public interface PredictionMethods {
		public String getName();
		public float testEffortPredictionMeasure(ProjectMetrics m);
		public float bugCountPredictionMeasure(ProjectMetrics m);
	}

	enum PredictionTypes {
		BugCount,
		TestEffort
	}

	public static void countCorrectPredictions(final PredictionMethods predMethods) throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst) XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));
		final List<ProjectMetrics> pml = metrics.projectMetrics;

		int teCorrect = 0;
		int bcCorrect = 0;
		int total = 0;

		for(ResponseProjects rp : rpl.responseProjs) {
			if(rp.user == null) continue;

			List<Project> projs = rp.toProjectList();

			if(skipInvalidProjects(pml, projs)) continue;

			total += 2;

			List<Float> bcPredVals = calcPredictionValues(predMethods, PredictionTypes.BugCount, pml, projs);

			int highestPredIx = getHighestPredIndex(projs, bcPredVals);
			if(rp.highestBugCount.equals(projs.get(highestPredIx).repoName))
				bcCorrect++;

			int lowestPredIx = getLowestPredictionIndex(projs, bcPredVals);
			if(rp.lowestBugCount.equals(projs.get(lowestPredIx).repoName))
				bcCorrect++;

			List<Float> tePredVals = calcPredictionValues(predMethods, PredictionTypes.TestEffort, pml, projs);

			highestPredIx = getHighestPredIndex(projs, tePredVals);
			if(rp.mostTested.equals(projs.get(highestPredIx).repoName))
				teCorrect++;

			lowestPredIx = getLowestPredictionIndex(projs, tePredVals);
			if(rp.leastTested.equals(projs.get(lowestPredIx).repoName))
				teCorrect++;
		}

		Helpers.log("Method = " + predMethods.getName());
		float percentCorrect = teCorrect / (float)total * 100.0f;
		Helpers.log("Correct test effort predictions = " + teCorrect + " of " + total + " -> " + percentCorrect + "%");
		percentCorrect = bcCorrect / (float)total * 100.0f;
		Helpers.log("Correct bug count predictions = " + bcCorrect + " of " + total + " -> " + percentCorrect + "%");
	}

	private static int getLowestPredictionIndex(List<Project> projs, List<Float> predVals) {
		int lowestPredIx = 0;
		float min = Integer.MAX_VALUE;
		for(int i=0; i<projs.size(); i++) {
			if(predVals.get(i) < min) {
				min = predVals.get(i);
				lowestPredIx = i;
			}
		}
		return lowestPredIx;
	}

	private static int getHighestPredIndex(List<Project> projs, List<Float> predVals) {
		int highestPredIx = 0;
		float max = Integer.MIN_VALUE;
		for(int i=0; i<projs.size(); i++) {
			if(predVals.get(i) > max) {
				max = predVals.get(i);
				highestPredIx = i;
			}
		}
		return highestPredIx;
	}

	private static List<Float> calcPredictionValues(final PredictionMethods predMethods, final PredictionTypes predType, final List<ProjectMetrics> pml, List<Project> projs) {
		Transform<Project, Float> bcPred = new Transform<Project, Float>() {
			@Override
			public Float invoke(Project p) {
				switch(predType) {
					case BugCount:
						return predMethods.bugCountPredictionMeasure(metricsForProject(p, pml));
					default:
					case TestEffort:
						return predMethods.testEffortPredictionMeasure(metricsForProject(p, pml));
				}
			}
		};
		return ListHelpers.map(bcPred, projs);
	}

	private static boolean skipInvalidProjects(final List<ProjectMetrics> pml, List<Project> projs) {
		Predicate<Project> isInvalid = new Predicate<Project>() {
			@Override
			public boolean invoke(Project p) {
				return metricsForProject(p, pml) == null;
			}
		};
		return ListHelpers.contains(isInvalid, projs);
	}

	public static ProjectMetrics metricsForProject(final Project p, List<ProjectMetrics> pml) {
		Predicate<ProjectMetrics> isProj = new Predicate<ProjectMetrics>() {
			@Override
			public boolean invoke(ProjectMetrics obj) {
				return obj.project.equals(p);
			}
		};
		return ListHelpers.find(isProj, pml);
	}

}
