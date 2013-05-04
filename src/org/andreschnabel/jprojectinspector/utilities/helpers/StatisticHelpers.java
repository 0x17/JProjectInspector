package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.IBinaryOperator;

import java.util.List;

/**
 * Einige Hilfsfunktion für statistische Berechnungen.
 */
public final class StatisticHelpers {
	/**
	 * Nur statische Methoden.
	 */
	private StatisticHelpers() {}

	/**
	 * Bestimme kleinste Fließkommazahl in aufsteigend sortierter Liste.
	 *
	 * @param nums aufsteigend sortierte Liste von Fließkommazahlen.
	 * @return kleinste der Zahlen.
	 */
	public static double minOfSorted(List<Double> nums) {
		return nums.get(0);
	}

	/**
	 * Bestimme größte Fließkommazahl in aufsteigend sortierter Liste.
	 * @param nums aufsteigend sortierte Liste von Fließkommazahlen.
	 * @return größte der Zahlen.
	 */
	public static double maxOfSorted(List<Double> nums) {
		return nums.get(nums.size()-1);
	}

	/**
	 * Bestimme Median von aufsteigend sortierter Liste von Fließkommazahlen.
	 * @param nums aufsteigend sortierte Liste von Fließkommazahlen.
	 * @return Median der Zahlen.
	 */
	public static double medianOfSorted(List<Double> nums) {
		int index = (int)Math.floor(nums.size()/2.0)-1;
		return nums.get(index < 0 ? 0 : index);
	}

	/**
	 * Bestimme unteres Quartil von aufsteigend sortierter Liste von Fließkommazahlen.
	 * @param nums aufsteigend sortierte Liste von Fließkommazahlen.
	 * @return unterstes Quartil der Zahlen.
	 */
	public static double lowerQuartileOfSorted(List<Double> nums) {
		int index = (int)Math.floor(nums.size()/4.0)-1;
		return nums.get(index < 0 ? 0 : index);
	}

	/**
	 * Bestimme oberes Quartil von aufsteigend sortierter Liste von Fließkommazahlen.
	 * @param nums aufsteigend sortierte Liste von Fließkommazahlen.
	 * @return oberstes Quartil der Zahlen.
	 */
	public static double upperQuartileOfSorted(List<Double> nums) {
		int index = (int)Math.floor(nums.size()*3.0/4.0)-1;
		return nums.get(index < 0 ? 0 : index);
	}

	/**
	 * Bestimme Mittelwert einer Liste aus Fließkommazahlen.
	 * @param nums Liste aus Fließkommazahlen.
	 * @return Mittelwert der Zahlen, d.h. Summe der Zahlen dividiert durch ihre Anzahl.
	 */
	public static double mean(List<Double> nums) {
		IBinaryOperator<Double, Double> sumOp = new IBinaryOperator<Double, Double>() {
			@Override
			public Double invoke(Double a, Double b) {
				return a+b;
			}
		};
		return Func.reduce(sumOp, 0.0, nums) / (double)nums.size();
	}
}
