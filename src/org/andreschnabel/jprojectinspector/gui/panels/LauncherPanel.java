package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.windows.BenchmarkWindow;
import org.andreschnabel.jprojectinspector.gui.windows.InputWindow;
import org.andreschnabel.jprojectinspector.gui.windows.MetricResultsWindow;
import org.andreschnabel.jprojectinspector.gui.windows.SettingsWindow;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class LauncherPanel extends PanelWithParent implements ILaunchSettings {
	private static final long serialVersionUID = 1L;
	
	private static final String ABOUT_TEXT = "<html><h2>JProjectInspector</h2><br />" +
			"Author: André Schnabel (andreschnabel@me.com)<br />" +
			"License: BSD<br />" +
			"<br />Third party libraries:<br />" +
			"ASM (http://asm.ow2.org/)<br />" +
			"org.eclipse.egit.github.core (http://www.eclipse.org/egit/)<br />" +
			"google-gson (https://code.google.com/p/google-gson/)<br />" +
			"JCommon (http://www.jfree.org/jcommon/)<br />" +
			"JFreeChart (http://www.jfree.org/jfreechart/)<br />" +
			"iText (http://itextpdf.com/)<br />" +
			"<br />Third party tools:<br />" +
			"CLOC (http://cloc.sourceforge.net/)<br />" +
			"git (http://git-scm.com/)<br />" +
			"<br /><i>Omnia sub specie aeternitatis.</i></html>";

	private static class LauncherButton {
		public String text;
		public String descr;
		public ActionListener actionListener;

		private LauncherButton(String text, String descr, ActionListener actionListener) {
			this.text = text;
			this.descr = descr;
			this.actionListener = actionListener;
		}
	}

	public LauncherPanel() {
		setLayout(new BorderLayout());
		initInfoText();
		initButtonPanel();
	}

	private void initInfoText() {
		String name = System.getProperty("user.name");
		String infoText = "<html><div align=\"center\">Welcome to <i>JProjectInspector</i>!<br />What do you want to do, "+name+"?</div></html>";
		JLabel infoLbl = new JLabel(infoText);
		infoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		add(infoLbl, BorderLayout.NORTH);
	}

	private void initButtonPanel() {
		List<LauncherButton> btns = initLauncherButtons();

		JPanel buttonPanel = new JPanel(new GridLayout(btns.size(), 1));
		add(buttonPanel, BorderLayout.CENTER);

		for(LauncherButton lbtn : btns) {
			JButton btn = new JButton(lbtn.text);
			btn.setToolTipText(lbtn.descr);
			btn.addActionListener(lbtn.actionListener);
			buttonPanel.add(btn);
		}
	}

	private List<LauncherButton> initLauncherButtons() {
		List<LauncherButton> btns = new LinkedList<LauncherButton>();
		btns.add(new LauncherButton("New Measurement", "Select input projects and measure selected metrics for them.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				InputWindow iw = new InputWindow(parentFrame);
				iw.setVisible(true);
				parentFrame.setVisible(false);
			}
		}));
		btns.add(new LauncherButton("Load Measurement", "Import already measured metric values from a CSV file.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CsvData results = GuiHelpers.loadCsvDialog(new File("."));
					if(results == null) {
						return;
					}

					String[] headers = results.getHeaders();
					if(headers.length < 2 || !headers[0].equals("owner") || !headers[1].equals("repo")) {
						throw new Exception("CSV format incorrect for metric results.");
					}
					MetricResultsWindow mrw = new MetricResultsWindow(results, parentFrame);
					mrw.setVisible(true);
				} catch(Exception exception) {
					exception.printStackTrace();
					GuiHelpers.showError(exception.getMessage());
				}
			}
		}));
		btns.add(new LauncherButton("Benchmark Predictors", "Evaluate quality of test effort / bug count estimation predictors using survey results.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BenchmarkWindow bw = new BenchmarkWindow(parentFrame);
				bw.setVisible(true);
				parentFrame.setVisible(false);
			}
		}));
		btns.add(new LauncherButton("Settings", "Set executable and temporary directory paths.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tryShowSettingsWindow();
			}
		}));
		btns.add(new LauncherButton("Documentation", "Show introductory documentation for this tool.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiHelpers.showPdf(new File("Manual.pdf"));
			}
		}));
		btns.add(new LauncherButton("Website", "GitHub profile of this project.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					GuiHelpers.openUrl("https://github.com/0x17/JProjectInspector");
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}));
		btns.add(new LauncherButton("About", "Show license information and credits.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, ABOUT_TEXT, "About", JOptionPane.INFORMATION_MESSAGE);
			}
		}));
		btns.add(new LauncherButton("Quit", "Terminate this tool.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}));
		return btns;
	}

	private SettingsWindow settingsWindow;
	@Override
	public void closeSettings() {
		settingsWindow = null;
	}
	private void tryShowSettingsWindow() {
		if(settingsWindow == null) {
			settingsWindow = new SettingsWindow(this);
			settingsWindow.setVisible(true);
		}
	}

}
