package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.gui.listeners.QuitOnEscapeKeyListener;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.StringConcatenationOp;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvData;
import org.andreschnabel.jprojectinspector.utilities.serialization.CsvHelpers;

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

	public static void saveStringWithFileDialog(String str, File path, final String... extensions) throws Exception {
		File selectedFile = saveFileDialog(path, extensions);
		if(selectedFile != null) {
			FileHelpers.writeStrToFile(str, selectedFile);
		}
	}

	public static void showError(String msg) {
		JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public static void saveCsvDialog(File path, CsvData csvData) throws Exception {
		saveStringWithFileDialog(csvData.toString(), path, "csv");
	}

	public static CsvData loadCsvDialog(File path) throws Exception {
		File selectedFile = loadFileDialog(path, "csv");
		if(selectedFile != null) {
			String s = FileHelpers.readEntireFile(selectedFile);
			CsvData data = (s != null) ? CsvHelpers.parseCsv(s) : null;
			if(data != null) {
				data.title = selectedFile.getName();
			}
			return data;
		}
		return null;
	}

	private static String loadStringWithFileDialog(File path, final String... extensions) throws Exception {
		File selectedFile = loadFileDialog(path, extensions);
		if(selectedFile != null) {
			return FileHelpers.readEntireFile(selectedFile);
		}
		return null;
	}

	private static File loadFileDialog(File path, String... extensions) {
		return fileDialogCommon(DialogType.Load, path, extensions);
	}

	public static File saveFileDialog(File path, final String... extensions) {
		return fileDialogCommon(DialogType.Save, path, extensions);
	}

	public static void showPdf(File file) {
		if(!Helpers.runningOnUnix()) {
			try {
				String call = "rundll32 SHELL32.DLL,ShellExec_RunDLL "+file.getAbsolutePath();
				Runtime.getRuntime().exec(call);
			} catch (Exception e1) {
				showError("Unable to open " + file.getName() + "!");
			}
		}
	}

	public static CsvData loadCsvDialog(File path, String[] expectedHeaders) throws Exception {
		String str = loadStringWithFileDialog(path, "csv");
		if(str == null) {
			return null;
		}
		validateHeaders(expectedHeaders, CsvHelpers.parseCsv(StringHelpers.firstLine(str)).getHeaders());
		CsvData data = CsvHelpers.parseCsv(str);
		return data;
	}

	private static void validateHeaders(String[] expectedHeaders, String[] headers) throws Exception {
		String failMsg = null;

		if(expectedHeaders.length != headers.length) {
			failMsg = "Wrong number of columns.";
		}

		for(int i=0; i<expectedHeaders.length; i++) {
			if(!headers[i].equals(expectedHeaders[i])) {
				failMsg = "Headers don't match at column " + (i+1) + ": " + headers[i] + " should be " + expectedHeaders[i];
				break;
			}
		}

		if(failMsg != null) {
			String expHeadersStr = Func.reduce(new StringConcatenationOp(","), "", expectedHeaders).substring(1);
			String actualHeadersStr = Func.reduce(new StringConcatenationOp(","), "", headers).substring(1);
			throw new Exception(failMsg + "\nExpected \"" + expHeadersStr + "\" but got \"" + actualHeadersStr + "\"");
		}
	}

	public static enum DialogType {
		Load,
		Save
	}

	public static File fileDialogCommon(DialogType dt, File path, final String... extensions) {
		JFileChooser chooser = new JFileChooser(path);
		for(final String extension : extensions) {
			chooser.addChoosableFileFilter(new FileFilter() {
				@Override
				public boolean accept(File file) {
					return FileHelpers.extension(file).equals(extension) || file.isDirectory();
				}

				@Override
				public String getDescription() {
					return extension;
				}
			});
		}

		chooser.setFileFilter(chooser.getChoosableFileFilters()[1]);

		int state;
		switch(dt) {
			case Load:
				state = chooser.showOpenDialog(null);
				break;
			case Save:
				state = chooser.showSaveDialog(null);
				break;
			default:
				state = JFileChooser.ERROR_OPTION;
				break;
		}

		switch(state) {
			case JFileChooser.APPROVE_OPTION:
				File file = chooser.getSelectedFile();
				if(extensions.length == 1 && !FileHelpers.extension(file).equals(extensions[0])) {
					file = new File(file.getPath() + "." + extensions[0]);
				}
				return file;
			case JFileChooser.CANCEL_OPTION:
			case JFileChooser.ERROR_OPTION:
			default:
				return null;
		}
	}

	public static void addKeyListenerToPanel(JPanel p) {
		QuitOnEscapeKeyListener keyListener = new QuitOnEscapeKeyListener();
		for(Component c : p.getComponents()) {
			c.addKeyListener(keyListener);
		}
	}
}
