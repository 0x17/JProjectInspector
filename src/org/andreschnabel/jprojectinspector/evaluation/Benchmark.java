package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;

import java.util.LinkedList;
import java.util.List;

/**
 * Assess quality of test effort estimation resp. bug count estimation prediction equation.
 */
public class Benchmark {

	public static String permutate(String template, List<ProjectWithResults> metricsData, List<ResponseProjects> respProjs, PredictionType mode) throws Exception {
		List<String[]> matches = RegexHelpers.batchMatch("([A-Za-z]+)", template);
		if(matches.isEmpty()) {
			return null;
		}

		String[] vars = new String[matches.size()];

		if(vars.length == 0) {
			return null;
		}

		int i=0;
		for(String[] marr : matches) {
			String match = marr[0];
			if(match.length() > 1) {
				return null;
			}
			vars[i++] = match;
		}

		String winner = null;
		double winnerCorr = 0.0;

		String[] metricNames = metricsData.get(0).getResultHeaders();

		// TODO: Use Func.nestedFor here...

		if(vars.length == 1) {
			for(int j=0; j<metricNames.length; j++) {
					String candidate = template.replace(vars[0], metricNames[j]);

					Quality quality = runBenchmark(new PredictionMethodsFromString(candidate), metricsData, respProjs);

					double corr = 0;

					switch(mode) {
						case BugCount:
							corr = quality.bcWeightedCorrect;
							break;
						case TestEffort:
							corr = quality.teWeightedCorrect;
							break;
					}

					if(corr > winnerCorr) {
						winner = candidate;
						winnerCorr = corr;
					}

			}
		} else if(vars.length == 2) {
			for(int j=0; j<metricNames.length; j++) {
				for(int k=0; k<metricNames.length; k++) {
					if(j == k) {
						continue;
					}

					String candidate = template.replace(vars[0], metricNames[j]).replace(vars[1], metricNames[k]);

					Quality quality = runBenchmark(new PredictionMethodsFromString(candidate), metricsData, respProjs);

					double corr = 0;

					switch(mode) {
						case BugCount:
							corr = quality.bcWeightedCorrect;
							break;
						case TestEffort:
							corr = quality.teWeightedCorrect;
							break;
					}

					if(corr > winnerCorr) {
						winner = candidate;
						winnerCorr = corr;
					}

				}
			}
		} else if(vars.length == 3) {
			for(int j=0; j<metricNames.length; j++) {
				for(int k=0; k<metricNames.length; k++) {
					for(int l=0; l<metricNames.length; l++) {
						if(j == k && k == l) {
							continue;
						}

						String candidate = template.replace(vars[0], metricNames[j]).replace(vars[1], metricNames[k]).replace(vars[2], metricNames[l]);

						Quality quality = runBenchmark(new PredictionMethodsFromString(candidate), metricsData, respProjs);

						double corr = 0;

						switch(mode) {
							case BugCount:
								corr = quality.bcWeightedCorrect;
								break;
							case TestEffort:
								corr = quality.teWeightedCorrect;
								break;
						}

						if(corr > winnerCorr) {
							winner = candidate;
							winnerCorr = corr;
						}
					}
				}
			}
		}

		return winner;
	}

	public interface PredictionMethods {
		public String getName();
		public double testEffortPredictionMeasure(ProjectWithResults m);
		public double bugCountPredictionMeasure(ProjectWithResults m);
	}

