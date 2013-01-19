package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.util.List;

import org.andreschnabel.jprojectinspector.utilities.helpers.RegexHelpers.StringTriple;
import org.junit.Test;

public class RegexHelpersTest {

	@Test
	public void testBatchMatchOneGroup() throws Exception {
		List<String> result = RegexHelpers.batchMatchOneGroup("([a-z]+);?+", "hund;katze;maus");
		AssertHelpers.arrayEqualsLstOrderSensitive(new String[]{"hund", "katze", "maus"}, result);
	}

	@Test
	public void testBatchMatchTwoGroups() {
		List<RegexHelpers.StringPair> result = RegexHelpers.batchMatchTwoGroups("(\\d+):([a-z]+);?+", "0:null;1:eins;2:zwei");
		RegexHelpers.StringPair[] expectedPairs = new RegexHelpers.StringPair[]{
				new RegexHelpers.StringPair("0", "null"),
				new RegexHelpers.StringPair("1", "eins"),
				new RegexHelpers.StringPair("2", "zwei")
		};
		AssertHelpers.arrayEqualsLstOrderSensitive(expectedPairs, result);
	}

	@Test
	public void testBatchMatchThreeGroups() {
		List<StringTriple> result = RegexHelpers.batchMatchThreeGroups("(\\w+):(\\w+):(\\w+);?+", "Hans:Mann:23;Peter:Mann:55;Frauke:Frau:88");
		StringTriple[] expectedTriples = new RegexHelpers.StringTriple[]{
				new RegexHelpers.StringTriple("Hans", "Mann", "23"),
				new RegexHelpers.StringTriple("Peter", "Mann", "55"),
				new RegexHelpers.StringTriple("Frauke", "Frau", "88")
		};
		AssertHelpers.arrayEqualsLstOrderSensitive(expectedTriples, result);
	}

}
