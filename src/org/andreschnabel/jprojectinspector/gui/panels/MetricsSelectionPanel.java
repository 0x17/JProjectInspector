package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.windows.MetricResultsWindow;
import org.andreschnabel.jprojectinspector.metrics.registry.MetricsRegistry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;


public class MetricsSelectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("rawtypes")
	private final JList metricNamesLst;
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public MetricsSelectionPanel(final InputProjectTablePanel projLstPanel) {
		setLayout(new BorderLayout());

		JLabel topLbl = new JLabel("Select metrics to measure. Try cmd+click and shift+click.");
		add(topLbl, BorderLayout.NORTH);

		java.util.List<String> ms = MetricsRegistry.listAllMetrics();
		
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
