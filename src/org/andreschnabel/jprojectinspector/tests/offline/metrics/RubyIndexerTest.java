package org.andreschnabel.jprojectinspector.tests.offline.metrics;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.RubyIndexer;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class RubyIndexerTest {
	private RubyIndexer indexer;
	private final static String rubyCode = "require \"./simple_number\"\n" +
			"require \"test/unit\"\n" +
			" \n" +
			"class TestSimpleNumber < Test::Unit::TestCase\n" +
			" \n" +
			"  def test_simple\n" +
			"    assert_equal(4, SimpleNumber.new(2).add(2) )\n" +
			"    assert_equal(4, SimpleNumber.new(2).multiply(2) )\n" +
			"  end\n" +
			" \n" +
			"  def test_typecheck\n" +
			"    assert_raise( RuntimeError ) { SimpleNumber.new('a') }\n" +
			"  end\n" +
			" \n" +
			"  def test_failure\n" +
			"    assert_equal(3, SimpleNumber.new(2).add(2), \"Adding doesn't work\" )\n" +
			"  end\n" +
			" \n" +
			"end";

	@Before
	public void setUp() throws Exception {
		indexer = new RubyIndexer();
	}

	@Test
	public void testListFunctionDeclarations() throws Exception {
		List<String> decls = indexer.listFunctionDeclarations(rubyCode);
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {
				"test_simple", "test_typecheck", "test_failure"
		}, decls);
	}

	@Test
	public void testListFunctionCalls() throws Exception {
		List<String> calls = indexer.listFunctionCalls(rubyCode);
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {
				"assert_equal", "assert_raise", "add", "multiply"
		}, calls);
	}
}
