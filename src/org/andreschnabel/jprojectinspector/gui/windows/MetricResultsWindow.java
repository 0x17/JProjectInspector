package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.freechart.ChartWindow;
import org.andreschnabel.jprojectinspector.freechart.MetricBarChart;
import org.andreschnabel.jprojectinspector.gui.tables.MetricResultTableModel;
import org.andreschnabel.jprojectinspector.metrics.MetricType;
import org.andreschnabel.jprojectinspector.metrics.MetricsRegistry;
import org.andreschnabel.jprojectinspector.model.CsvData;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.*;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MetricResultsWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private AsyncTaskBatch<Float[]> batch;
	private JTable resultTable;

	public MetricResultsWindow(List<Project> projects, final List<String> metricNames) {
		super("Metric results");
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		final MetricResultTableModel mrtm = new MetricResultTableModel(projects, metricNames);

		initAndAddTable(mrtm);
		initAndAddTopPane(mrtm, metricNames);

		setSize(640, 480);
		setLocationRelativeTo(null);

		initAndExecTaskBatch(projects, metricNames, mrtm);
		setAlwaysOnTop(true);
	}

	private void initAndAddTopPane(final MetricResultTableModel mrtm, final List<String> metricNames) {
		JPanel topPane = new JPanel(new FlowLayout());

		JButton exportBtn = new JButton("Export");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				Map<Project, Float[]> results = mrtm.getResults();

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
					Float[] result = results.get(p);

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
					ChartWindow cw = new ChartWindow(title, mbc, new Dimension(640, 480));
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
		batch = new AsyncTaskBatch<Float[]>(projects.size());
		for(final Project p : projects) {
			batch.add(new AsyncTask<Float[]>() {
				@Override
				public void onFinished(Float[] results) {
					mrtm.addResultTupleToCache(p, results);
					resultTable.updateUI();
				}

				@Override
				public Float[] doInBackground() {
					Float[] results = new Float[metricNames.size()];

					Predicate<String> isOfflineMetric = new Predicate<String>() {
						@Override
						public boolean invoke(String metricName) {
							return MetricsRegistry.getTypeOfMetric(metricName) == MetricType.Offline;
						}
					};
					boolean hasOfflineMetric = ListHelpers.contains(isOfflineMetric, metricNames);

					File repoPath = null;
					try {
						if(hasOfflineMetric) {
							repoPath = ProjectDownloader.loadProject(p);
						}
					} catch(Exception e)  {
						e.printStackTrace();
					}

					for(int i = 0; i < metricNames.size(); i++) {
						String metricName = metricNames.get(i);
						MetricType type = MetricsRegistry.getTypeOfMetric(metricName);
						float result = Float.NaN;

						try {
							switch(type) {
								case Offline:
									if(repoPath != null) {
										result = MetricsRegistry.measureOfflineMetric(metricName, repoPath);
									}
									break;
								case Online:
									result = MetricsRegistry.measureOnlineMetric(metricName, p);
									break;
							}
						} catch(Exception e) {
							e.printStackTrace();
						}

						results[i] = result;
					}

					if(repoPath != null) {
						try {
							ProjectDownloader.deleteProject(p);
						} catch(Exception e) {
							e.printStackTrace();
						}
					}

					return results;
				}
			});
		}
		batch.execute();
	}

	@Override
	public void dispose() {
		batch.dipose();
		super.dispose();
	}
}
