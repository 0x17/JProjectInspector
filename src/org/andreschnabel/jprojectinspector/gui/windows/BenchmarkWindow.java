package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.BenchmarkPanel;

import javax.swing.*;

public class BenchmarkWindow extends AbstractWindowWithParent<BenchmarkPanel> {

	public BenchmarkWindow(JFrame parentFrame) {
		super("Benchmark Predictors", 800, 600, JFrame.DISPOSE_ON_CLOSE, new BenchmarkPanel(), parentFrame);
	}

	public static void main(String[] args) {
		BenchmarkWindow bw = new BenchmarkWindow(null);
		bw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bw.setVisible(true);
	}
}
