
package org.andreschnabel.jprojectinspector.metrics;

import java.io.IOException;

public interface IGitHubMetrics {

	public abstract int getNumberOfContributors() throws IOException;

	public abstract int getTestPopularity() throws IOException;

	public abstract int getNumberOfIssues() throws IOException;

	public abstract int getSelectivity() throws IOException;

	public abstract int getCodeFrequency() throws IOException;

	public abstract long getRepositoryAge() throws IOException;

	public abstract String toJson() throws IOException;

}
