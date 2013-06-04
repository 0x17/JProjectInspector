package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.tables.CsvTableModel;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.pecker.serialization.CsvData;
import org.andreschnabel.pecker.serialization.CsvHelpers;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CsvTableModelTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "csvtablewindow";
					}

					@Override
					public void invoke() throws Exception {
						frm.setLayout(new GridLayout(1,1));
						CsvData data = CsvHelpers.parseCsv(new File("data/responses500.csv"));
						JTable table = new JTable(new CsvTableModel(data));
						frm.add(new JScrollPane(table));
						frm.setSize(1024, 768);
						frm.setLocationRelativeTo(null);
					}
				}
		};
	}
}
