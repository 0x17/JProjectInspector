package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.model.Project;

import javax.swing.table.AbstractTableModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MetricResultTableModel extends AbstractTableModel {
	private final List<Project> projects;
	private final List<String> metricNames;

	private final Map<Project, Float[]> resultsCache = new HashMap<Project, Float[]>();

	public MetricResultTableModel(List<Project> projects, List<String> metricNames) {
		this.projects = projects;
		this.metricNames = metricNames;
	}

	public void addResultTupleToCache(Project p, Float[] resultTuple) {
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

		float result;
		if(resultsCache.containsKey(p)) {
			result = resultsCache.get(p)[columnIndex-2];
		} else {
			result = 0.0f;
		}

		switch(columnIndex) {
			case 0:
				return p.owner;
			case 1:
				return p.repoName;
			default:
				return String.valueOf(result);
		}
	}
}
