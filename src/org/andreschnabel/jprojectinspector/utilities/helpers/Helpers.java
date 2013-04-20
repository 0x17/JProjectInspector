
package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Helpers {
	
	public static boolean runningOnUnix() {
		return !System.getProperty("os.name").toLowerCase().contains("win");
	}

	public static String loadUrlIntoStr(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
		StringBuilder builder = new StringBuilder();
		while(br.ready()) {
			builder.append(br.readLine()).append("\n");
		}
		br.close();
		return builder.toString();
	}

	public static String loadHTMLUrlIntoStr(String urlStr) throws Exception {
		// Taken from: http://stackoverflow.com/questions/1381617/simplest-way-to-correctly-load-html-from-web-page-into-a-string-in-java
		URL url = new URL(urlStr);
		URLConnection con = url.openConnection();
		Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
		Matcher m = p.matcher(con.getContentType());
		/* If Content-Type doesn't match this pre-conception, choose default and 
		 * hope for the best. */
		String charset = m.matches() ? m.group(1) : "ISO-8859-1";
		Reader r = new InputStreamReader(con.getInputStream(), charset);
		StringBuilder buf = new StringBuilder();
		while(true) {
			int ch = r.read();
			if(ch < 0)
				break;
			buf.append((char) ch);
		}
		return buf.toString();
	}

	public static String prompt(String string) throws Exception {
		System.out.print(string + ": ");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		return br.readLine();
	}

	public static String promptPw(String string) throws Exception {
		final boolean RUNNING_IN_STUPID_ECLIPSE = true;
		if(RUNNING_IN_STUPID_ECLIPSE) {
			return prompt(string);
		} else {
			System.out.print(string + ": ");
			Console c = System.console();
			char[] pw = c.readPassword();
			return new String(pw);
		}
	}

	public static void log(String msg) {
		System.out.println(msg);
		System.out.flush();
	}

	public static String executableExtension() {
		return runningOnUnix() ? "" : ".exe";
	}

	public static String unixType(String cmd) throws Exception {
		String[] parts = ProcessHelpers.monitorProcess(new File("."), "type", cmd).split(" ");
		return parts[parts.length-1].trim();
	}
}
