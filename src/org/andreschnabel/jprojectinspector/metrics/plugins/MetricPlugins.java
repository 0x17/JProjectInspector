package org.andreschnabel.jprojectinspector.metrics.plugins;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.AsmHelpers;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class MetricPlugins {

	public static List<OfflineMetric> loadOfflineMetricPlugins(File dir) throws Exception {
		List<File> classFiles = AsmHelpers.findClassFilesImplementingInterfaceInDir(dir, "OfflineMetric");
		final URLClassLoader classLoader = initLoader(dir);

		Transform<File, OfflineMetric> classFileToOfflineMetric = new Transform<File, OfflineMetric>() {
			@Override
			public OfflineMetric invoke(File classFile) {
				return (OfflineMetric)objFromClassFile(classFile, classLoader);
			}
		};
		
		List<OfflineMetric> plugins = Func.map(classFileToOfflineMetric, classFiles);
		//classLoader.close();
		return plugins;		
	}
	
	public static List<OnlineMetric> loadOnlineMetricPlugins(File dir) throws Exception {
		List<File> classFiles = AsmHelpers.findClassFilesImplementingInterfaceInDir(dir, "OnlineMetric");
		final URLClassLoader classLoader = initLoader(dir);

		Transform<File, OnlineMetric> classFileToOnlineMetric = new Transform<File, OnlineMetric>() {
			@Override
			public OnlineMetric invoke(File classFile) {
				return (OnlineMetric)objFromClassFile(classFile, classLoader);
			}
		};
		
		List<OnlineMetric> plugins = Func.map(classFileToOnlineMetric, classFiles);
		//classLoader.close();
		return plugins;
	}
	
	public static URLClassLoader initLoader(File dir) throws Exception {
		return new URLClassLoader(new URL[]{dir.toURI().toURL()});
	}
	
	public static Object objFromClassFile(File classFile, ClassLoader classLoader) {
		try {
			String className = classFile.getName().replace(".class", "");
			Class<?> clazz = classLoader.loadClass(className);
			return clazz.newInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
