package org.andreschnabel.jprojectinspector.runners.evaluation;

import org.andreschnabel.jprojectinspector.evaluation.survey.DeltaCalculator;
import org.andreschnabel.jprojectinspector.model.metrics.ProjectMetricsLst;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.XmlHelpers;

import java.io.File;
import java.util.List;

public class CalcDeltasRunner {

	public static void main(String[] args) throws Exception {
		calcDeltas();
	}

	public static List<DeltaCalculator.Deltas> calcDeltas() throws Exception {
		ProjectMetricsLst metrics = (ProjectMetricsLst) XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("responses500.xml"));

		List<DeltaCalculator.Deltas> deltasLst = DeltaCalculator.calculateDeltas(rpl.responseProjs, metrics.projectMetrics);

		Predicate<DeltaCalculator.Deltas> nonNull = new Predicate<DeltaCalculator.Deltas>() {
			@Override
			public boolean invoke(DeltaCalculator.Deltas obj) {
				return obj.user != null;
			}
		};
		deltasLst = ListHelpers.filter(nonNull, deltasLst);

		Transform<DeltaCalculator.Deltas, String> deltaToCsvLine = new Transform<DeltaCalculator.Deltas, String>() {
			@Override
			public String invoke(DeltaCalculator.Deltas d) {
				return d.toCsvLine(',');
			}
		};

		List<String> lines = ListHelpers.map(deltaToCsvLine , deltasLst);

		StringBuilder sb = new StringBuilder();
		sb.append(DeltaCalculator.Deltas.csvHeader(','));
		for(String line : lines) {
			sb.append(line);
		}

		FileHelpers.writeStrToFile(sb.toString(), "deltas.csv");

		return deltasLst;
	}

}
