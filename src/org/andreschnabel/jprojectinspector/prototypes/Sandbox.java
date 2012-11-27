
package org.andreschnabel.jprojectinspector.prototypes;

import java.io.File;

public class Sandbox {
	public static void main(String[] args) {
		File f = new File(".");
		for(File g : f.listFiles()) {
			System.out.println(g.getName());
		}
	}

}
