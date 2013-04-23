package org.andreschnabel.jprojectinspector.utilities.functional;

public interface ITestCallback {

	public String getDescription();
	public void invoke() throws Exception;

}
