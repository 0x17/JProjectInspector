package org.andreschnabel.jprojectinspector.tests;

import javax.swing.*;

public abstract class VisualTestCallback {

	protected JFrame frm;

	public abstract String getDescription();
	public abstract void invoke() throws Exception;

	public void invokeWithSetupAndTeardown() throws Exception {
		frm = VisualTest.getTestFrame();
		invoke();
		VisualTest.waitForFrameToClose(frm);
	}

}
