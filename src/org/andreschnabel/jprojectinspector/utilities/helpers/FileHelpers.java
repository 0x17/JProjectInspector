package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.Config;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IPredicate;
import org.andreschnabel.jprojectinspector.utilities.functional.Tautology;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;

public class FileHelpers {

	public static List<File> filesWithPredicateInTree(File root, IPredicate<File> pred) throws Exception {
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
	
	public static Class<?> loadClassFromFile(File f, String classname) throws Exception {
		Class<?> cls = null;
		URLClassLoader cl = null;
		try {
		    URI uri = f.toURI();
		    URL url = uri.toURL();
		    URL[] urls = new URL[]{url};
		    cl = new URLClassLoader(urls);
		    cls = cl.loadClass(classname);
		} finally {
			// close is missing in Java 6 and OS X still ships with that.
			/*if(cl != null) {
				cl.close();
			}*/
		}
		return cls;
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
	
	public static byte[] readBytes(File file) throws Exception {
		byte[] buf = new byte[(int)file.length()];
		InputStream ios = null;
		try {
			ios = new FileInputStream(file);
			ios.read(buf);
		} finally {
			if(ios != null)
				ios.close();
		}
		return buf;
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

	public static List<File> recursiveCollectJavaSrcFiles(File dir) throws Exception {
		IPredicate<File> isJavaSrc = new IPredicate<File>() {
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
		ITransform<File, String> fileToName = new ITransform<File, String>() {
			@Override
			public String invoke(File f) {
				return f.getName();
			}
		};
		return Func.map(fileToName, FileHelpers.recursiveCollectJavaSrcFiles(dir));
	}

	public static List<File> filesInTree(File root) throws Exception {
		return filesWithPredicateInTree(root, new Tautology<File>());
	}

	public static String extension(File f) {
		String filename = f.getName();
		if(filename.contains(".") && filename.charAt(0) != '.') {
			String[] parts = filename.split("\\.");
			return parts[parts.length-1];
		} else return "";
	}

	public static String trimExtension(File f) {
		return f.getName().substring(0, f.getName().lastIndexOf("."));
	}

	public static void writeStrToFile(String str, File outFile) throws Exception {
		FileWriter fw = new FileWriter(outFile);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(str);
		bw.close();
		fw.close();
	}
}
