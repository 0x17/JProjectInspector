package org.andreschnabel.jprojectinspector.tests.offline.utilities.functional;

import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.FuncInPlace;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class FuncInPlaceTest {
	@Test
	public void testRemoveAll() throws Exception {
		List<Integer> nums = Func.countUpTo(10);
		Predicate<Integer> isOdd = new Predicate<Integer>() {
			@Override
			public boolean invoke(Integer n) {
				return n % 2 == 1;
			}
		};
		FuncInPlace.removeAll(isOdd, nums);
		Integer[] evens = new Integer[]{2, 4, 6, 8, 10};
		AssertHelpers.lstEqualsLstOrderSensitive(Arrays.asList(evens), nums);
	}

	@Test
	public void testAddNoDups() throws Exception {
		List<Integer> nums = Func.countUpTo(10);
		FuncInPlace.addNoDups(nums, 4);
		Assert.assertEquals(10, nums.size());
		FuncInPlace.addNoDups(nums, 11);
		Assert.assertEquals(11, nums.size());
		Assert.assertTrue(nums.contains(11));
	}
}
