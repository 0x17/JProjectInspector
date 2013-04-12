package org.andreschnabel.jprojectinspector.utilities.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.Tautology;
import org.andreschnabel.jprojectinspector.utilities.Transform;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class FileHelpers {

	public static List<File> filesWithPredicateInTree(File root, Predicate<File> pred) throws Exception {
		if(!root.isDirectory())
			throw new Exception("Path must point to dir!");

		List<File> files = new LinkedList<File>();

		for(File f : root.listFiles()) {
			if(f.isDirectory()) {
				files.addAll(filesWithPredicateInTree(f, pred));
			} else {
				if(pred.invoke(f)) {
					files.add(f);
				}
			}
		}

		return files;
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
				ProcessHelpers.system(rmCmd + " " + destPath + File.separator);
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

	public static List<File> recursiveCollectJavaSrcFiles(File dir) throws Exception {
		Predicate<File> isJavaSrc = new Predicate<File>() {
			@Override
			public boolean invoke(File f) {
				return f.getName().endsWith(".java");
			}
		};
		return filesWithPredicateInTree(dir, isJavaSrc);
	}

	public static void deleteFile(String path) throws Exception {
		File f = new File(path);
		if(!f.delete()) throw new Exception("Failed to delete file!");
	}
	
	public static File enforceDir(String path) throws Exception {
		File root = new File(path);
		if(!root.isDirectory()) {
			throw new Exception("Path must point to directory!");
		}
		return root;
	}
	
	public static List<String> listTestFiles(String path) throws Exception {
		File root = enforceDir(path);
		List<String> testfiles = new LinkedList<String>();
		recursivelyCollectTestFiles(root, testfiles);
		return testfiles;
	}
	
	public static void recursivelyCollectTestFiles(File root, List<String> testfiles) {	
		for(File f : root.listFiles()) {
			if(f.isDirectory()) {
				recursivelyCollectTestFiles(f, testfiles);
			} else {
				String fname = f.getName();
				if(fname.endsWith("Test.java"))
					testfiles.add(f.getPath());
			}
		}
	}

	public static List<String> listProductFiles(String path) throws Exception {
		File root = enforceDir(path);
		List<String> productfiles = new LinkedList<String>();
		recursivelyCollectProductFiles(root, productfiles);
		return productfiles;
	}

	public static void recursivelyCollectProductFiles(File root, List<String> productfiles) {
		for(File f : root.listFiles()) {
			if(f.isDirectory()) {
				recursivelyCollectProductFiles(f, productfiles);
			} else {
				String fname = f.getName();
				if(!fname.endsWith("Test.java") && fname.endsWith(".java"))
					productfiles.add(f.getPath());
			}
		}
	}

	public static boolean exists(String path) {
		return new File(path).exists();
	}

	public static List<String> listJavaSourceFiles(File dir) throws Exception {
		Transform<File, String> fileToName = new Transform<File, String>() {
			@Override
			public String invoke(File f) {
				return f.getName();
			}
		};
		return ListHelpers.map(fileToName, FileHelpers.recursiveCollectJavaSrcFiles(dir));
	}

	public static List<File> filesInTree(File root) throws Exception {
		return filesWithPredicateInTree(root, new Tautology());
	}

	public static String extension(File f) {
		String filename = f.getName();
		if(filename.contains(".") && filename.charAt(0) != '.') {
			String[] parts = filename.split("\\.");
			return parts[parts.length-1];
		} else return "";
	}
}
