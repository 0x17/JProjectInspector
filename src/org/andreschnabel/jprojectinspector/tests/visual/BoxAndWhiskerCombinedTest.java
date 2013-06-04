package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.FreeChartPanel;
import org.andreschnabel.jprojectinspector.gui.visualizations.BoxAndWhisker;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;
import org.andreschnabel.pecker.serialization.CsvData;
import org.andreschnabel.pecker.serialization.CsvHelpers;
import org.jfree.chart.JFreeChart;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class BoxAndWhiskerCombinedTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "box and whisker combined test";
					}

					@Override
					public void invoke() throws Exception {
						CsvData csvData = CsvHelpers.parseCsv(new File("data/benchmark/MetricResultsUmfragenCombined.csv"));
						List<ProjectWithResults> pwr = ProjectWithResults.fromCsv(csvData);
						Map<Project, Double[]> results = ProjectWithResults.toMap(pwr);
						JFreeChart chart = new BoxAndWhisker().visualizeCombined(pwr.get(0).getResultHeaders(), results);
						frm.add(new FreeChartPanel(chart, new Dimension(640, 480)));
						frm.pack();
					}
				}
		};
	}
}
