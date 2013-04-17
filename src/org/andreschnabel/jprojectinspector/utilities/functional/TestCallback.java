package org.andreschnabel.jprojectinspector.utilities.functional;

public interface TestCallback {

	public String getDescription();
	public void invoke() throws Exception;

}
