package org.andreschnabel.jprojectinspector.freechart;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;

public class ChartWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public ChartWindow(String title, JFreeChart chart, Dimension dim) {
		super(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ChartPanel cp = new ChartPanel(chart);
		cp.setPreferredSize(dim);
		setContentPane(cp);
		pack();
		setLocationRelativeTo(null);
	}

}
