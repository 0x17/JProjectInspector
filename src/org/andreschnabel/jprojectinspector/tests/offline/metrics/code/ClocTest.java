package org.andreschnabel.jprojectinspector.tests.offline.metrics.code;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.code.ClocResult;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class ClocTest {
	@Test
	public void testDetermineLinesOfCode() throws Exception {
		List<ClocResult> results = Cloc.determineLinesOfCode(new File("."), "testdata");
		AssertHelpers.listNotEmpty(results);

		Assert.assertTrue(Func.contains(new Predicate<ClocResult>() {
			@Override
			public boolean invoke(ClocResult result) {
				return result.language.equals("Java");
			}
		}, results));
	}
}
