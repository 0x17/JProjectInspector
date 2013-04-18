package org.andreschnabel.jprojectinspector.gui.panels;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.gui.windows.SettingsWindow;
import org.andreschnabel.jprojectinspector.utilities.helpers.GuiHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SettingsPanel extends PanelWithParent {

	private static final long serialVersionUID = 1L;
	
	private final PathPanel[] fields;

	private static class PathPanel extends JPanel {
		private JTextField field;
		private JButton browseBtn;

		public PathPanel(final String path, final String extension, boolean local) {
			setLayout(new FlowLayout(FlowLayout.LEFT));

			field = new JTextField(path);
			field.setColumns(25);
			add(field);

			if(local) {
				browseBtn = new JButton("Browse");
				browseBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						File sf;
						if(extension.equals("")) {
							sf = GuiHelpers.loadDirectoryDialog(new File(path));
						} else {
							sf = GuiHelpers.loadFileDialog(new File(path), extension);
						}
						field.setText(sf.getAbsolutePath());
					}
				});
				add(browseBtn);
			}
		}

		public String getText() {
			return field.getText();
		}
	}

	public SettingsPanel() {
		final String[] settingNames = new String[] { "Temp clone folder", "GitHub base url", "Git executable path", "Perl interpreter path", "Cloc executable path" };
		int nrows = settingNames.length;
		setLayout(new GridLayout(nrows+1, 2));
		JLabel[] lbls = new JLabel[nrows];
		fields = new PathPanel[nrows];

		String[] texts = new String[] {
				Config.DEST_BASE,
				Config.BASE_URL,
				Config.GIT_PATH,
				Config.PERL_PATH,
				Config.CLOC_PATH
		};

		for(int i=0; i<nrows; i++) {
			lbls[i] = new JLabel(settingNames[i]);
			add(lbls[i]);

			String ext = "";

			if(i == 2 || i == 3) {
				ext = Helpers.executableExtension().replace(".", "");
			}
			else if(i == 4) {
				ext = "pl";
			}

			fields[i] = new PathPanel(texts[i], ext, i != 1);

			add(fields[i]);
		}

		setSize(400, 300);

		JButton hideBtn = new JButton("Cancel");
		hideBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		add(hideBtn);

		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
				saveSettings();
				} catch(Exception e1) {
					e1.printStackTrace();
				}
				close();
			}
		});
		add(saveBtn);
	}

	private void close() {
		((SettingsWindow)parentFrame).launcher.closeSettings();
		parentFrame.dispose();
	}

	private void saveSettings() throws IOException {
		Config.DEST_BASE = fields[0].getText();
		Config.BASE_URL = fields[1].getText();
		Config.GIT_PATH = fields[2].getText();
		Config.PERL_PATH = fields[3].getText();
		Config.CLOC_PATH = fields[4].getText();
		Config.persist();
	}
}
