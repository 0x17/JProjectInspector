package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.evaluation.Benchmark;
import org.andreschnabel.jprojectinspector.evaluation.PredictionMethodsFromString;
import org.andreschnabel.jprojectinspector.evaluation.PredictionType;
import org.andreschnabel.jprojectinspector.evaluation.SurveyFormat;
import org.andreschnabel.jprojectinspector.gui.tables.BenchmarkTableCellRenderer;
import org.andreschnabel.jprojectinspector.gui.tables.BenchmarkTableModel;
import org.andreschnabel.jprojectinspector.gui.windows.ProjectMetricsWindow;
import org.andreschnabel.jprojectinspector.gui.windows.UserStatsWindow;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.model.ProjectWithResults;
import org.andreschnabel.jprojectinspector.model.survey.ResponseProjects;
import org.andreschnabel.jprojectinspector.utilities.helpers.EquationHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Panel für das Benchmark.
 *
 * Erlaubt Auswahl von CSV-Dateien mit Einschätzungen und Metrik-Ergebnissen.
 *
 * Eine Combobox erlaubt auswahl des Modus -> Testaufwand oder Fehlerzahl.
 *
 * Ein Textfeld für Vorhersageformel zum gewählten Modus.
 *
 * "Null is invalid"-Checkbox wählt, ob ein Ergebnis von 0 (bspw. bei Testcodezeilen) ein ungültiges Ergebnis sein
 * und nicht für das Benchmark beachtet wird. Wird also nicht für Gesamtzahl der Entscheidungen gezählt.
 *
 * "Compute"-Schaltfläche führt Benchmark für gewählten Modus und eingegebene Formel aus. *
 * Ergebnisse werden darunter angezeigt.
 *
 * "Enumerate"-Schaltfläche probiert für Platzhalter (einzelne Zeichen, z.B. 'a') alle Metriknamen aus und setzt am
 * Ende in das Textfeld für die Formel die Metriknamen ein, bei denen die Bewertung am besten war.
 */
public class BenchmarkPanel extends PanelWithParent {
	private static final long serialVersionUID = 1L;

	private JTextField equationField;
	private JLabel estimationsLbl;
	private JLabel metricResultsLbl;
	private JTextArea variablesArea;
	private List<String> variablesLst = new ArrayList<String>();

	private List<ResponseProjects> respProjs;
	private Map<Project, ProjectWithResults> metricsData;
	private JLabel numEstimationsLbl;
	private JLabel numCorrLbl;
	private JLabel percCorrLbl;
	private JComboBox modeCombo;
	private PredictionType mode = PredictionType.TestEffort;
	private JLabel weightedNumCorrLbl;
	private BenchmarkTableModel benchmarkTableModel = new BenchmarkTableModel();
	private JTable benchmarkTable = new JTable(benchmarkTableModel);

	private volatile boolean updating;
	private JLabel weightSumLbl;
	private JLabel percWeightCorrLbl;
	private double weightSum;
	private JCheckBox zeroIsInvalidCheckbox;

	/**
	 * Konstruktor
	 * Erzeuge neues Benchmark-Panel.
	 */
	public BenchmarkPanel() {
		initTopPane();
		initTablePane();
	}

