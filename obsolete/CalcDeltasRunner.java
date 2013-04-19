package org.andreschnabel.jprojectinspector.evaluation.survey.runners;

import org.andreschnabel.jprojectinspector.evaluation.DeltaCalculator;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjectsLst;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.XmlHelpers;

import java.io.File;
import java.util.List;

public class CalcDeltasRunner {

	public static void main(String[] args) throws Exception {
		calcDeltas();
	}

	public static List<Float[]> calcDeltas() throws Exception {
		//ProjectMetricsLst metrics = (ProjectMetricsLst) XmlHelpers.deserializeFromXml(ProjectMetricsLst.class, new File("data/metrics500.xml"));
		ResponseProjectsLst rpl = (ResponseProjectsLst)XmlHelpers.deserializeFromXml(ResponseProjectsLst.class, new File("data/responseswithuser500.xml"));

		final List<ProjectWithResults> pms = ProjectWithResults.fromCsv(CsvHelpers.parseCsv(new File("data/benchmark/metrics500.csv")));

		List<DeltaCalculator.Deltas> deltasLst = DeltaCalculator.calculateDeltas(rpl.responseProjs, pms);

		Predicate<DeltaCalculator.Deltas> nonNull = new Predicate<DeltaCalculator.Deltas>() {
			@Override
			public boolean invoke(DeltaCalculator.Deltas obj) {
				return obj.user != null;
			}
		};
		deltasLst = Func.filter(nonNull, deltasLst);

		Transform<DeltaCalculator.Deltas, String> deltaToCsvLine = new Transform<DeltaCalculator.Deltas, String>() {
			@Override
			public String invoke(DeltaCalculator.Deltas d) {
				return d.toCsvLine(',');
			}
		};

		List<String> lines = Func.map(deltaToCsvLine, deltasLst);

		StringBuilder sb = new StringBuilder();
		sb.append(DeltaCalculator.Deltas.csvHeader(','));
		for(String line : lines) {
			sb.append(line);
		}

		FileHelpers.writeStrToFile(sb.toString(), "deltas.csv");

		return deltasLst;
	}

}
