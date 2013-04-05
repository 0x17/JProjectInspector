package org.andreschnabel.jprojectinspector.tests.offline;

import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ListHelpersTest {
	@Test
	public void testAddNoDups() throws Exception {
		List<Integer> nums = ListHelpers.countUpTo(10);
		ListHelpers.addNoDups(nums, 4);
		Assert.assertEquals(10, nums.size());
		ListHelpers.addNoDups(nums, 11);
		Assert.assertEquals(11, nums.size());
		Assert.assertTrue(nums.contains(11));
	}

	@Test
	public void testContains() throws Exception {
		List<Integer> nums = ListHelpers.countUpTo(10);

		Predicate<Integer> pred10 = new Predicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num == 10;
			}
		};
		Assert.assertTrue(ListHelpers.contains(pred10, nums));

		Predicate<Integer> pred20 = new Predicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num == 20;
			}
		};
		Assert.assertFalse(ListHelpers.contains(pred20, nums));
	}

	@Test
	public void testRange() throws Exception {
		Integer[] zeroToNine = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		AssertHelpers.arrayEqualsLstOrderSensitive(zeroToNine, ListHelpers.range(10));
	}

	@Test
	public void testCountUpTo() throws Exception {
		Integer[] oneToTen = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		AssertHelpers.arrayEqualsLstOrderSensitive(oneToTen, ListHelpers.countUpTo(10));
	}

	@Test
	public void testMap() throws Exception {
		Integer[] oneToTen = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Integer[] oneToTenSquared = new Integer[] { 1, 4, 9, 16, 25, 36, 49, 64, 81, 100 };
		Transform<Integer, Integer> square = new Transform<Integer, Integer>() {
			@Override
			public Integer invoke(Integer obj) {
				return obj * obj;
			}
		};
		AssertHelpers.arrayEqualsLstOrderSensitive(oneToTenSquared, ListHelpers.map(square, ListHelpers.fromArray(oneToTen)));
	}

	@Test
	public void testFromArray() throws Exception {
		Integer[] oneToTen = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		AssertHelpers.arrayEqualsLstOrderSensitive(oneToTen, ListHelpers.fromArray(oneToTen));
	}

	@Test
	public void testFilter() throws Exception {
		Integer[] oneToTen = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Integer[] evens = new Integer[] { 2, 4, 6, 8, 10 };
		Predicate<Integer> isEven = new Predicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num % 2 == 0;
			}
		};
		AssertHelpers.arrayEqualsLstOrderSensitive(evens, ListHelpers.filter(isEven, ListHelpers.fromArray(oneToTen)));
	}
}
