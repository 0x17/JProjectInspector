package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.tables.MetricResultTableModel;
import org.andreschnabel.jprojectinspector.metrics.MetricType;
import org.andreschnabel.jprojectinspector.metrics.MetricsRegistry;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.utilities.AsyncTask;
import org.andreschnabel.jprojectinspector.utilities.AsyncTaskBatch;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.ProjectDownloader;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class MetricResultsWindow extends JFrame {

	private AsyncTaskBatch<Float[]> batch;
	private JTable resultTable;

	public MetricResultsWindow(List<Project> projects, final List<String> metricNames) {
		super("Metric results");
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		final MetricResultTableModel mrtm = new MetricResultTableModel(projects, metricNames);

		initAndAddTable(mrtm);

		setSize(640, 480);
		setLocationRelativeTo(null);

		initAndExecTaskBatch(projects, metricNames, mrtm);
		setAlwaysOnTop(true);
	}

	private void initAndAddTable(MetricResultTableModel mrtm) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 1;
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
						float result = 0.0f;

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
