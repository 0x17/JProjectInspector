
package org.andreschnabel.jprojectinspector;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public static List<String> listSourceFiles(String path) {
		File dir = new File(path);
		List<String> srcFilenames = new LinkedList<String>();
		recursiveCollectSrcFiles(srcFilenames, dir);		
		return srcFilenames;
	}
	
	private static void recursiveCollectSrcFiles(List<String> srcFilenames, File dir) {
		for(File f : dir.listFiles()) {
			if(f.isDirectory()) {
				recursiveCollectSrcFiles(srcFilenames, f);
			} else {
				String name = f.getName();
				if(name.endsWith(".java"))
					srcFilenames.add(name);
			}
		}
	}

	public static boolean strContainsOneOf(String str, String... candidates) {
		for(String candidate : candidates)
			if(str.contains(candidate)) return true;
		return false;
	}

	public static int countOccurencesOfWords(String str, String[] words) {
		int sum = 0;
		for(String word : words) {
			sum += countOccurencesOfWord(str, word);
		}
		return sum;
	}

	private static int countOccurencesOfWord(String str, String word) {
		int ctr = 0;
		int j = 0;
		for(int i=0; i<str.length(); i++) {
			if(str.charAt(i) == word.charAt(j)) {
				if(j == word.length() - 1)  {
					ctr++;
					j = 0;
				}
				else
					j++;
			} else {
				j = 0;
			}
		}
		return ctr;
	}

	public static void writeStrToFile(String str, String outFilename) throws IOException {
		FileWriter fw = new FileWriter(outFilename);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.close();
		fw.close();
	}

	public static void deleteDir(File root) throws Exception {
		if(!root.isDirectory())
			throw new Exception("Path must point to dir!");
		
		for(File f : root.listFiles()) {
			if(f.isDirectory()) {
				deleteDir(f);
			} else
				f.delete();
		}
		root.delete();
	}
	
	public static String readEntireFile(File file) throws Exception {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		StringBuilder builder = new StringBuilder();
		while(br.ready()) {
			builder.append(br.readLine() + "\n");
		}
		br.close();
		fr.close();
		return builder.toString();
	}

	public static String prompt(String string) throws Exception {
		System.out.print(string + ": ");
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		String input = br.readLine();
		return input;
	}

	public static String capitalize(String str) {
		char[] chars = str.toCharArray();
		chars[0] = Character.toUpperCase(chars[0]);
		return new String(chars);
	}

	public static boolean strEndsWithOneOf(String str, String... suffixes) {
		for(String suffix : suffixes)
			if(str.endsWith(suffix)) return true;
		
		return false;
	}

	public static boolean equalsOneOf(String str, String... candidates) {
		for(String candidate : candidates)
			if(str.equals(candidate)) return true;
		
		return false;
	}

	public static void rmDir(String destPath) throws Exception {
		if(destPath.startsWith("/tmp/"))
			system("rm -rf " + destPath);
		else throw new Exception("Never leave /tmp/ with rm -rf!");
	}
	
	public static List<String> batchMatchOneGroup(String regex, String input) {
		List<String> result = new LinkedList<String>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()) {
			if(m.groupCount() == 1) {
				result.add(m.group(1));
			}
		}
		return result;
	}
	
	public static List<StringPair> batchMatchTwoGroups(String regex, String input) {
		List<StringPair> result = new LinkedList<StringPair>();
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		while(m.find()) {
			if(m.groupCount() == 2) {
				result.add(new StringPair(m.group(1), m.group(2)));
			}
		}
		return result;
	}

	public static String removeAllWhitespace(String s) {
		return s.replaceAll("\\s", "");
	}
	
	public static String removeWhitespace(String s) {
		return s.replaceAll("[\n\t]*", "");
	}
}
