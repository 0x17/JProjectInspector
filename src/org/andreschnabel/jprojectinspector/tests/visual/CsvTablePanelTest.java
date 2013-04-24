package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.panels.CsvTablePanel;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import java.util.LinkedList;
import java.util.List;

public class CsvTablePanelTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "csv table panel test";
					}

					@Override
					public void invoke() throws Exception {
						List<String[]> rowList = new LinkedList<String[]>();
						rowList.add(new String[] {"name", "age"});
						rowList.add(new String[] {"Peter", "44"});
						rowList.add(new String[] {"Hans", "23"});
						CsvData data = new CsvData(rowList);
						frm.add(new CsvTablePanel(data));
						frm.pack();
					}
				}
		};
	}
}
