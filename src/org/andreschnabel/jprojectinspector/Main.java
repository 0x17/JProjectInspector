
package org.andreschnabel.jprojectinspector;

import java.io.IOException;

import org.andreschnabel.jprojectinspector.metrics.IGitHubMetrics;
import org.andreschnabel.jprojectinspector.metrics.impls.GitHubMetrics;

public class Main {
	public static void main(String[] args) {
		try {
			IGitHubMetrics ghm = new GitHubMetrics("mono", "MonoGame");
			System.out.println(ghm.toJson());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
