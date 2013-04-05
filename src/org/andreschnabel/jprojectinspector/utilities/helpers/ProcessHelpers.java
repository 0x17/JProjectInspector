package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProcessHelpers {

	private static class StreamTapper extends Thread {
		private InputStream is;
		private String output;
		private boolean done;

		private StreamTapper(InputStream is) {
			this.is = is;
		}

		@Override
		public void run() {
			super.run();
			InputStreamReader isr = new InputStreamReader(is);
			StringBuilder outputBuilder = new StringBuilder();
			int c;
			try {
				while((c = isr.read()) != -1 && !done) {
					outputBuilder.append((char) c);
				}
			} catch(IOException e) {
				e.printStackTrace();
			}

			output = outputBuilder.toString();
		}

		public synchronized String getOutput() {
			done = true;
			return output;
		}
	}

	public static String monitorProcess(File workDir, String... command) throws Exception {
		ProcessBuilder pb = new ProcessBuilder(command);
		pb.directory(workDir);

		pb.redirectErrorStream(true);
		Process p = pb.start();
		StreamTapper st = new StreamTapper(p.getInputStream());
		st.run();

		p.waitFor();

		return st.getOutput();
	}

	public static void system(String cmd) throws Exception {
		System.out.println("Running: " + cmd);
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			StreamTapper st = new StreamTapper(p.getInputStream());
			st.run();

			p.waitFor();

			System.out.print(st.getOutput());

		} catch(IOException e) {
			System.out.println("Error executing: " + cmd);
			e.printStackTrace();
		}
	}

}
