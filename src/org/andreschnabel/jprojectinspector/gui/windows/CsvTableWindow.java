package org.andreschnabel.jprojectinspector.gui.windows;

import org.andreschnabel.jprojectinspector.gui.panels.CsvTablePanel;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import javax.swing.*;
import java.io.File;

public class CsvTableWindow extends AbstractWindow<CsvTablePanel> {
	
	private static final long serialVersionUID = 1L;

	public CsvTableWindow(final CsvData data) {
		super(data.title, JFrame.DISPOSE_ON_CLOSE, new CsvTablePanel(data));
	}

	public static void main(String[] args) throws Exception {
		File f = new File("data/responses500.csv");
		CsvData csv = CsvHelpers.parseCsv(f);
		//CsvData csv = GuiHelpers.loadCsvDialog(new File("."));
		CsvTableWindow ctw = new CsvTableWindow(csv);
		ctw.setVisible(true);
		ctw.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ctw.pack();
	}

}
