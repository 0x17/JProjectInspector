package org.andreschnabel.jprojectinspector.evaluation.survey;

import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;

import java.util.List;

/**
 * Assess quality of test effort estimation resp. bug count estimation prediction equation.
 */
public class Benchmark {

	public interface PredictionMethods {
		public String getName();
		public float testEffortPredictionMeasure(ProjectWithResults m);
		public float bugCountPredictionMeasure(ProjectWithResults m);
	}

	public enum PredictionTypes {
		BugCount,
		TestEffort
	}

	public static Quality countCorrectPredictions(final PredictionMethods predMethods, List<ProjectWithResults> pml, List<ResponseProjects> rpl) throws Exception {
		int teCorrect = 0;
		int bcCorrect = 0;

		for(ResponseProjects rp : rpl) {
			if(rp.user == null) continue;

			List<Project> projs = rp.toProjectList();

			if(skipInvalidProjects(pml, projs)) continue;

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

		return new Quality(teCorrect, bcCorrect);
	}

	public static int getLowestPredictionIndex(List<Project> projs, List<Float> predVals) {
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

	public static int getHighestPredIndex(List<Project> projs, List<Float> predVals) {
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

	public static List<Float> calcPredictionValues(final PredictionMethods predMethods, final PredictionTypes predType, final List<ProjectWithResults> pml, List<Project> projs) {
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
		return Func.map(bcPred, projs);
	}

	public static boolean skipInvalidProjects(final List<ProjectWithResults> pml, List<Project> projs) {
		Predicate<Project> isInvalid = new Predicate<Project>() {
			@Override
			public boolean invoke(Project p) {
				return metricsForProject(p, pml) == null;
			}
		};
		return Func.contains(isInvalid, projs);
	}

	public static ProjectWithResults metricsForProject(final Project p, List<ProjectWithResults> pml) {
		Predicate<ProjectWithResults> isProj = new Predicate<ProjectWithResults>() {
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

		public Quality(int teCorrect, int bcCorrect) {
			this.teCorrect = teCorrect;
			this.bcCorrect = bcCorrect;
		}
	}
}
