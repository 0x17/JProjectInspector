package org.andreschnabel.jprojectinspector;

public class Main {
	public static void main(String[] args) {
		JsonTesting jt = new JsonTesting();
		jt.runTests();

		GitHubTesting ght = new GitHubTesting();
		ght.runTests();
		
		GitTesting gt = new GitTesting();
		gt.runTests();
	}
}
