package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.lists.MetricListModel;
import org.andreschnabel.jprojectinspector.gui.windows.MetricResultsWindow;
import org.andreschnabel.jprojectinspector.metrics.MetricType;
import org.andreschnabel.jprojectinspector.metrics.registry.MetricsRegistry;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * Panel zur Auswahl von Metriken für eine Messung von zuvor gewählten Projekten.
 *
 * Auf der linken Seite werden die zur Verfügung stehenden Metriken in einer Liste angezeigt.
 * Auf der rechten Seite werden die für die Messung ausgewählten Metriken in einer Liste angezeigt.
 *
 * Buttons im unteren Bereich erlauben Verschieben zwischen den Listen und Starten der Messung.
 *
 * Oben in der Mitte wird eine Beschreibung der zuletzt ausgewählten Metrik angezeigt.
 *
 */
public class MetricsSelectionPanel extends PanelWithParent {

	private static final long serialVersionUID = 1L;

	private JList availableMetricNamesLst;
	private JList selectedMetricNamesLst;

	private List<String> availableMetricNames = new LinkedList<String>();
	private List<String> selectedMetricNames = new LinkedList<String>();
	private JLabel descriptionLbl;

	public MetricsSelectionPanel(final InputProjectTablePanel projLstPanel) {
		setLayout(new GridLayout(1, 3));
		initLeftPanel();
		initMiddlePanel(projLstPanel);
		initRightPanel();
	}

	private void initLeftPanel() {
		JPanel leftPanel = new JPanel(new BorderLayout());

		JLabel headerLbl = new JLabel("Available Metrics");
		headerLbl.setHorizontalAlignment(SwingConstants.CENTER);
		leftPanel.add(headerLbl, BorderLayout.NORTH);

		availableMetricNames.addAll(MetricsRegistry.listAllMetrics());
		availableMetricNamesLst = new JList(new MetricListModel(availableMetricNames));
		availableMetricNamesLst.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String metricName = availableMetricNames.get(availableMetricNamesLst.getSelectedIndex());
				setCurDescription(MetricsRegistry.getDescriptionOfMetric(metricName), MetricsRegistry.getTypeOfMetric(metricName));
			}
		});
		leftPanel.add(new JScrollPane(availableMetricNamesLst), BorderLayout.CENTER);

		add(leftPanel);
	}

	private void initRightPanel() {
		JPanel rightPanel = new JPanel(new BorderLayout());

		JLabel headerLbl = new JLabel("Selected Metrics");
		headerLbl.setHorizontalAlignment(SwingConstants.CENTER);
		rightPanel.add(headerLbl, BorderLayout.NORTH);

		selectedMetricNamesLst = new JList(new MetricListModel(selectedMetricNames));
		selectedMetricNamesLst.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String metricName = selectedMetricNames.get(selectedMetricNamesLst.getSelectedIndex());
				setCurDescription(MetricsRegistry.getDescriptionOfMetric(metricName), MetricsRegistry.getTypeOfMetric(metricName));
			}
		});

		rightPanel.add(new JScrollPane(selectedMetricNamesLst), BorderLayout.CENTER);

		add(rightPanel);
	}

	private void setCurDescription(String description, MetricType typeOfMetric) {
		if(descriptionLbl != null) {
			String typeNote;
			switch(typeOfMetric) {
				case Offline:
					typeNote = "Measuring an offline metrics requires cloning of the project's repository. Slow for big projects.";
					break;
				case Online:
					typeNote = "Measuring an online metric doesn't require cloning. Performance invariant of project size.";
					break;
				case Survey:
					typeNote = "Survey metrics require survey result CSV with entry for project to work.";
					break;
				default:
					typeNote = "";
					break;
			}
			description += ("<br /><br />" + typeNote);
			float lblWidth = descriptionLbl.getWidth()/2.0f;
			String htmlFormatStr = "<html><div style=\"width:%.2fpx;\">%s</div><html>";
			String lblText = String.format(htmlFormatStr, lblWidth, description.replace("\n", "<br />"));
			descriptionLbl.setText(lblText);
			descriptionLbl.updateUI();
		}
	}

	private void initMiddlePanel(final InputProjectTablePanel projLstPanel) {
		JPanel middlePanel = new JPanel(new BorderLayout());

		JLabel topLbl = new JLabel("Description");
		topLbl.setHorizontalAlignment(SwingConstants.CENTER);
		middlePanel.add(topLbl, BorderLayout.NORTH);

		descriptionLbl = new JLabel("Select a metric first.");
		descriptionLbl.setVerticalAlignment(SwingConstants.TOP);
		descriptionLbl.setFont(new Font("font", Font.PLAIN, 15));
		middlePanel.add(descriptionLbl, BorderLayout.CENTER);

		JPanel buttonBar = new JPanel(new FlowLayout());

		JButton metricToLeft = new JButton("<<");
		metricToLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selIndex = selectedMetricNamesLst.getSelectedIndex();
				if(selIndex == -1 || selIndex >= selectedMetricNames.size()) {
					return;
				} else if(selIndex == selectedMetricNames.size()-1) {
					selectedMetricNamesLst.setSelectedIndex(selectedMetricNames.size()-2);
				}
				availableMetricNames.add(selectedMetricNames.get(selIndex));
				selectedMetricNames.remove(selIndex);
				updateLists();
			}
		});
		buttonBar.add(metricToLeft);

		JButton measureBtn = new JButton("Measure");
		measureBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selectedMetricNames.isEmpty()) {
					GuiHelpers.showError("No metric selected for measurement!");
					return;
				}
				parentFrame.setVisible(false);
				MetricResultsWindow metricResultsWindow = new MetricResultsWindow(projLstPanel.getProjects(), getSelectedMetrics());
				metricResultsWindow.setVisible(true);
			}
		});
		buttonBar.add(measureBtn);

		JButton metricToRight = new JButton(">>");
		metricToRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selIndex = availableMetricNamesLst.getSelectedIndex();
				if(selIndex == -1 || selIndex >= availableMetricNames.size()) {
					return;
				} else if(selIndex == availableMetricNames.size()-1) {
					availableMetricNamesLst.setSelectedIndex(availableMetricNames.size()-2);
				}
				selectedMetricNames.add(availableMetricNames.get(selIndex));
				availableMetricNames.remove(selIndex);
				updateLists();
			}
		});
		buttonBar.add(metricToRight);

		middlePanel.add(buttonBar, BorderLayout.SOUTH);

		add(middlePanel);
	}

	private void updateLists() {
		availableMetricNamesLst.updateUI();
		selectedMetricNamesLst.updateUI();
	}

	public List<String> getSelectedMetrics() {
		return selectedMetricNames;
	}
}
