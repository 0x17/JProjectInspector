package org.andreschnabel.jprojectinspector.gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import org.andreschnabel.jprojectinspector.metrics.MetricsRegistry;

public class MetricsSelectionWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public MetricsSelectionWindow() {
		super("Metrics Selection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		JLabel topLbl = new JLabel("Select metrics to measure. Try cmd+click and shift+click.");
		add(topLbl, BorderLayout.NORTH);

		List<String> ms = MetricsRegistry.listAllMetrics();
		JList metricNamesLst = new JList(ms.toArray(new String[] {}));
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
