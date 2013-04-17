package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.tables.CsvTableModel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.TimelineTapper;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.functional.TestCallback;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TimelineTapperPlausibilityTest extends VisualTest {
	@Override
	protected TestCallback[] getTests() {
		return new TestCallback[] {
			new TestCallback() {
				@Override
				public String getDescription() {
					return "timeline tapper plausiblity";
				}

				@Override
				public void invoke() throws Exception {
					JFrame frm = getTestFrame();
					frm.setLayout(new GridLayout(1,1));
					List<Project> projs = TimelineTapper.tapProjects();
					CsvData data = Project.projectListToCsv(projs);
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
