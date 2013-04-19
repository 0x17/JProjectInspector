package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.evaluation.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.SurveyFormat;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
	private JComboBox modeCombo;
	private BenchmarkMode mode;
	private JLabel weightedNumCorrLbl;

	private static enum BenchmarkMode {
		TestEffort,
		BugCount
	}

	public BenchmarkPanel() {
		initTopPane();
		initTablePane();
	}

	private void initTopPane() {
		setLayout(new BorderLayout());
		JPanel topPane = new JPanel(new GridLayout(9, 2));

		initResultSelectionPanel(topPane);
		initEstimationsSelectionPanel(topPane);

		topPane.add(new JLabel("Variables:"));
		variablesLbl = new JLabel();
		topPane.add(variablesLbl);

		topPane.add(new JLabel("Mode: "));
		modeCombo = new JComboBox(new String[]{"Test effort estimation", "Bug count estimation"});
		modeCombo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent itemEvent) {
				mode = modeCombo.getSelectedIndex() == 0 ? BenchmarkMode.TestEffort : BenchmarkMode.BugCount;
			}
		});
		topPane.add(modeCombo);

		topPane.add(new JLabel("Prediction equation:"));
		equationField = new JTextField("0");
		equationField.getDocument().addDocumentListener(new EquationChangeListener());
		topPane.add(equationField);

		topPane.add(new JLabel("# Correct predictions:"));
		numCorrLbl = new JLabel("0");
		topPane.add(numCorrLbl);

		topPane.add(new JLabel("Weighted:"));
		weightedNumCorrLbl = new JLabel("0");
		topPane.add(weightedNumCorrLbl);

		topPane.add(new JLabel("Total number of estimations:"));
		numEstimationsLbl = new JLabel("0");
		topPane.add(numEstimationsLbl);

		topPane.add(new JLabel("Percentage:"));
		percCorrLbl = new JLabel("0%");
		topPane.add(percCorrLbl);

		add(topPane, BorderLayout.NORTH);
	}

	private static String selectCsvMessage(String colNames, int width) {
		return "<html><div style=\"width:"+width+"px;\">Select CSV file with column headers: " + colNames +"</div></html>";
	}

	private void initEstimationsSelectionPanel(JPanel topPane) {
		topPane.add(new JLabel("Estimations:"));
		JPanel esp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPane.add(esp);
		estimationsLbl = new JLabel(selectCsvMessage(SurveyFormat.ESTIMATION_COLUMN_HEADERS, (int) (topPane.getWidth()/2.0f)));
		esp.add(estimationsLbl);
		esp.add(getBrowseButton(BenchInputType.Estimations));
	}

	private void initResultSelectionPanel(JPanel topPane) {
		topPane.add(new JLabel("Metric results:"));
		JPanel mrp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPane.add(mrp);
		metricResultsLbl = new JLabel(selectCsvMessage(SurveyFormat.METRIC_RESULTS_COLUMN_HEADERS, (int) (topPane.getWidth()/2.0f)));
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
					final CsvData data = GuiHelpers.loadCsvDialog(new File("data/benchmark"));
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
		// Je nach modus (testeffort/bugcount)
		// Jeder Nutzer eine Reihe
		// nutzername, least, most, leastpred, mostpred <- pred jeweils rot falls falsch und grün, falls richtig.
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

		if(!EquationHelpers.validateEquation(variablesLst, equationField.getText())) {
			return;
		}

		Benchmark.PredictionMethods predMethods = new Benchmark.PredictionMethods() {
			@Override
			public String getName() {
				return "gui-input";
			}

			@Override
			public double testEffortPredictionMeasure(ProjectWithResults m) {
				return common(m);
			}

			@Override
			public double bugCountPredictionMeasure(ProjectWithResults m) {
				return common(m);
			}

			private double common(ProjectWithResults m) {
				Map<String, Object> bindings = resultsToBindings(m);
				Object result = EquationHelpers.parseEquation(bindings, equationField.getText());
				if(result != null) {
					return (Double)result;
				}
				return 0;
			}

			private Map<String, Object> resultsToBindings(ProjectWithResults m) {
				Map<String, Object> bindings = new HashMap<String, Object>();
				String[] headers = m.getResultHeaders();
				for(int i=0; i<m.results.length; i++) {
					bindings.put(headers[i], m.results[i]);
				}
				return bindings;
			}
		};
		try {
			Benchmark.Quality q = Benchmark.countCorrectPredictions(predMethods, metricsData, respProjs);

			int ncorr;
			double wncorr;
			switch(mode) {
				default:
				case TestEffort:
					ncorr = q.teCorrect;
					wncorr = q.teWeightedCorrect;
					break;
				case BugCount:
					ncorr = q.bcCorrect;
					wncorr = q.bcWeightedCorrect;
					break;
			}

			numCorrLbl.setText(""+ncorr);
			weightedNumCorrLbl.setText(""+wncorr);
			percCorrLbl.setText("" + ncorr/(double)(respProjs.size()*2) + "%");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
