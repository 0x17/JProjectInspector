package org.andreschnabel.jprojectinspector.prototypes;

import org.gitective.core.CommitFinder;
import org.gitective.core.PathFilterUtils;

/**
 * https://github.com/kevinsawicki/gitective
 */
public class Gitective {

	public static void main(String[] args) {
		CommitFinder finder = new CommitFinder("test");
		finder.setFilter(PathFilterUtils.andSuffix("test.java"));
		finder.find();
	}

}