	/**
	 * Initialisiere die obere Leiste, in welcher Messergebnisse und Einschätzungen geladen,<br />
	 * eine Formel eingegeben (berechnet oder durch ausprobieren der Platzhalter ausgewertet),<br />
	 * die Bewertungsergebnisse angezeigt werden.
	 */
	private void initTopPane() {
		setLayout(new BorderLayout());
		JPanel topPane = new JPanel(new GridLayout(12, 2));

		initResultSelectionPanel(topPane);
		initEstimationsSelectionPanel(topPane);

		topPane.add(new JLabel("Variables:"));
		variablesArea = new JTextArea(1, 30);
		variablesArea.setEditable(false);
		variablesArea.setLineWrap(true);
		topPane.add(new JScrollPane(variablesArea));

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

		topPane.add(new JLabel("Option:"));
		zeroIsInvalidCheckbox = new JCheckBox("Zero is invalid", false);
		topPane.add(zeroIsInvalidCheckbox);

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

		JButton enumerateBtn = new JButton("Enumerate");
		enumerateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String winner = null;
				try {
					winner = Benchmark.enumerate(equationField.getText(), metricsData, respProjs, mode, zeroIsInvalidCheckbox.isSelected());
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				if(winner == null) {
					return;
				}

				equationField.setText(winner);
				eqtnChanged();
			}
		});
		equationPanel.add(enumerateBtn);

		topPane.add(equationPanel);

		topPane.add(new JLabel("# Correct predictions:"));
		numCorrLbl = new JLabel("0");
		topPane.add(numCorrLbl);

		topPane.add(new JLabel("Total number of estimations:"));
		numEstimationsLbl = new JLabel("0");
		topPane.add(numEstimationsLbl);

		topPane.add(new JLabel("Percentage of correct orderings:"));
		percCorrLbl = new JLabel("0%");
		topPane.add(percCorrLbl);

		topPane.add(new JLabel("# Correct weighted:"));
		weightedNumCorrLbl = new JLabel("0");
		topPane.add(weightedNumCorrLbl);

		topPane.add(new JLabel("Total weight sum:"));
		weightSumLbl = new JLabel("0");
		topPane.add(weightSumLbl);

		topPane.add(new JLabel("Percentage of weighted correct orderings:"));
		percWeightCorrLbl = new JLabel("0%");
		topPane.add(percWeightCorrLbl);

		add(topPane, BorderLayout.NORTH);
	}

	private static String selectCsvMessage(String colNames, int width) {
		return "<html><div style=\"width:"+width+"px;\">Select CSV file with column headers: " + colNames +"</div></html>";
	}

	/**
	 * Einschätzungspanel mit Label für Namen der gewählten Datei und Auswahlbutton zum Öffnen des Lade-Dialogs.
	 * @param topPane obere Leiste des Benchmark-Panels.
	 */
	private void initEstimationsSelectionPanel(JPanel topPane) {
		topPane.add(new JLabel("Estimations:"));
		JPanel esp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPane.add(esp);
		estimationsLbl = new JLabel(selectCsvMessage(SurveyFormat.ESTIMATION_COLUMN_HEADERS, (int) (topPane.getWidth()/2.0f)));
		esp.add(estimationsLbl);
		esp.add(getBrowseButton(BenchInputType.Estimations));
	}

	/**
	 * Ergebniswahlpanel mit Label für Namen der gewählten Datei und Auswahlbutton zum Öffnen des Lade-Dialogs.
	 * @param topPane obere Leiste des Benchmark-Panels.
	 */
	private void initResultSelectionPanel(JPanel topPane) {
		topPane.add(new JLabel("Metric results:"));
		JPanel mrp = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPane.add(mrp);
		metricResultsLbl = new JLabel(selectCsvMessage(SurveyFormat.METRIC_RESULTS_COLUMN_HEADERS, (int) (topPane.getWidth()/2.0f)));
		mrp.add(metricResultsLbl);
		mrp.add(getBrowseButton(BenchInputType.MetricResults));
	}

	/**
	 * Eingabetypen: Einschätzungen aus Umfrage oder Messergebnisse.
	 */
	private static enum BenchInputType {
		Estimations,
		MetricResults
	}

	/**
	 * Erzeuge Lade-Button für Messergebnisse oder Umfrage.
	 * @param inputType Eingabetyp.
	 * @return neuen Button zum Laden.
	 */
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
							numEstimationsLbl.setText(""+respProjs.size());

							weightSum = 0;
							for(ResponseProjects rps : respProjs) {
								weightSum += rps.weight;
							}
							weightSumLbl.setText(""+weightSum);

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
							variablesArea.setText(sb.toString());
							List<ProjectWithResults> pwrLst = ProjectWithResults.fromCsv(data);
							metricsData = new HashMap<Project, ProjectWithResults>();
							for(ProjectWithResults pwr : pwrLst) {
								metricsData.put(pwr.project, pwr);
							}
							break;
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		return btn;
	}

	/**
	 * Initialisiere den unteren Bereichs des Benchmark-Panels mit einer Tabelle von Schätzungen und Vorhersagen.
	 */
	private void initTablePane() {
		JPanel tablePane = new JPanel(new GridLayout(1,1));
		tablePane.add(new JScrollPane(benchmarkTable));
		benchmarkTable.setDefaultRenderer(Object.class, new BenchmarkTableCellRenderer());
		benchmarkTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				int selRowIndex = benchmarkTable.getSelectedRow();
				int selColIndex = benchmarkTable.getSelectedColumn();

				String user = (String)benchmarkTableModel.getValueAt(selRowIndex, 0);

				if(selColIndex == 0) {
					new UserStatsWindow(user).setVisible(true);
					return;
				}

				Object cellVal = benchmarkTableModel.getValueAt(selRowIndex, selColIndex);
				if(!(cellVal instanceof String)) {
					return;
				}
				String repoName = (String)cellVal;

				Project p = new Project(user, repoName);
				if(metricsData.containsKey(p)) {
					new ProjectMetricsWindow(metricsData.get(p)).setVisible(true);
				}
			}
		});
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

		Benchmark.PredictionMethods predMethods = new PredictionMethodsFromString(equationField.getText());

		try {
			Benchmark.Quality q = Benchmark.runBenchmark(predMethods, metricsData, respProjs, zeroIsInvalidCheckbox.isSelected());

			int ncorr;
			double wncorr;
			int napplicable;
			double nwapplicable;
			switch(mode) {
				default:
				case TestEffort:
					ncorr = q.teCorrect;
					wncorr = q.teWeightedCorrect;
					napplicable = q.numTeApplicable;
					nwapplicable = q.numTeWeightedApplicable;
					break;
				case BugCount:
					ncorr = q.bcCorrect;
					wncorr = q.bcWeightedCorrect;
					napplicable = q.numBcApplicable;
					nwapplicable = q.numBcWeightedApplicable;
					break;
			}

			numCorrLbl.setText("" + ncorr);
			weightedNumCorrLbl.setText(""+wncorr);
			percCorrLbl.setText(String.format("%.2f", (double)ncorr/(double)napplicable*100.0) + "%");
			percWeightCorrLbl.setText(String.format("%.2f", wncorr/nwapplicable*100.0) + "%");

			weightSumLbl.setText(""+nwapplicable);
			numEstimationsLbl.setText(""+napplicable);

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
