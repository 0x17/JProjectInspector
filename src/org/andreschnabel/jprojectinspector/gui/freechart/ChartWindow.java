package org.andreschnabel.jprojectinspector.gui.freechart;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ChartWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	public ChartWindow(String title, final JFreeChart chart, final Dimension dim) {
		super(title);
		setLayout(new GridBagLayout());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 0;
		gbc.weighty = 1;
		JButton exportBtn = new JButton("Export");
		exportBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				File file = GuiHelpers.saveFileDialog(new File("."), "png", "pdf");
				if(file != null) {
					String ext = FileHelpers.extension(file);
					try {
						if(ext.equals("png")) {
							FreeChartExporter.saveChartToPNG(chart, file, dim.width, dim.height);
						} else if(ext.equals("pdf")) {
							FreeChartExporter.saveChartToPDF(chart, file, dim.width, dim.height);
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		add(exportBtn, gbc);

		ChartPanel cp = new ChartPanel(chart);
		cp.setPreferredSize(dim);

		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = gbc.weighty = 1;
		add(cp, gbc);

		pack();
		setLocationRelativeTo(null);
	}

}