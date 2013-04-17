package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.gui.windows.InputWindow;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class LauncherPanel extends PanelWithParent {

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
		String infoText = "<html><div align=\"center\">Welcome to <i>JProjectInspector</i>!<br />What do you want to do?</div></html>";
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
				InputWindow iw = new InputWindow();
				iw.setVisible(true);
				parentFrame.setVisible(false);
			}
		}));
		btns.add(new LauncherButton("Load Measurement", "Import already measured metric values from a CSV file.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		}));
		btns.add(new LauncherButton("Benchmark Predictors", "Evaluate quality of test effort / bug count estimation predictors using survey results.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		}));
		btns.add(new LauncherButton("Documentation", "Show introductory documentation for this tool.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GuiHelpers.showPdf(new File("Manual.pdf"));
			}
		}));
		btns.add(new LauncherButton("About", "Show license information and credits.", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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

}
