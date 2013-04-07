package org.andreschnabel.jprojectinspector.tests.offline;

import org.andreschnabel.jprojectinspector.utilities.BinaryOperator;
import org.andreschnabel.jprojectinspector.utilities.IndexedTransform;
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
	public void testMapi() throws Exception {
		String[] names = new String[] { "Hans", "Peter", "Uwe", "Harald", "Anton", "Heinrich", "Hermann", "Siegfried", "Joachim", "Heinz" };
		IndexedTransform<String, Integer> itrans = new IndexedTransform<String, Integer>() {
			@Override
			public Integer invoke(int i, String obj) {
				return i;
			}
		};
		AssertHelpers.lstEqualsLstOrderSensitive(ListHelpers.range(10), ListHelpers.mapi(itrans, ListHelpers.fromArray(names)));
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
		AssertHelpers.arrayEqualsLstOrderSensitive(evens, ListHelpers.filter(isEven, ListHelpers.countUpTo(10)));
	}

	@Test
	public void testCount() throws Exception {
		Predicate<Integer> isEven = new Predicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num % 2 == 0;
			}
		};
		Assert.assertEquals(5, ListHelpers.count(isEven, ListHelpers.countUpTo(10)));
	}

	@Test
	public void testFind() throws Exception {
		List<Integer> nums = ListHelpers.countUpTo(10);

		Predicate<Integer> pred10 = new Predicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num == 10;
			}
		};
		Assert.assertEquals(10, (int)ListHelpers.find(pred10, nums));

		Predicate<Integer> pred20 = new Predicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num == 20;
			}
		};
		Assert.assertEquals(null, ListHelpers.find(pred20, nums));
	}

	@Test
	public void testReduce() throws Exception {
		List<Integer> nums = ListHelpers.countUpTo(10);
		int sumOfIntsTo10 = 10 * 11 / 2;
		BinaryOperator<Integer, Integer> add = new BinaryOperator<Integer, Integer>() {
			@Override
			public Integer invoke(Integer a, Integer b) {
				return a + b;
			}
		};
		Assert.assertEquals(sumOfIntsTo10, (int)ListHelpers.reduce(add, 0, nums));
	}
}
