package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Hilfsfunktionen für den Umgang mit Prozessen.
 */
public class ProcessHelpers {
	/**
	 * Führe Prozess in gegebenem Arbeitsverzeichnis aus.<br />
	 * Kommando und Parameter werden in String-Array "command" übergeben.<br />
	 * Versucht unter Windows bei Scheitern noch mit Präfix "cmd /c".
	 * @param workDir Arbeitsverzeichnis zur Ausführung des Befehls.
	 * @param command Array aus [Befehl,Parameter1,...,ParameterN].
	 * @return Ausgabe von Befehl auf stderr und stdout.
	 * @throws Exception
	 */
	public static String monitorProcess(File workDir, String... command) throws Exception {
		try {
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.directory(workDir);

			pb.redirectErrorStream(true);
			Process p = pb.start();
			StreamTapper st = new StreamTapper(p.getInputStream());
			st.run();

			p.waitFor();

			return st.getOutput();
		} catch(Exception e) {
			if(Helpers.runningOnUnix() || command[0].equals("cmd")) {
				throw e;
			} else {
				try {
					return tryWithCmdPrefix(workDir, command);
				} catch(Exception e2) {
					throw e2;
				}
			}
		}
	}

	/**
	 * Windows durchsucht sonst PATH nicht.
	 * @param workDir Arbeitsverzeichnis.
	 * @param command [Befehl,Param1,...,ParamN].
	 * @return Ausgabe in stdout vereinigt mit stderr.
	 * @throws Exception
	 */
	private static String tryWithCmdPrefix(File workDir, String[] command) throws Exception {
		String[] cmds = new String[command.length+2];
		cmds[0] = "cmd";
		cmds[1] = "/c";
		for(int i=0; i<command.length; i++) {
			cmds[2+i] = command[i];
		}
		return monitorProcess(workDir, cmds);
	}

	/**
	 * Führe Befehl mit Parametern in Zeichenkette aus.
	 * Gebe Ausgabe auf stdout aus.
	 * @param cmd Befehl (evtl. mit Parametern) "befehl param1 param2 ... paramN"
	 */
	public static void system(String cmd) {
		System.out.println("Running: " + cmd);
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			StreamTapper st = new StreamTapper(p.getInputStream());
			st.run();

			p.waitFor();

			System.out.print(st.getOutput());
		} catch(Exception e) {

			if(Helpers.runningOnUnix() || cmd.split(" ")[0].equals("cmd")) {
				System.out.println("Error executing: " + cmd);
				e.printStackTrace();
			} else {
				try {
					trySystemWithCmdPrefix(cmd);
				} catch(Exception e2) {
					System.out.println("Error executing: " + cmd);
					e.printStackTrace();
				}
			}
		}
	}

	private static void trySystemWithCmdPrefix(String cmd) {
		system("cmd /c " + cmd);
	}

	/**
	 * Führe Befehl in Arbeitsverzeichnis. Befehl und Parameter in Array.
	 * Gebe Ausgabe von stdout und stderr direkt weiter an stdout.
	 * @param workDir Arbeitsverzeichnis
	 * @param command [Befehl,Param1,...,ParamN]
	 * @throws Exception
	 */
	public static void system(File workDir, String... command) throws Exception {
		try {
			System.out.print("Running: \"");
			for(String cmd : command) {
				System.out.print(cmd + " ");
			}
			System.out.print("\" in " + workDir.getAbsolutePath() + "\n");

			ProcessBuilder pb = new ProcessBuilder(command);
			pb.directory(workDir);

			pb.redirectErrorStream(true);
			Process p = pb.start();
			StreamTapper st = new StreamTapper(p.getInputStream(), true);
			st.run();

			p.waitFor();
		} catch(Exception e) {
			if(Helpers.runningOnUnix() || command[0].equals("cmd")) {
				throw e;
			} else {
				try {
					trySystemWithCmdPrefix(workDir, command);
				} catch(Exception e2) {
					throw e2;
				}
			}
		}
	}

	private static void trySystemWithCmdPrefix(File workDir, String[] command) throws Exception {
		String[] cmds = new String[command.length+2];
		cmds[0] = "cmd";
		cmds[1] = "/c";
		for(int i=0; i<command.length; i++) {
			cmds[2+i] = command[i];
		}
		system(workDir, cmds);
	}

	private static class StreamTapper extends Thread {
		private InputStream is;
		private boolean forwardToStdOut;
		private String output;
		private boolean done;

		private StreamTapper(InputStream is) {
			this(is, false);
		}

		private StreamTapper(InputStream is, boolean forwardToStdOut) {
			this.is = is;
			this.forwardToStdOut = forwardToStdOut;
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
					if(forwardToStdOut) {
						System.out.append((char)c);
					}
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

}
