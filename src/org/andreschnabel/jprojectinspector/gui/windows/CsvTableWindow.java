package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.CsvTablePanel;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import javax.swing.*;
import java.io.File;

public class CsvTableWindow extends AbstractWindow<CsvTablePanel> {
	
	private static final long serialVersionUID = 1L;

	public CsvTableWindow(final CsvData data) {
		super(data.title, 640, 480, JFrame.DISPOSE_ON_CLOSE, new CsvTablePanel(data));
	}

	public static void main(String[] args) throws Exception {
		new CsvTableWindow(CsvHelpers.parseCsv(new File("data/responses500.csv"))).setVisible(true);
	}

}
