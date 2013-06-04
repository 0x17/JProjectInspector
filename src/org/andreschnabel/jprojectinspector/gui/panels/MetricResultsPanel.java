package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.evaluation.MetricsCollector;
import org.andreschnabel.jprojectinspector.gui.tables.MetricResultTableModel;
import org.andreschnabel.jprojectinspector.gui.windows.ProjectMetricsWindow;
import org.andreschnabel.jprojectinspector.gui.windows.UserStatsWindow;
import org.andreschnabel.jprojectinspector.gui.windows.VisualizationWindow;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.pecker.helpers.GuiHelpers;
import org.andreschnabel.pecker.serialization.CsvData;
import org.andreschnabel.pecker.threading.AsyncTask;
import org.andreschnabel.pecker.threading.AsyncTaskBatch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Panel zur Darstellung von Metrik-Ergebnissen.
 *
 * Metrik-Ergebnisse werden entweder für gegebene Projekte und Metrik-Namen im Hintergrund neu ermittelt
 * ODER von CSV-Daten übernommen.
 *
 * Falls Metrik-Namen erst im Hintergrund ermittelt werden, sind die Werte vorher mit N/A gefüllt.
 * Das steht für ungültig.
 */
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

	public MetricResultsPanel(List<Project> projects, List<String> metricNames) {
		MetricResultTableModel mrtm = initCommon(projects, metricNames);
		initAndExecTaskBatch(projects, metricNames, mrtm);
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

		JButton visualizeBtn = new JButton("Visualize");
		visualizeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				JFrame vw = new VisualizationWindow(metricNames, mrtm.getResults());
				vw.setVisible(true);
			}
		});
		topPane.add(visualizeBtn);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(topPane);
	}

	private void initAndAddTable(final MetricResultTableModel mrtm) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 3;
		resultTable = new JTable(mrtm);
		resultTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				int selRowIndex = resultTable.getSelectedRow();
				int selColIndex = resultTable.getSelectedColumn();
				String owner = (String)mrtm.getValueAt(selRowIndex, 0);
				String repo = (String)mrtm.getValueAt(selRowIndex, 1);
				Project p = new Project(owner, repo);

				if(selColIndex == 0) {
					new UserStatsWindow(owner).setVisible(true);
					return;
				}

				Map<Project, Double[]> cache = mrtm.getResults();
				if(!cache.containsKey(p)) {
					return;
				}

				Double[] results = cache.get(p);
				List<String> metricNames = mrtm.getMetricNames();
				String[] metricNamesArray = metricNames.toArray(new String[] {});
				new ProjectMetricsWindow(new ProjectWithResults(p, metricNamesArray, results)).setVisible(true);
			}
		});
		JScrollPane scrollPane = new JScrollPane(resultTable);
		add(scrollPane, gbc);
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
