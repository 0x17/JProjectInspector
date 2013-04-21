package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.evaluation.MetricsCollector;
import org.andreschnabel.jprojectinspector.gui.freechart.MetricBarChart;
import org.andreschnabel.jprojectinspector.gui.tables.MetricResultTableModel;
import org.andreschnabel.jprojectinspector.gui.windows.FreeChartWindow;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.threading.AsyncTask;
import org.andreschnabel.jprojectinspector.utilities.threading.AsyncTaskBatch;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MetricResultsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private AsyncTaskBatch<Double[]> batch;
	private JTable resultTable;

	public AsyncTaskBatch<Double[]> getBatch() { return batch; }

	public MetricResultsPanel(CsvData results) throws Exception {
		List<Project> projects = Project.projectListFromCsv(results);
		List<String> metricNames = metricNamesFromHeaders(results.getHeaders());
		MetricResultTableModel mrtm = initCommon(projects, metricNames);
		addResultsFromCsv(mrtm, results);
		resultTable.updateUI();
	}

	private List<String> metricNamesFromHeaders(String[] headers) {
		List<String> metricNames = new LinkedList<String>();
		for(int i=2; i<headers.length; i++) {
			metricNames.add(headers[i]);
		}
		return metricNames;
	}

	private void addResultsFromCsv(MetricResultTableModel mrtm, CsvData results) {
		for(int i=0; i<results.rowCount(); i++) {
			String[] row = results.getRow(i);
			Double[] vals = new Double[results.columnCount()-2];
			for(int j=0; j<vals.length; j++) {
				vals[j] = Double.valueOf(row[j+2]);
			}
			mrtm.addResultTupleToCache(new Project(row[0], row[1]), vals);
		}
	}

	public MetricResultsPanel(List<Project> projects, List<String> metricNames) {
		MetricResultTableModel mrtm = initCommon(projects, metricNames);
		initAndExecTaskBatch(projects, metricNames, mrtm);
	}

	private MetricResultTableModel initCommon(List<Project> projects, List<String> metricNames) {
		setLayout(new GridBagLayout());

		final MetricResultTableModel mrtm = new MetricResultTableModel(projects, metricNames);

		initAndAddTable(mrtm);
		initAndAddTopPane(mrtm, metricNames);

		return mrtm;
	}

	private void initAndAddTopPane(final MetricResultTableModel mrtm, final List<String> metricNames) {
		JPanel topPane = new JPanel(new FlowLayout());

		JButton exportBtn = new JButton("Export");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Map<Project, Double[]> results = mrtm.getResults();

				List<String[]> ownerRepoMetricsRows = new ArrayList<String[]>(results.keySet().size());

				int ncols = metricNames.size()+2;

				String[] headers = new String[ncols];
				headers[0] = "owner";
				headers[1] = "repo";
				for(int i1 = 0; i1 < metricNames.size(); i1++) {
					String metricName = metricNames.get(i1);
					headers[2+i1] = metricName;
				}
				ownerRepoMetricsRows.add(headers);

				for(Project p : results.keySet()) {
					Double[] result = results.get(p);

					String[] row = new String[ncols];

					row[0] = p.owner;
					row[1] = p.repoName;

					for(int i=0; i<result.length; i++) {
						row[2+i] = ""+result[i];
					}

					ownerRepoMetricsRows.add(row);
				}

				CsvData csvData = new CsvData(ownerRepoMetricsRows);

				try {
					GuiHelpers.saveCsvDialog(new File("."), csvData);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		topPane.add(exportBtn);

		for(int col=0; col<metricNames.size(); col++) {
			JButton visualizeBtn = new JButton("Visualize " + metricNames.get(col));
			final int finalCol = col;
			visualizeBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent actionEvent) {
					String title = metricNames.get(finalCol);
					JFreeChart mbc = MetricBarChart.newInstance(title, mrtm.getResults(), finalCol);
					FreeChartWindow cw = new FreeChartWindow(mbc, new Dimension(640, 480));
					cw.setVisible(true);
				}
			});
			topPane.add(visualizeBtn);
		}

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(topPane);
	}

	private void initAndAddTable(MetricResultTableModel mrtm) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		resultTable = new JTable(mrtm);
		add(new JScrollPane(resultTable), gbc);
	}

	private void initAndExecTaskBatch(List<Project> projects, final List<String> metricNames, final MetricResultTableModel mrtm) {
		batch = new AsyncTaskBatch<Double[]>(projects.size());
		for(final Project p : projects) {
			batch.add(new AsyncTask<Double[]>() {
				@Override
				public void onFinished(Double[] results) {
					mrtm.addResultTupleToCache(p, results);
					resultTable.updateUI();
				}

				@Override
				public Double[] doInBackground() {
					return MetricsCollector.gatherMetricsForProject(metricNames, p);
				}
			});
		}
		batch.execute();
	}

}
