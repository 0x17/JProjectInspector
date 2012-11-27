
package org.andreschnabel.jprojectinspector.prototypes;

public class Runner {

	public static void main(String[] args) {
		runPrototypes();
	}

	private static void runPrototypes() {
		Json jt = new Json();
		jt.runTests();

		GitHub ght = new GitHub();
		ght.runTests();

		Git gt = new Git();
		gt.runTests();
	}

}
