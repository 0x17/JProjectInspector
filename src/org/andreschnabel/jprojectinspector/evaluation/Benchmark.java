package org.andreschnabel.jprojectinspector.evaluation;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
import org.andreschnabel.jprojectinspector.utilities.functional.IVarIndexedAction;
import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.StringHelpers;

import java.util.LinkedList;
import java.util.List;

/**
 * Assess quality of test effort estimation resp. bug count estimation prediction equation.
 */
public class Benchmark {

	public static String enumerate(final String template, final List<ProjectWithResults> metricsData, final List<ResponseProjects> respProjs, final PredictionType mode) throws Exception {
		List<String[]> matches = RegexHelpers.batchMatch("([A-Za-z]+)", template);
		if(matches.isEmpty()) {
			return null;
		}

		final String[] vars = new String[matches.size()];

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

		final String[] winner = new String[1];
		final double[] winnerCorr = new double[1];

		final String[] metricNames = metricsData.get(0).getResultHeaders();

		Func.nestedFor(vars.length, 0, metricNames.length, new IVarIndexedAction() {
			@Override
			public void invoke(int[] indices) {
				for(int i=0; i<indices.length; i++) {
					for(int j=0; j<indices.length; j++) {
						if(i != j && indices[i] == indices[j]) {
							return;
						}
					}
				}

				String candidate = insertMetricsForVarPlaceholders(template, vars, metricNames, indices);

				Quality quality;
				try {
					quality = runBenchmark(new PredictionMethodsFromString(candidate), metricsData, respProjs);
				} catch(Exception e) {
					e.printStackTrace();
					return;
				}

				if(quality == null) {
					return;
				}

				double corr = 0;

				switch(mode) {
					case BugCount:
						if(quality.numBcWeightedApplicable == 0) {
							corr = 0;
						} else {
							corr = quality.bcWeightedCorrect / quality.numBcWeightedApplicable;
						}
						break;
					case TestEffort:
						if(quality.numTeWeightedApplicable == 0) {
							corr = 0;
						} else {
							corr = quality.teWeightedCorrect / quality.numTeWeightedApplicable;;
						}
						break;
				}

				if(corr > winnerCorr[0]) {
					winner[0] = candidate;
					winnerCorr[0] = corr;
				}

			}
		});

		return winner[0];
	}

	public static String insertMetricsForVarPlaceholders(String template, String[] vars, String[] metricNames, int[] indices) {
		String[] varReplacements = new String[vars.length];
		for(int i=0; i<indices.length; i++) {
			varReplacements[i] = metricNames[indices[i]];
		}
		return StringHelpers.replaceCorresponding(template, vars, varReplacements);
	}

	public interface PredictionMethods {
		public String getName();
		public double testEffortPredictionMeasure(ProjectWithResults m);
		public double bugCountPredictionMeasure(ProjectWithResults m);
	}

