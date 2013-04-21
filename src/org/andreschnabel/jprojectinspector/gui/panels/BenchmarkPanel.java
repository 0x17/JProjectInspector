package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.evaluation.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.PredictionType;
import org.andreschnabel.jprojectinspector.evaluation.SurveyFormat;
import org.andreschnabel.jprojectinspector.gui.tables.BenchmarkTableCellRenderer;
import org.andreschnabel.jprojectinspector.gui.tables.BenchmarkTableModel;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
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
	private PredictionType mode = PredictionType.TestEffort;
	private JLabel weightedNumCorrLbl;
	private BenchmarkTableModel benchmarkTableModel = new BenchmarkTableModel();
	private JTable benchmarkTable = new JTable(benchmarkTableModel);

	private volatile boolean updating;

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
				mode = modeCombo.getSelectedIndex() == 0 ? PredictionType.TestEffort : PredictionType.BugCount;
				benchmarkTableModel.setMode(mode);
				benchmarkTable.updateUI();
			}
		});
		topPane.add(modeCombo);

		topPane.add(new JLabel("Prediction equation:"));

		JPanel equationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		equationField = new JTextField("0");
		equationField.setColumns(35);
		equationField.getDocument().addDocumentListener(new EquationChangeListener());
		equationPanel.add(equationField);

		JButton computeBtn = new JButton("Compute");
		computeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eqtnChanged();
			}
		});
		equationPanel.add(computeBtn);

		//topPane.add(equationField);
		topPane.add(equationPanel);

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
							respProjs = ResponseProjects.fromPreprocessedCsvData(data);
							benchmarkTableModel.setRespProjs(respProjs);
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
		JPanel tablePane = new JPanel(new GridLayout(1,1));
		tablePane.add(new JScrollPane(benchmarkTable));
		benchmarkTable.setDefaultRenderer(Object.class, new BenchmarkTableCellRenderer());
		add(tablePane, BorderLayout.CENTER);
	}

	private class EquationChangeListener implements DocumentListener {
		@Override
		public void insertUpdate(DocumentEvent e) {
			//eqtnChanged();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			//eqtnChanged();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			//eqtnChanged();
		}
	}

	private void eqtnChanged() {
		if(updating) {
			return;
		}

		if(respProjs == null || metricsData == null) {
			return;
		}

		if(!EquationHelpers.validateEquationSuccess(variablesLst, equationField.getText())) {
			return;
		}

		updating = true;

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
				return 0.0;
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
			Benchmark.Quality q = Benchmark.runBenchmark(predMethods, metricsData, respProjs);

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

			numCorrLbl.setText("" + ncorr);
			weightedNumCorrLbl.setText(""+wncorr);
			percCorrLbl.setText(String.format("%.2f", (ncorr/(double)(respProjs.size()*2))*100.0) + "%");

			updateTable(q);

		} catch(Exception e) {
			e.printStackTrace();
		}

		updating = false;
	}

	private void updateTable(Benchmark.Quality q) {
		benchmarkTableModel.setPredictions(mode == PredictionType.BugCount ? q.bcPredictions : q.tePredictions);
		benchmarkTable.updateUI();
	}

}
