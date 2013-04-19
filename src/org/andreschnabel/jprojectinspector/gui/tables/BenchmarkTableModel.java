package org.andreschnabel.jprojectinspector.gui.tables;

import org.andreschnabel.jprojectinspector.evaluation.PredictionType;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class BenchmarkTableModel extends AbstractTableModel {

	private final static String[] columnNames = new String[] {"owner", "min", "max", "min-predicted", "max-predicted", "weight"};

	private List<ResponseProjects> respProjs;

	private PredictionType mode = PredictionType.TestEffort;
	private List<String[]> predictions;

	@Override
	public String getColumnName(int column) {
		return columnNames[column];
	}

	@Override
	public int getRowCount() {
		if(respProjs == null) {
			return 0;
		}
		return respProjs.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		ResponseProjects rps = respProjs.get(rowIndex);
		switch(columnIndex) {
			case 0:
				return rps.user;
			case 1:
				switch(mode) {
					case BugCount:
						return rps.lowestBugCount;
					case TestEffort:
						return rps.leastTested;
				}
			case 2:
				switch(mode) {
					case BugCount:
						return rps.highestBugCount;
					case TestEffort:
						return rps.mostTested;
				}
			case 3:
				if(predictions != null) {
					return predictions.get(rowIndex)[0];
				} else return "N/A";
			case 4:
				if(predictions != null) {
					return predictions.get(rowIndex)[1];
				} else return "N/A";
			case 5:
				return rps.weight;
			default:
				return "N/A";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setRespProjs(List<ResponseProjects> respProjs) {
		this.respProjs = respProjs;
	}

	public void setMode(PredictionType mode) {
		this.mode = mode;
	}

	public void setPredictions(List<String[]> predictions) {
		this.predictions = predictions;
	}
}
