package org.andreschnabel.jprojectinspector.tests.visual;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.gui.panels.CsvTablePanel;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.tests.VisualTestCallback;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.*;
import java.io.File;

public class GuiHelpersTest extends VisualTest {
	@Override
	protected VisualTestCallback[] getTests() {
		return new VisualTestCallback[] {
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "Nimbus laf";
					}

					@Override
					public void invoke() throws Exception {
						LookAndFeel laf = UIManager.getLookAndFeel();
						GuiHelpers.setNimbusLaf();
						frm.add(new JButton("Test"));
						UIManager.setLookAndFeel(laf);
					}
				},
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "Open URL";
					}

					@Override
					public void invoke() throws Exception {
						GuiHelpers.openUrl("https://www.github.com/0x17/JProjectInspector");
						frm.dispose();
					}
				},
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "Show PDF";
					}

					@Override
					public void invoke() throws Exception {
						GuiHelpers.showPdf(new File("Manual.pdf"));
						frm.dispose();
					}
				},
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "Load CSV dialog. Please open a file!";
					}

					@Override
					public void invoke() throws Exception {
						CsvData csvData = GuiHelpers.loadCsvDialog(new File("."));
						frm.add(new CsvTablePanel(csvData));
						frm.pack();
					}
				},
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "Show error";
					}

					@Override
					public void invoke() throws Exception {
						GuiHelpers.showError("This is some error, isn't it? Actually everything works fine. :)");
					}
				},
				new VisualTestCallback() {
					@Override
					public String getDescription() {
						return "Save string with file dialog";
					}

					@Override
					public void invoke() throws Exception {
						File file = GuiHelpers.saveStringWithFileDialog("Hallo!", new File("."), "txt");
						Assert.assertNotNull(file);
						Assert.assertEquals("txt", FileHelpers.extension(file));
						String readTxt = FileHelpers.readEntireFile(file);
						Assert.assertEquals("Hallo!", readTxt);
						Assert.assertTrue(file.delete());
						frm = getTestFrame();
						frm.add(new JLabel("Wrote: Hallo!"));
						frm.add(new JLabel("Read: " + readTxt));
					}
				}
		};
	}
}
