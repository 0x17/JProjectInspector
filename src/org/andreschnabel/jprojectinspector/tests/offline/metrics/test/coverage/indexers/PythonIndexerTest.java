package org.andreschnabel.jprojectinspector.tests.offline.metrics.test.coverage.indexers;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.PythonIndexer;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PythonIndexerTest {
	private PythonIndexer indexer;

	private final static String pythonCode = "import random\n" +
			"import unittest\n" +
			"\n" +
			"class TestSequenceFunctions(unittest.TestCase):\n" +
			"\n" +
			"    def setUp(self):\n" +
			"        self.seq = range(10)\n" +
			"\n" +
			"    def test_shuffle(self):\n" +
			"        # make sure the shuffled sequence does not lose any elements\n" +
			"        random.shuffle(self.seq)\n" +
			"        self.seq.sort()\n" +
			"        self.assertEqual(self.seq, range(10))\n" +
			"\n" +
			"        # should raise an exception for an immutable sequence\n" +
			"        self.assertRaises(TypeError, random.shuffle, (1,2,3))\n" +
			"\n" +
			"    def test_choice(self):\n" +
			"        element = random.choice(self.seq)\n" +
			"        self.assertTrue(element in self.seq)\n" +
			"\n" +
			"    def test_sample(self):\n" +
			"        with self.assertRaises(ValueError):\n" +
			"            random.sample(self.seq, 20)\n" +
			"        for element in random.sample(self.seq, 5):\n" +
			"            self.assertTrue(element in self.seq)\n" +
			"\n" +
			"if __name__ == '__main__':\n" +
			"    unittest.main()";

	@Before
	public void setUp() throws Exception {
		indexer = new PythonIndexer();
	}

	@Test
	public void testListFunctionDeclarations() throws Exception {
		List<String> decls = indexer.listFunctionDeclarations(pythonCode);
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {
				"setUp", "test_shuffle", "test_choice", "test_sample"
		}, decls);
	}

	@Test
	public void testListFunctionCalls() throws Exception {
		List<String> calls = indexer.listFunctionCalls(pythonCode);
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {
				"range", "shuffle", "sort", "choice", "assertEqual", "assertTrue", "sample", "assertRaises", "main"
		}, calls);
	}
}
