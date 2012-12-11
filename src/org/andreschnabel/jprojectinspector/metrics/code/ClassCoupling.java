package org.andreschnabel.jprojectinspector.metrics.code;

import java.io.File;
import java.util.List;

public class ClassCoupling {

	public float getAverageCoupling() {
		// TODO: Implement!
		return 0.0f;
	}

	public List<String> listClassNamesInProject(File rootDir) {
	// TODO: Implement!
		return null;
	}
	
	public List<String> referencedClasses(String classname) {
	// TODO: Implement!
		return null;
	}
	
	public class CouplingPair {
		public String classname;
		public List<String> referencedClasses;
	}
	
	public List<CouplingPair> getCouplingPairsOfProject(File rootDir) {
	// TODO: Implement!
		return null;
	}
	
	public static void main(String[] args) {
	}

}