	public static Quality runBenchmark(final PredictionMethods predMethods, List<ProjectWithResults> pml, List<ResponseProjects> rpl) throws Exception {
		int teCorrect = 0;
		int bcCorrect = 0;
		double teWeightedCorrect = 0.0;
		double bcWeightedCorrect = 0.0;
		List<String[]> tePredictions = new LinkedList<String[]>();
		List<String[]> bcPredictions = new LinkedList<String[]>();
		int numBcApplicable = 0;
		int numTeApplicable = 0;
		double numBcWeightedApplicable = 0.0;
		double numTeWeightedApplicable = 0.0;

		for(final ResponseProjects rp : rpl) {
			if(rp.user == null) {
				tePredictions.add(new String[] {"N/A", "N/A"});
				bcPredictions.add(new String[] {"N/A", "N/A"});
				continue;
			}

			List<Project> projs = rp.toProjectList();

			if(containsInvalidProject(pml, projs)) {
				tePredictions.add(new String[] {"N/A", "N/A"});
				bcPredictions.add(new String[] {"N/A", "N/A"});
				continue;
			}

			ProjectWithResults highestBugCountProjWithResults = Func.find(new IPredicate<ProjectWithResults>() {
				@Override
				public boolean invoke(ProjectWithResults pwr) {
					return pwr.project.owner.equals(rp.user) && pwr.project.repoName.equals(rp.highestBugCount);
				}
			}, pml);
			ProjectWithResults lowestBugCountProjWithResults = Func.find(new IPredicate<ProjectWithResults>() {
				@Override
				public boolean invoke(ProjectWithResults pwr) {
					return pwr.project.owner.equals(rp.user) && pwr.project.repoName.equals(rp.lowestBugCount);
				}
			}, pml);

			double hiPred = predMethods.bugCountPredictionMeasure(highestBugCountProjWithResults);
			double lowPred = predMethods.bugCountPredictionMeasure(lowestBugCountProjWithResults);

			if(Double.isNaN(hiPred) || Double.isNaN(lowPred)){
				bcPredictions.add(new String[] {"N/A","N/A"});
			} else if(hiPred > lowPred) {
				bcCorrect++;
				bcWeightedCorrect += rp.weight;
				bcPredictions.add(new String[] {rp.lowestBugCount, rp.highestBugCount});
				numBcApplicable++;
				numBcWeightedApplicable += rp.weight;
			} else {
				bcPredictions.add(new String[] {rp.highestBugCount, rp.lowestBugCount});
				numBcApplicable++;
				numBcWeightedApplicable += rp.weight;
			}

			ProjectWithResults mostTestedProjWithResults = Func.find(new IPredicate<ProjectWithResults>() {
				@Override
				public boolean invoke(ProjectWithResults pwr) {
					return pwr.project.owner.equals(rp.user) && pwr.project.repoName.equals(rp.mostTested);
				}
			}, pml);
			ProjectWithResults leastTestedCountProjWithResults = Func.find(new IPredicate<ProjectWithResults>() {
				@Override
				public boolean invoke(ProjectWithResults pwr) {
					return pwr.project.owner.equals(rp.user) && pwr.project.repoName.equals(rp.leastTested);
				}
			}, pml);

			hiPred = predMethods.testEffortPredictionMeasure(mostTestedProjWithResults);
			lowPred = predMethods.testEffortPredictionMeasure(leastTestedCountProjWithResults);

			if(Double.isNaN(hiPred) || Double.isNaN(lowPred)) {
				tePredictions.add(new String[] {"N/A", "N/A"});
			} else if(hiPred > lowPred) {
				teCorrect++;
				teWeightedCorrect += rp.weight;
				tePredictions.add(new String[] {rp.leastTested, rp.mostTested});
				numTeApplicable++;
				numTeWeightedApplicable += rp.weight;
			} else {
				tePredictions.add(new String[] {rp.mostTested, rp.leastTested});
				numTeApplicable++;
				numTeWeightedApplicable += rp.weight;
			}
		}
		return new Quality(teCorrect, bcCorrect, teWeightedCorrect, bcWeightedCorrect, tePredictions, bcPredictions, numTeApplicable, numTeWeightedApplicable, numBcApplicable, numBcWeightedApplicable);
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

	public static boolean containsInvalidProject(final List<ProjectWithResults> pml, List<Project> projs) {
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
		public final int numTeApplicable;
		public final double numTeWeightedApplicable;
		public final int numBcApplicable;
		public final double numBcWeightedApplicable;

		public Quality(int teCorrect, int bcCorrect, double teWeightedCorrect, double bcWeightedCorrect, List<String[]> tePredictions, List<String[]> bcPredictions, int numTeApplicable, double numTeWeightedApplicable, int numBcApplicable, double numBcWeightedApplicable) {
			this.teCorrect = teCorrect;
			this.bcCorrect = bcCorrect;
			this.teWeightedCorrect = teWeightedCorrect;
			this.bcWeightedCorrect = bcWeightedCorrect;
			this.tePredictions = tePredictions;
			this.bcPredictions = bcPredictions;
			this.numTeApplicable = numTeApplicable;
			this.numTeWeightedApplicable = numTeWeightedApplicable;
			this.numBcApplicable = numBcApplicable;
			this.numBcWeightedApplicable = numBcWeightedApplicable;
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
