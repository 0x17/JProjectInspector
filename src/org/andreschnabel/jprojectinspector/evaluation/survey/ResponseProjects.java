package org.andreschnabel.jprojectinspector.evaluation.survey;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "responseprojects")
public class ResponseProjects {
	
	public String leastTested;
	public String mostTested;	
	
	public String lowestBugCount;
	public String highestBugCount;
	
	public ResponseProjects(String leastTested, String mostTested, String lowestBugCount, String highestBugCount) {
		this.leastTested = leastTested;
		this.mostTested = mostTested;
		this.lowestBugCount = lowestBugCount;
		this.highestBugCount = highestBugCount;
	}
	
	public ResponseProjects() {}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((highestBugCount == null) ? 0 : highestBugCount.hashCode());
		result = prime * result + ((leastTested == null) ? 0 : leastTested.hashCode());
		result = prime * result + ((lowestBugCount == null) ? 0 : lowestBugCount.hashCode());
		result = prime * result + ((mostTested == null) ? 0 : mostTested.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		ResponseProjects other = (ResponseProjects)obj;
		if (highestBugCount == null) {
			if (other.highestBugCount != null) return false;
		} else if (!highestBugCount.equals(other.highestBugCount)) return false;
		if (leastTested == null) {
			if (other.leastTested != null) return false;
		} else if (!leastTested.equals(other.leastTested)) return false;
		if (lowestBugCount == null) {
			if (other.lowestBugCount != null) return false;
		} else if (!lowestBugCount.equals(other.lowestBugCount)) return false;
		if (mostTested == null) {
			if (other.mostTested != null) return false;
		} else if (!mostTested.equals(other.mostTested)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseProjects [leastTested=" + leastTested + ", mostTested=" + mostTested + ", lowestBugCount=" + lowestBugCount
			+ ", highestBugCount=" + highestBugCount + "]";
	}
	
	
	
}
