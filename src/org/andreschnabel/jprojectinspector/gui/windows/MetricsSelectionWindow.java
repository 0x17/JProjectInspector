package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.tables.InputProjectTablePanel;
import org.andreschnabel.jprojectinspector.metrics.MetricsRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MetricsSelectionWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private final JList metricNamesLst;

	public MetricsSelectionWindow(final InputProjectTablePanel projLstPanel) {
		super("Metrics Selection");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setLayout(new BorderLayout());

		JLabel topLbl = new JLabel("Select metrics to measure. Try cmd+click and shift+click.");
		add(topLbl, BorderLayout.NORTH);

		List<String> ms = MetricsRegistry.listAllMetrics();
		metricNamesLst = new JList(ms.toArray(new String[]{}));
		add(new JScrollPane(metricNamesLst), BorderLayout.CENTER);

		JButton measureBtn = new JButton("Measure");
		measureBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				MetricResultsWindow metricResultsWindow = new MetricResultsWindow(projLstPanel.getProjects(), getSelectedMetrics());
				metricResultsWindow.setVisible(true);
			}
		});
		add(measureBtn, BorderLayout.SOUTH);

		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);
		setAlwaysOnTop(true);
	}

	public List<String> getSelectedMetrics() {
		int[] selIndices = metricNamesLst.getSelectedIndices();
		List<String> mxNames = new ArrayList<String>(selIndices.length);
		List<String> allMx = MetricsRegistry.listAllMetrics();
		for(int ix : selIndices) {
			mxNames.add(allMx.get(ix));
		}
		return mxNames;
	}

}
