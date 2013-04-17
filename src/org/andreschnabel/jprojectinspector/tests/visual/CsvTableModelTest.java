package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.tables.CsvTableModel;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.functional.TestCallback;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class CsvTableModelTest extends VisualTest {
	@Override
	protected TestCallback[] getTests() {
		return new TestCallback[] {
				new TestCallback() {
					@Override
					public String getDescription() {
						return "csvtablewindow";
					}

					@Override
					public void invoke() throws Exception {
						JFrame frm = getTestFrame();
						frm.setLayout(new GridLayout(1,1));
						CsvData data = CsvHelpers.parseCsv(new File("data/responses500.csv"));
						JTable table = new JTable(new CsvTableModel(data));
						frm.add(new JScrollPane(table));
						frm.setSize(1024, 768);
						frm.setLocationRelativeTo(null);
						waitForFrameToClose(frm);
					}
				}
		};
	}
}
