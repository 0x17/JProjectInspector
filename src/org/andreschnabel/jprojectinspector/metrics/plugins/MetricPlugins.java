package org.andreschnabel.jprojectinspector.metrics.plugins;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.OnlineMetric;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

public class MetricPlugins {

	public static List<OfflineMetric> loadOfflineMetricPlugins(File dir) throws Exception {
		List<File> classFiles = AsmHelpers.findClassFilesImplementingInterfaceInDir(dir, "OfflineMetric");
		final URLClassLoader classLoader = new URLClassLoader(new URL[]{dir.toURI().toURL()});

		Transform<File, OfflineMetric> classFileToOfflineMetric = new Transform<File, OfflineMetric>() {
			@Override
			public OfflineMetric invoke(File classFile) {
				String className = classFile.getName().replace(".class", "");
				try {
					Class<?> clazz = classLoader.loadClass(className);
					return (OfflineMetric)clazz.newInstance();
				} catch(Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		return Func.map(classFileToOfflineMetric, classFiles);
	}

	public static List<OnlineMetric> loadOnlineMetricPlugins(File dir) throws Exception {
		List<File> classFiles = AsmHelpers.findClassFilesImplementingInterfaceInDir(dir, "OnlineMetric");
		final URLClassLoader classLoader = new URLClassLoader(new URL[]{dir.toURI().toURL()});

		Transform<File, OnlineMetric> classFileToOnlineMetric = new Transform<File, OnlineMetric>() {
			@Override
			public OnlineMetric invoke(File classFile) {
				String className = classFile.getName().replace(".class", "");
				try {
					Class<?> clazz = classLoader.loadClass(className);
					return (OnlineMetric)clazz.newInstance();
				} catch(Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		};
		return Func.map(classFileToOnlineMetric, classFiles);
	}

}
