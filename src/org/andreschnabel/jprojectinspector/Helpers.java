
package org.andreschnabel.jprojectinspector;

import java.util.Date;

public class Helpers {

	public static Date timeBetween(Date a, Date b) {
		long delta = b.getTime() - a.getTime();
		return new Date(delta);
	}

}
