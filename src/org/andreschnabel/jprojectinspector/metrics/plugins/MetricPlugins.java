package org.andreschnabel.jprojectinspector.metrics.plugins;

import org.andreschnabel.jprojectinspector.metrics.IOfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.IOnlineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
import org.andreschnabel.jprojectinspector.utilities.helpers.AsmHelpers;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * Hilfsklasse zum Laden von Metrik-Plugins.
 */
public class MetricPlugins {

	public static List<IOfflineMetric> loadOfflineMetricPlugins(File dir) throws Exception {
		List<File> classFiles = AsmHelpers.findClassFilesImplementingInterfaceInDir(dir, "IOfflineMetric");
		final URLClassLoader classLoader = initLoader(dir);

		ITransform<File, IOfflineMetric> classFileToOfflineMetric = new ITransform<File, IOfflineMetric>() {
			@Override
			public IOfflineMetric invoke(File classFile) {
				return (IOfflineMetric)objFromClassFile(classFile, classLoader);
			}
		};
		
		List<IOfflineMetric> plugins = Func.map(classFileToOfflineMetric, classFiles);
		//classLoader.close();
		return plugins;		
	}
	
	public static List<IOnlineMetric> loadOnlineMetricPlugins(File dir) throws Exception {
		List<File> classFiles = AsmHelpers.findClassFilesImplementingInterfaceInDir(dir, "IOnlineMetric");
		final URLClassLoader classLoader = initLoader(dir);

		ITransform<File, IOnlineMetric> classFileToOnlineMetric = new ITransform<File, IOnlineMetric>() {
			@Override
			public IOnlineMetric invoke(File classFile) {
				return (IOnlineMetric)objFromClassFile(classFile, classLoader);
			}
		};
		
		List<IOnlineMetric> plugins = Func.map(classFileToOnlineMetric, classFiles);
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