	public static Quality runBenchmark(final PredictionMethods predMethods, List<ProjectWithResults> pml, List<ResponseProjects> rpl) throws Exception {
		int teCorrect = 0;
		int bcCorrect = 0;
		double teWeightedCorrect = 0.0f;
		double bcWeightedCorrect = 0.0f;
		List<String[]> tePredictions = new LinkedList<String[]>();
		List<String[]> bcPredictions = new LinkedList<String[]>();

		for(ResponseProjects rp : rpl) {
			if(rp.user == null) {
				tePredictions.add(new String[] {"N/A", "N/A"});
				bcPredictions.add(new String[] {"N/A", "N/A"});
				continue;
			}

			List<Project> projs = rp.toProjectList();

			if(isInvalidProject(pml, projs)) {
				tePredictions.add(new String[] {"N/A", "N/A"});
				bcPredictions.add(new String[] {"N/A", "N/A"});
				continue;
			}

			List<Double> bcPredVals = calcPredictionValues(predMethods, PredictionType.BugCount, pml, projs);

			int highestPredIx = getHighestPredIndex(projs, bcPredVals);
			String hiRepo = projs.get(highestPredIx).repoName;
			if(rp.highestBugCount.equals(hiRepo)) {
				bcCorrect++;
				bcWeightedCorrect += rp.weight;
			}

			int lowestPredIx = getLowestPredictionIndex(projs, bcPredVals);
			String loRepo = projs.get(lowestPredIx).repoName;
			if(rp.lowestBugCount.equals(loRepo)) {
				bcCorrect++;
				bcWeightedCorrect += rp.weight;
			}

			bcPredictions.add(new String[] {loRepo, hiRepo});

			List<Double> tePredVals = calcPredictionValues(predMethods, PredictionType.TestEffort, pml, projs);

			highestPredIx = getHighestPredIndex(projs, tePredVals);
			hiRepo = projs.get(highestPredIx).repoName;
			if(rp.mostTested.equals(hiRepo)) {
				teCorrect++;
				teWeightedCorrect += rp.weight;
			}

			lowestPredIx = getLowestPredictionIndex(projs, tePredVals);
			loRepo = projs.get(lowestPredIx).repoName;
			if(rp.leastTested.equals(loRepo)) {
				teCorrect++;
				teWeightedCorrect += rp.weight;
			}

			tePredictions.add(new String[] {loRepo, hiRepo});
		}
		return new Quality(teCorrect, bcCorrect, teWeightedCorrect, bcWeightedCorrect, tePredictions, bcPredictions);
	}

	public static int getLowestPredictionIndex(List<Project> projs, List<Double> predVals) {
		int lowestPredIx = 0;
		double min = Double.POSITIVE_INFINITY;
		for(int i=0; i<projs.size(); i++) {
			if(predVals.get(i) < min) {
				min = predVals.get(i);
				lowestPredIx = i;
			}
		}
		return lowestPredIx;
	}

	public static int getHighestPredIndex(List<Project> projs, List<Double> predVals) {
		int highestPredIx = 0;
		double max = Double.NEGATIVE_INFINITY;
		for(int i=0; i<projs.size(); i++) {
			if(predVals.get(i) > max) {
				max = predVals.get(i);
				highestPredIx = i;
			}
		}
		return highestPredIx;
	}

	public static List<Double> calcPredictionValues(final PredictionMethods predMethods, final PredictionType predType, final List<ProjectWithResults> pml, List<Project> projectList) {
		ITransform<Project, Double> projectToMeasureResult = new ProjectToMeasureResultTransform(predType, predMethods, pml);
		return Func.map(projectToMeasureResult, projectList);
	}

	public static boolean isInvalidProject(final List<ProjectWithResults> pml, List<Project> projs) {
		IPredicate<Project> isInvalid = new IPredicate<Project>() {
			@Override
			public boolean invoke(Project p) {
				return metricsForProject(p, pml) == null;
			}
		};
		return Func.contains(isInvalid, projs);
	}

	public static ProjectWithResults metricsForProject(final Project p, List<ProjectWithResults> pml) {
		IPredicate<ProjectWithResults> isProj = new IPredicate<ProjectWithResults>() {
			@Override
			public boolean invoke(ProjectWithResults obj) {
				return obj.project.equals(p);
			}
		};
		return Func.find(isProj, pml);
	}

	public static class Quality {
		public final int teCorrect;
		public final int bcCorrect;
		public final double teWeightedCorrect;
		public final double bcWeightedCorrect;
		public final List<String[]> tePredictions;
		public final List<String[]> bcPredictions;

		public Quality(int teCorrect, int bcCorrect, double teWeightedCorrect, double bcWeightedCorrect, List<String[]> tePredictions, List<String[]> bcPredictions) {
			this.teCorrect = teCorrect;
			this.bcCorrect = bcCorrect;
			this.teWeightedCorrect = teWeightedCorrect;
			this.bcWeightedCorrect = bcWeightedCorrect;
			this.tePredictions = tePredictions;
			this.bcPredictions = bcPredictions;
		}
	}

	public static class ProjectToMeasureResultTransform implements ITransform<Project, Double> {
		private final PredictionType predType;
		private final PredictionMethods predMethods;
		private final List<ProjectWithResults> pml;

		public ProjectToMeasureResultTransform(PredictionType predType, PredictionMethods predMethods, List<ProjectWithResults> pml) {
			this.predType = predType;
			this.predMethods = predMethods;
			this.pml = pml;
		}

		@Override
		public Double invoke(Project p) {
			switch(predType) {
				case BugCount:
					return predMethods.bugCountPredictionMeasure(metricsForProject(p, pml));
				default:
				case TestEffort:
					return predMethods.testEffortPredictionMeasure(metricsForProject(p, pml));
			}
		}
	}
}
