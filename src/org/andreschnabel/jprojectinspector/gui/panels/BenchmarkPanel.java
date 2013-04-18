package org.andreschnabel.jprojectinspector.gui.panels;

import javax.swing.*;
import java.awt.*;

public class BenchmarkPanel extends PanelWithParent {

	public BenchmarkPanel() {
		initTopPane();
		initTablePane();
	}

	private void initTopPane() {
		setLayout(new BorderLayout());
		JPanel topPane = new JPanel(new GridLayout(6, 2));

		topPane.add(new JLabel("Metric results:"));
		topPane.add(new JTextField("results.csv"));

		topPane.add(new JLabel("Estimations:"));
		topPane.add(new JTextField("estimations.csv"));

		topPane.add(new JLabel("Prediction equation:"));
		topPane.add(new JTextField("x/y"));

		topPane.add(new JLabel("# Correct predictions:"));
		topPane.add(new JLabel("0"));

		topPane.add(new JLabel("Total number of estimations:"));
		topPane.add(new JLabel("80"));

		topPane.add(new JLabel("Percentage:"));
		topPane.add(new JLabel("0%"));

		add(topPane, BorderLayout.NORTH);
	}

	private void initTablePane() {
		JPanel tablePane = new JPanel();
		tablePane.add(new JTable());
		add(tablePane, BorderLayout.CENTER);
	}
}
