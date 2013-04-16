package org.andreschnabel.jprojectinspector.utilities.helpers;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.File;

public class GuiHelpers {

	public static void setNimbusLaf() throws Exception {
		if(Helpers.runningOnUnix()) {
			return;
		}

		for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			if("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}
	}

	public static GridBagConstraints fillHorizontalConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.weightx = 1;
		gbc.weighty = 0;
		return gbc;
	}

	public static GridBagConstraints fillBothConstraints() {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 1;
		return gbc;
	}

	public static File saveFileDialog(File path, final String... extensions) {
		JFileChooser chooser = new JFileChooser(path);
		chooser.addChoosableFileFilter(new FileFilter() {
			@Override
			public boolean accept(File file) {
				String ext = FileHelpers.extension(file);
				for(String allowedExt : extensions) {
					if(ext.equals(allowedExt))
						return true;
				}
				return false;
			}

			@Override
			public String getDescription() {
				StringBuilder sb = new StringBuilder();
				for(String ext : extensions) {
					sb.append(ext + ",");
				}
				return sb.toString();
			}
		});
		int state = chooser.showSaveDialog(null);
		switch(state) {
			case JFileChooser.APPROVE_OPTION:
				return chooser.getSelectedFile();
			case JFileChooser.CANCEL_OPTION:
			case JFileChooser.ERROR_OPTION:
			default:
				return null;
		}
	}

	public static void saveStringWithFileDialog(String str, File path, final String... extensions) throws Exception {
		File selectedFile = saveFileDialog(path, extensions);
		FileHelpers.writeStrToFile(str, selectedFile);
	}
}
