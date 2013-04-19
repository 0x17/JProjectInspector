package org.andreschnabel.jprojectinspector.metrics.survey;

import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import java.io.File;

public class SurveyMetricCommon {

	public static File estimationsFile = new File("data/benchmark/WeightedEstimates.csv");

	public static Estimation measureCommon(Project p, String minHeader, String maxHeader) {
		try {
			CsvData respWithUser = CsvHelpers.parseCsv(estimationsFile);
			for(int row=0; row<respWithUser.rowCount(); row++) {
				String user = respWithUser.getCellAt(row, "user");
				if(p.owner.equals(user)) {
					String minRepo = respWithUser.getCellAt(row, minHeader);
					if(p.repoName.equals(minRepo)) {
						return Estimation.Lowest;
					}

					String maxRepo = respWithUser.getCellAt(row, maxHeader);
					if(p.repoName.equals(maxRepo)) {
						return Estimation.Highest;
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Estimation.None;
	}

}
