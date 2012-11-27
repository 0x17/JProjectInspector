
package org.andreschnabel.jprojectinspector.prototypes;

import java.io.IOException;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

public class GitHub {

	public GitHub() {
	}

	public void runTests() {
		RepositoryService service = new RepositoryService();
		try {
			for (Repository repo : service.getRepositories("0x17")) {
				System.out.println("Repo Name = " + repo.getName() + " Watchers = " + repo.getWatchers());
			}
		} catch (IOException ioe) {
			System.out.println("IO CRAP");
			ioe.printStackTrace();
		}
	}
}
