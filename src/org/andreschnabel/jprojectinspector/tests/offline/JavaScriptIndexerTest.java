package org.andreschnabel.jprojectinspector.tests.offline;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.JavaScriptIndexer;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JavaScriptIndexerTest {
	private JavaScriptIndexer indexer;
	private final static String jsCode = "function UserInfoTest() {\n" +
			"  // Each test function gets its own instance of UserInfoTest, so tests can\n" +
			"  this.getInfoFromDb_ = createMockFunction();\n" +
			"  this.userInfo_ = new UserInfo(this.getInfoFromDb_);\n" +
			"}\n" +
			"registerTestSuite(UserInfoTest);\n" +
			"\n" +
			"UserInfoTest.prototype.formatsUSPhoneNumber = function() {\n" +
			"  expectCall(this.getInfoFromDb_)(0xdeadbeef)\n" +
			"    .willOnce(returnWith('phone_number: \"650 253 0000\"'));\n" +
			"\n" +
			"  // Make sure that our class returns correctly formatted output.\n" +
			"  expectEq('(650) 253-0000', this.userInfo_.getPhoneForId(0xdeadbeef));\n" +
			"};\n" +
			"\n" +
			"UserInfoTest.prototype.returnsLastNameFirst = function() {\n" +
			"  expectCall(this.getInfoFromDb_)(0xdeadbeef)\n" +
			"    .willOnce(returnWith('given_name: \"John\" family_name: \"Doe\"'));\n" +
			"\n" +
			"  // Make sure that our class puts the last name first.\n" +
			"  expectEq('Doe, John', this.userInfo_.getNameForId(0xdeadbeef));\n" +
			"};";

	@Before
	public void setUp() throws Exception {
		indexer = new JavaScriptIndexer();
	}

	@Test
	public void testListFunctionDeclarations() throws Exception {
		List<String> decls = indexer.listFunctionDeclarations(jsCode);
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {
				"UserInfoTest", "formatsUSPhoneNumber", "returnsLastNameFirst"
		}, decls);
	}

	@Test
	public void testListFunctionCalls() throws Exception {
		List<String> calls = indexer.listFunctionCalls(jsCode);
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {
				"createMockFunction", "registerTestSuite", "expectCall", "willOnce",
				"getInfoFromDb_", "returnWith", "expectEq", "getPhoneForId", "expectCall",
				"returnWith", "getNameForId"
		}, calls);
	}
}
