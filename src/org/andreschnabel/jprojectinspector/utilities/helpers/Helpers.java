
package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.junit.Assert;


public class Helpers {
	
	public static void system(String cmd) throws Exception {
		System.out.println("Running: " + cmd);
		try {
			Process p = Runtime.getRuntime().exec(cmd);		
			InputStreamReader isr = new InputStreamReader(p.getInputStream());			
			p.waitFor();
			
			StringBuilder output = new StringBuilder();
			int c;
			while((c = isr.read()) != -1) {
				output.append((char)c);
			}
			System.out.print(output);		
			
		} catch (IOException e) {
			System.out.println("Error executing: " + cmd);
			e.printStackTrace();
		}
	}
	
	public static String loadUrlIntoStr(String urlStr) throws Exception {		
		URL url = new URL(urlStr);
		BufferedReader br = new BufferedReader( new InputStreamReader(url.openStream()));
		StringBuilder builder = new StringBuilder();
		while(br.ready()) {
			builder.append(br.readLine() + "\n");
		}
		br.close();
		return builder.toString();
	}
	
	public static String prompt(String string) throws Exception {
		System.out.print(string + ": ");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String input = br.readLine();
		return input;
	}

	public static <T> void assertListEquals(T[] expectedValues, List<T> actualValues) {
		Assert.assertEquals(expectedValues.length, actualValues.size());
		for(int i=0; i<expectedValues.length; i++) {
			Assert.assertEquals(expectedValues[i], actualValues.get(i));
		}
	}
	
	public static <T> void addToLstNoDups(List<T> lst, T obj) {
		if(!lst.contains(obj))
			lst.add(obj);
	}

	public static void log(String msg) {
		System.out.println(msg);
		System.out.flush();
	}
}
