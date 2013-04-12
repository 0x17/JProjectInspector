package org.andreschnabel.jprojectinspector.tests.offline;

import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.JavaIndexer;
import org.andreschnabel.jprojectinspector.utilities.helpers.AssertHelpers;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class JavaIndexerTest {

	private JavaIndexer indexer;

	@Before
	public void setUp() throws Exception {
		indexer = new JavaIndexer();
	}

	@Test
	public void testListFunctionDeclarations() throws Exception {
		String codeStr = "package org.andreschnabel.jprojectinspector.tests.offline;\n" +
				"\n" +
				"import org.junit.Test;\n" +
				"\n" +
				"public class JavaIndexerTest {\n" +
				"\t@Test\n" +
				"\tpublic void testListFunctionDeclarations() throws Exception {\n" +
				"\t\t\n" +
				"\t}\n" +
				"\n" +
				"\t@Test\n" +
				"\tpublic void testListFunctionCalls() throws Exception {\n" +
				"\t}\n" +
				"}\n";
		List<String> decls = indexer.listFunctionDeclarations(codeStr);
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {"testListFunctionDeclarations", "testListFunctionCalls"}, decls);
	}

	@Test
	public void testListFunctionCalls() throws Exception {
		String codeStr = "@Override\n" +
				"\tpublic List<String> listFunctionDeclarations(String src) {\n" +
				"\t\tList<String> funcNames = new LinkedList<String>();\n" +
				"\t\tString methodRegex = \"(private|public|protected)?(\\\\s+static)?\\\\s+\\\\w+\\\\s+(\\\\w+)\\\\(.*\\\\)\";\n" +
				"\t\tPattern p = Pattern.compile(methodRegex);\n" +
				"\t\tMatcher m = p.matcher(src);\n" +
				"\t\twhile(m.find()) {\n" +
				"\t\t\tif(m.groupCount() == 3) {\n" +
				"\t\t\t\tString funcName = m.group(3);\n" +
				"\t\t\t\tListHelpers.addNoDups(funcNames, funcName);\n" +
				"\t\t\t}\n" +
				"\t\t}\n" +
				"\t\treturn funcNames;\n" +
				"\t}";
		List<String> calls = indexer.listFunctionCalls(codeStr);
		AssertHelpers.arrayEqualsLstOrderInsensitive(new String[] {"compile", "matcher", "find", "groupCount", "group", "addNoDups"}, calls);
	}
}
