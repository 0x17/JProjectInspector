package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.ListListModel;
import org.andreschnabel.jprojectinspector.gui.windows.MetricResultsWindow;
import org.andreschnabel.jprojectinspector.metrics.registry.MetricsRegistry;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;


public class MetricsSelectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	private JList availableMetricNamesLst;
	private JList selectedMetricNamesLst;

	private List<String> availableMetricNames = new LinkedList<String>();
	private List<String> selectedMetricNames = new LinkedList<String>();
	private JLabel descriptionLbl;

	@SuppressWarnings({"unchecked", "rawtypes"})
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
		availableMetricNamesLst = new JList(new ListListModel(availableMetricNames));
		availableMetricNamesLst.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setCurDescription(MetricsRegistry.getDescriptionOfMetric(availableMetricNames.get(availableMetricNamesLst.getSelectedIndex())));
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

		selectedMetricNamesLst = new JList(new ListListModel(selectedMetricNames));
		selectedMetricNamesLst.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				setCurDescription(MetricsRegistry.getDescriptionOfMetric(selectedMetricNames.get(selectedMetricNamesLst.getSelectedIndex())));
			}
		});

		rightPanel.add(new JScrollPane(selectedMetricNamesLst), BorderLayout.CENTER);

		add(rightPanel);
	}

	private void setCurDescription(String description) {
		if(descriptionLbl != null) {
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
				if(selIndex == -1) {
					return;
				}
				availableMetricNames.add(availableMetricNames.get(selIndex));
				selectedMetricNames.remove(selIndex);
				updateLists();
			}
		});
		buttonBar.add(metricToLeft);

		JButton measureBtn = new JButton("Measure");
		measureBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
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
				if(selIndex == -1) {
					return;
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
