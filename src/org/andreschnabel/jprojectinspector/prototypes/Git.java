
package org.andreschnabel.jprojectinspector.prototypes;

import org.eclipse.jgit.storage.file.FileRepository;

public class Git {
	public Git() {
	}

	public void runTests() {
		try {
			 FileRepository repo = new FileRepository(".");
			 System.out.println("Current state = " + repo.getRepositoryState());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
