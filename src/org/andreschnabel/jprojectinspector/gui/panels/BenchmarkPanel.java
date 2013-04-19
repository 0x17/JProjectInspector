package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.evaluation.survey.Benchmark;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.EquationHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BenchmarkPanel extends PanelWithParent {
	private static final long serialVersionUID = 1L;
	private JTextField equationField;
	private JLabel estimationsLbl;
	private JLabel metricResultsLbl;
	private JLabel variablesLbl;
	private List<String> variablesLst = new ArrayList<String>();

	private List<ResponseProjects> respProjs;
	private List<ProjectWithResults> metricsData;
	private JLabel numEstimationsLbl;
	private JLabel numCorrLbl;
	private JLabel percCorrLbl;

	public BenchmarkPanel() {
		initTopPane();
		initTablePane();
	}

	private void initTopPane() {
		setLayout(new BorderLayout());
		JPanel topPane = new JPanel(new GridLayout(8, 2));

		initResultSelectionPanel(topPane);
		initEstimationsSelectionPanel(topPane);

		topPane.add(new JLabel("Variables:"));
		variablesLbl = new JLabel();
		topPane.add(variablesLbl);

		topPane.add(new JLabel("Prediction equation:"));
		equationField = new JTextField("0");
		equationField.getDocument().addDocumentListener(new EquationChangeListener());
		topPane.add(equationField);

		topPane.add(new JLabel("# Correct predictions:"));
		numCorrLbl = new JLabel("0");
		topPane.add(numCorrLbl);


		topPane.add(new JLabel("Weighted:"));
		topPane.add(new JLabel("0"));

		topPane.add(new JLabel("Total number of estimations:"));
		numEstimationsLbl = new JLabel("0");
		topPane.add(numEstimationsLbl);

		topPane.add(new JLabel("Percentage:"));
		percCorrLbl = new JLabel("0%");
		topPane.add(percCorrLbl);

		add(topPane, BorderLayout.NORTH);
	}

	private void initEstimationsSelectionPanel(JPanel topPane) {
		topPane.add(new JLabel("Estimations:"));
		JPanel esp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPane.add(esp);
		estimationsLbl = new JLabel("Select file!");
		esp.add(estimationsLbl);
		esp.add(getBrowseButton(BenchInputType.Estimations));
	}

	private void initResultSelectionPanel(JPanel topPane) {
		topPane.add(new JLabel("Metric results:"));
		JPanel mrp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		topPane.add(mrp);
		metricResultsLbl = new JLabel("Select file!");
		mrp.add(metricResultsLbl);
		mrp.add(getBrowseButton(BenchInputType.MetricResults));
	}

	private static enum BenchInputType {
		Estimations,
		MetricResults
	}

	private JButton getBrowseButton(final BenchInputType inputType) {
		JButton btn = new JButton("Browse");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					final CsvData data = GuiHelpers.loadCsvDialog(new File("."));
					if(data == null) {
						return;
					}
					switch(inputType) {
						case Estimations:
							estimationsLbl.setText(data.title);
							Transform<String[], ResponseProjects> rowToRespProjs = new Transform<String[], ResponseProjects>() {
								@Override
								public ResponseProjects invoke(String[] sa) {
									ResponseProjects rps = new ResponseProjects();
									rps.user = sa[0];
									rps.leastTested = sa[1];
									rps.mostTested = sa[2];
									rps.lowestBugCount = sa[3];
									rps.highestBugCount = sa[4];
									return rps;
								}
							};
							respProjs = CsvData.toList(rowToRespProjs, data);
							numEstimationsLbl.setText(""+respProjs.size()*2);
							break;
						case MetricResults:
							metricResultsLbl.setText(data.title);
							String[] headers = data.getHeaders();
							StringBuilder sb = new StringBuilder();
							variablesLst.clear();
							for(int i=2; i<headers.length; i++) {
								String header = headers[i];
								variablesLst.add(header);
								sb.append(header);
								if(i+1<headers.length)
									sb.append(", ");
							}
							variablesLbl.setText(sb.toString());
							metricsData = ProjectWithResults.fromCsv(data);
							break;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		return btn;
	}

	private void initTablePane() {
		JPanel tablePane = new JPanel();
		tablePane.add(new JTable());
		add(tablePane, BorderLayout.CENTER);
	}

	private class EquationChangeListener implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			eqtnChanged();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			eqtnChanged();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			eqtnChanged();
		}
	}

	private void eqtnChanged() {
		if(respProjs == null || metricsData == null) {
			return;
		}

		Benchmark.PredictionMethods predMethods = new Benchmark.PredictionMethods() {
			@Override
			public String getName() {
				return "gui-input";
			}

			@Override
			public float testEffortPredictionMeasure(ProjectWithResults m) {
				return common(m);
			}

			@Override
			public float bugCountPredictionMeasure(ProjectWithResults m) {
				return common(m);
			}

			private float common(ProjectWithResults m) {
				Map<String, Object> bindings = resultsToBindings(m);
				Object result = EquationHelpers.parseEquation(bindings, equationField.getText());
				if(result != null) {
					return (Float)result;
				}
				return 0;
			}

			private Map<String, Object> resultsToBindings(ProjectWithResults m) {
				Map<String, Object> bindings = new HashMap<String, Object>();
				String[] headers = m.getResultHeaders();
				for(int i=0; i<m.results.length; i++) {
					bindings.put(headers[i+2], m.results[i]);
				}
				return bindings;
			}
		};
		try {
			Benchmark.Quality q = Benchmark.countCorrectPredictions(predMethods, metricsData, respProjs);
			numCorrLbl.setText(""+q.bcCorrect);
			percCorrLbl.setText("" + q.bcCorrect/(float)(respProjs.size()*2) + "%");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
