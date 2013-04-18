package org.andreschnabel.jprojectinspector.tests.visual;

import junit.framework.Assert;
import org.andreschnabel.jprojectinspector.gui.panels.CsvTablePanel;
import org.andreschnabel.jprojectinspector.tests.VisualTest;
import org.andreschnabel.jprojectinspector.utilities.functional.TestCallback;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.*;
import java.io.File;

public class GuiHelpersTest extends VisualTest {
	@Override
	protected TestCallback[] getTests() {
		return new TestCallback[] {
				new TestCallback() {
					@Override
					public String getDescription() {
						return "Nimbus laf";
					}

					@Override
					public void invoke() throws Exception {
						LookAndFeel laf = UIManager.getLookAndFeel();
						GuiHelpers.setNimbusLaf();
						JFrame frm = getTestFrame();
						frm.add(new JButton("Test"));
						waitForFrameToClose(frm);
						UIManager.setLookAndFeel(laf);
					}
				},
				new TestCallback() {
					@Override
					public String getDescription() {
						return "Open URL";
					}

					@Override
					public void invoke() throws Exception {
						GuiHelpers.openUrl("https://www.github.com/0x17/JProjectInspector");
					}
				},
				new TestCallback() {
					@Override
					public String getDescription() {
						return "Show PDF";
					}

					@Override
					public void invoke() throws Exception {
						GuiHelpers.showPdf(new File("Manual.pdf"));
					}
				},
				new TestCallback() {
					@Override
					public String getDescription() {
						return "Load CSV dialog";
					}

					@Override
					public void invoke() throws Exception {
						JFrame testFrame = getTestFrame();
						CsvData csvData = GuiHelpers.loadCsvDialog(new File("."));
						testFrame.add(new CsvTablePanel(csvData));
						testFrame.pack();
						waitForFrameToClose(testFrame);
					}
				},
				new TestCallback() {
					@Override
					public String getDescription() {
						return "Show error";
					}

					@Override
					public void invoke() throws Exception {
						GuiHelpers.showError("This is some error, isn't it? Actually everything works fine. :)");
					}
				},
				new TestCallback() {
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
						JFrame tf = getTestFrame();
						tf.add(new JLabel("Wrote: Hallo!"));
						tf.add(new JLabel("Read: " + readTxt));
						waitForFrameToClose(tf);
					}
				}
		};
	}
}
