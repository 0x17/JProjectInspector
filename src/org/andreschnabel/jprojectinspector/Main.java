package org.andreschnabel.jprojectinspector;

/**
 * Starter für JProjectInspector. Multiplexed zwischen Oberfläche und Ranking-Tool für Kommandozeile "Ranking"
 */
public class Main {
	/**
	 * Einstiegspunkt<br />
	 * Falls 1. Argument "Ranking" wird TestHubRunner dispatched.<br />
	 * Ansonsten GuiRunner.
	 * @param args Kommandozeilenargumente.
	 */
	public static void main(String[] args) throws Exception {
		if(args.length > 0 && args[0].equals("Ranking")) {
			String[] truncArgs = shiftArgsLeft(args);
			TestHubRunner.main(truncArgs);
		} else {
			GuiRunner.main(args);
		}
	}

	private static String[] shiftArgsLeft(String[] args) {
		String[] truncArgs = new String[args.length-1];
		for(int i=0; i<truncArgs.length; i++) {
			truncArgs[i] = args[i+1];
		}
		return truncArgs;
	}

}
