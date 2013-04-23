package org.andreschnabel.jprojectinspector.tests.offline.utilities.functional;

import org.andreschnabel.jprojectinspector.utilities.functional.*;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class FuncTest {
	@Test
	public void testContains() throws Exception {
		List<Integer> nums = Func.countUpTo(10);

		IPredicate<Integer> pred10 = new IPredicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num == 10;
			}
		};
		Assert.assertTrue(Func.contains(pred10, nums));

		IPredicate<Integer> pred20 = new IPredicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num == 20;
			}
		};
		Assert.assertFalse(Func.contains(pred20, nums));
	}

	@Test
	public void testRange() throws Exception {
		Integer[] zeroToNine = new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		AssertHelpers.arrayEqualsLstOrderSensitive(zeroToNine, Func.range(10));
	}

	@Test
	public void testCountUpTo() throws Exception {
		Integer[] oneToTen = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		AssertHelpers.arrayEqualsLstOrderSensitive(oneToTen, Func.countUpTo(10));
	}

	@Test
	public void testMap() throws Exception {
		Integer[] oneToTen = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		Integer[] oneToTenSquared = new Integer[] { 1, 4, 9, 16, 25, 36, 49, 64, 81, 100 };
		ITransform<Integer, Integer> square = new ITransform<Integer, Integer>() {
			@Override
			public Integer invoke(Integer obj) {
				return obj * obj;
			}
		};
		AssertHelpers.arrayEqualsLstOrderSensitive(oneToTenSquared, Func.map(square, Func.fromArray(oneToTen)));
	}

	@Test
	public void testMapi() throws Exception {
		String[] names = new String[] { "Hans", "Peter", "Uwe", "Harald", "Anton", "Heinrich", "Hermann", "Siegfried", "Joachim", "Heinz" };
		IIndexedTransform<String, Integer> itrans = new IIndexedTransform<String, Integer>() {
			@Override
			public Integer invoke(int i, String obj) {
				return i;
			}
		};
		AssertHelpers.lstEqualsLstOrderSensitive(Func.range(10), Func.mapi(itrans, Func.fromArray(names)));
	}

	@Test
	public void testFromArray() throws Exception {
		Integer[] oneToTen = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		AssertHelpers.arrayEqualsLstOrderSensitive(oneToTen, Func.fromArray(oneToTen));
	}

	@Test
	public void testFilter() throws Exception {
		Integer[] evens = new Integer[] { 2, 4, 6, 8, 10 };
		IPredicate<Integer> isEven = new IPredicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num % 2 == 0;
			}
		};
		AssertHelpers.arrayEqualsLstOrderSensitive(evens, Func.filter(isEven, Func.countUpTo(10)));
	}

	@Test
	public void testCount() throws Exception {
		IPredicate<Integer> isEven = new IPredicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num % 2 == 0;
			}
		};
		Assert.assertEquals(5, Func.count(isEven, Func.countUpTo(10)));
	}

	@Test
	public void testFind() throws Exception {
		List<Integer> nums = Func.countUpTo(10);

		IPredicate<Integer> pred10 = new IPredicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num == 10;
			}
		};
		Assert.assertEquals(10, (int) Func.find(pred10, nums));

		IPredicate<Integer> pred20 = new IPredicate<Integer>() {
			@Override
			public boolean invoke(Integer num) {
				return num == 20;
			}
		};
		Assert.assertEquals(null, Func.find(pred20, nums));
	}

	@Test
	public void testReduce() throws Exception {
		List<Integer> nums = Func.countUpTo(10);
		int sumOfIntsTo10 = 10 * 11 / 2;
		IBinaryOperator<Integer, Integer> add = new IBinaryOperator<Integer, Integer>() {
			@Override
			public Integer invoke(Integer a, Integer b) {
				return a + b;
			}
		};
		Assert.assertEquals(sumOfIntsTo10, (int) Func.reduce(add, 0, nums));
	}
}
