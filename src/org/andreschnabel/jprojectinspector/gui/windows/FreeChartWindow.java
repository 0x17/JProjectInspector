package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.FreeChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;

/**
 * Fenster mit FreeChart-Diagramm.
 * @see FreeChartPanel
 */
public class FreeChartWindow extends AbstractWindow<FreeChartPanel> {

	private static final long serialVersionUID = 1L;

	public FreeChartWindow(final JFreeChart chart, final Dimension dim) {
		super(chart.getTitle().getText(), JFrame.DISPOSE_ON_CLOSE, new FreeChartPanel(chart, dim));
	}

}
