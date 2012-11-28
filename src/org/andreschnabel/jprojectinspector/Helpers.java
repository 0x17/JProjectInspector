
package org.andreschnabel.jprojectinspector;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public class Helpers {
	
	public static void system(String cmd) {
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			System.out.println("Error executing: " + cmd);
			e.printStackTrace();
		}
	}
	
	public static String loadUrlIntoStr(String urlStr) throws Exception {
		URL url = new URL(urlStr);
		BufferedReader br = new BufferedReader( new InputStreamReader(url.openStream()));
		String content = "";
		while(br.ready()) {
			content += br.readLine() + '\n';
		}
		br.close();
		return content;
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

	public static boolean strContainsOneOf(String str, String[] candidates) {
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
}
