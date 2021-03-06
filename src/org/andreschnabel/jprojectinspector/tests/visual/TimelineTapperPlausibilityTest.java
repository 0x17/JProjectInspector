package org.andreschnabel.jprojectinspector.tests.visual;

import org.andreschnabel.jprojectinspector.gui.tables.CsvTableModel;
import org.andreschnabel.jprojectinspector.model.Project;
import org.andreschnabel.jprojectinspector.scrapers.TimelineTapper;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;
import org.andreschnabel.pecker.serialization.CsvData;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TimelineTapperPlausibilityTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
			new VisualTestCallback() {
				@Override
				public String getDescription() {
					return "timeline tapper plausiblity";
				}

				@Override
				public void invoke() throws Exception {
					frm.setLayout(new GridLayout(1,1));
					List<Project> projs = TimelineTapper.tapProjects();
					CsvData data = Project.projectListToCsv(projs);
					JTable table = new JTable(new CsvTableModel(data));
					frm.add(new JScrollPane(table));
					frm.setSize(1024, 768);
					frm.setLocationRelativeTo(null);
				}
			}
		};
	}
}
