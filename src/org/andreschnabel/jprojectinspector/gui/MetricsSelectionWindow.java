package org.andreschnabel.jprojectinspector.gui;

import org.andreschnabel.jprojectinspector.metrics.MetricsRegistry;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MetricsSelectionWindow extends JFrame {

	public MetricsSelectionWindow() {
		super("Metrics Selection");
		setLayout(new BorderLayout());

		JLabel topLbl = new JLabel("Select metrics to measure. Try cmd+click and shift+click.");
		add(topLbl, BorderLayout.NORTH);

		List<String> ms = MetricsRegistry.listAllMetrics();
		JList<String> metricNamesLst = new JList<String>(ms.toArray(new String[] {}));
		add(new JScrollPane(metricNamesLst), BorderLayout.CENTER);

		JButton measureBtn = new JButton("Measure");
		add(measureBtn, BorderLayout.SOUTH);

		setSize(400, 300);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		new MetricsSelectionWindow().setVisible(true);
	}


}
