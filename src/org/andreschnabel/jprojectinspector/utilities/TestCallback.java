package org.andreschnabel.jprojectinspector.utilities;

public interface TestCallback {

	public String getDescription();
	public void invoke() throws Exception;

}
