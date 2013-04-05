package org.andreschnabel.jprojectinspector.tests.offline;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ClocTest {
	@Test
	public void testDetermineLinesOfCode() throws Exception {
		List<Cloc.ClocResult> results = Cloc.determineLinesOfCode(new File("."));
		Assert.assertFalse(results.isEmpty());

		Assert.assertTrue(ListHelpers.contains(new Predicate<Cloc.ClocResult>() {
			@Override
			public boolean invoke(Cloc.ClocResult result) {
				return result.language.equals("Java");
			}
		}, results));

	}
}
