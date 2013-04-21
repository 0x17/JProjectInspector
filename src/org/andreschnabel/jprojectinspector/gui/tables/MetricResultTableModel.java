package org.andreschnabel.jprojectinspector.gui.tables;

import org.andreschnabel.jprojectinspector.model.Project;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricResultTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	private final List<Project> projects;
	private final List<String> metricNames;

	private final Map<Project, Double[]> resultsCache = new HashMap<Project, Double[]>();

	public Map<Project, Double[]> getResults() {
		return resultsCache;
	}

	public MetricResultTableModel(List<Project> projects, List<String> metricNames) {
		this.projects = projects;
		this.metricNames = metricNames;
	}

	public void addResultTupleToCache(Project p, Double[] resultTuple) {
		resultsCache.put(p, resultTuple);
	}

	@Override
	public int getRowCount() {
		return projects.size();
	}

	@Override
	public int getColumnCount() {
		return metricNames.size() + 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		switch(columnIndex) {
			case 0:
				return "owner";
			case 1:
				return "repo";
			default:
				return metricNames.get(columnIndex-2);
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Project p = projects.get(rowIndex);

		switch(columnIndex) {
			case 0:
				return p.owner;
			case 1:
				return p.repoName;
			default:
				Double result;
				if(resultsCache.containsKey(p)) {
					result = resultsCache.get(p)[columnIndex-2];
				} else {
					result = Double.NaN;
				}

				return Double.isNaN(result) ? "N/A" : String.valueOf(result);
		}
	}

	public List<String> getMetricNames() {
		return metricNames;
	}
}
