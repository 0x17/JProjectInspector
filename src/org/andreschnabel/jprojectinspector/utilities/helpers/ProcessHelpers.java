package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessHelpers {

	public static String monitorProcess(File workDir, String... command) throws Exception {
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.directory(workDir);

		pb.redirectErrorStream(true);
		Process p = pb.start();
		InputStreamReader isr = new InputStreamReader(p.getInputStream());

		p.waitFor();

		StringBuilder output = new StringBuilder();
		int c;
		while((c = isr.read()) != -1) {
			output.append((char) c);
		}

		return output.toString();
	}

	public static void system(String cmd) throws Exception {
		System.out.println("Running: " + cmd);
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			InputStreamReader isr = new InputStreamReader(p.getInputStream());

			p.waitFor();

			StringBuilder output = new StringBuilder();
			int c;
			while((c = isr.read()) != -1) {
				output.append((char) c);
			}
			System.out.print(output);

		} catch(IOException e) {
			System.out.println("Error executing: " + cmd);
			e.printStackTrace();
		}
	}

}
