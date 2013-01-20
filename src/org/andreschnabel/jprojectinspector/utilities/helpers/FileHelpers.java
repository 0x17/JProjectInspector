package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.andreschnabel.jprojectinspector.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileHelpers {

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
		StringBuilder builder = new StringBuilder();
		int ch;
		while((ch = fr.read()) != -1) {
			builder.append((char)ch);
		}
		fr.close();
		return builder.toString();
	}

	public static void rmDir(String destPath) throws Exception {
		if(destPath.startsWith(Config.DEST_BASE)) {
			String rmCmd = Helpers.runningOnUnix() ? "rm -rf" : "rmdir /S /Q";
			if(Helpers.runningOnUnix())
				Helpers.system(rmCmd + " " + destPath + File.separator);
			else
				deleteDir(new File(destPath + File.separator));
		}
		else throw new Exception("Never leave "+Config.DEST_BASE+" with recursive force deletion!");
	}

	public static int recursivelyCountFilesWithExtension(File rootDir, String extension) {
		if(rootDir.isDirectory()) {
			int sum = 0;
			for(File f : rootDir.listFiles()) {
				sum += recursivelyCountFilesWithExtension(f, extension);
			}
			return sum;

		} else {
			return rootDir.getName().endsWith(extension) ? 1 : 0;
		}
	}

	public static <T> void writeObjToJsonFile(T obj, String outFilename) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonStr = gson.toJson(obj);
		writeStrToFile(jsonStr, outFilename);
	}

	public static List<String> listSourceFiles(String path) {
		File dir = new File(path);
		List<String> srcFilenames = new LinkedList<String>();
		FileHelpers.recursiveCollectSrcFiles(srcFilenames, dir);
		return srcFilenames;
	}

	public static void recursiveCollectSrcFiles(List<String> srcFilenames, File dir) {
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

	public static List<String> recursiveCollectSrcFiles(String dir) {
		List<String> result = new LinkedList<String>();
		recursiveCollectSrcFiles(result, new File(dir));
		return result;
	}

}
