package org.andreschnabel.jprojectinspector.metrics.test.coverage;

import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.JavaIndexer;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.JavaScriptIndexer;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.PythonIndexer;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.RubyIndexer;
import org.andreschnabel.jprojectinspector.utilities.Predicate;
import org.andreschnabel.jprojectinspector.utilities.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;
import org.andreschnabel.jprojectinspector.utilities.helpers.ListHelpers;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RoughFunctionCoverage {

	public static Map<String, Float> approxFunctionCoverage(File rootPath) throws Exception {
		final Map<String, FunctionIndexer> indexerForExtension = getIndexers();

		final List<File> files = FileHelpers.filesInTree(rootPath);

		List<String> knownLangs = ListHelpers.fromArray(new String[] {"java", "js", "rb", "py"});
		List<List<String>> funcDeclsForKnownLangs = determineFunctionsForKnownLanguages(Mode.FunctionDeclarations, indexerForExtension, files, knownLangs);
		List<List<String>> funcCallsForKnownLangs = determineFunctionsForKnownLanguages(Mode.FunctionCalls, indexerForExtension, files, knownLangs);

		Map<String, Float> coverages = new HashMap<String, Float>();

		for(int i=0; i<knownLangs.size(); i++) {
			String lang = knownLangs.get(i);
			List<String> funcDecls = funcDeclsForKnownLangs.get(i);
			final List<String> funcCalls = funcCallsForKnownLangs.get(i);
			Predicate<String> wasCalled = new Predicate<String>() {
				@Override
				public boolean invoke(String funcName) {
					return funcCalls.contains(funcName);
				}
			};
			List<String> calledFuncs = ListHelpers.filter(wasCalled, funcDecls);
			coverages.put(lang, (float)calledFuncs.size() / (float)funcDecls.size());
		}

		Predicate<File> unknownLang = new Predicate<File>() {
			@Override
			public boolean invoke(File f) {
				return !indexerForExtension.containsKey(FileHelpers.extension(f));
			}
		};

		int unknownLangLoc = 0;
		int unknownLangTloc = 0;

		List<File> unknownLangFiles = FileHelpers.filesWithPredicateInTree(rootPath, unknownLang);
		for(File f : unknownLangFiles) {
			List<Cloc.ClocResult> res = Cloc.determineLinesOfCode(f.getParentFile(), f.getName());
			int loc = 0;
			for(Cloc.ClocResult r : res) {
				loc += r.codeLines;
			}

			if(UnitTestDetector.containsTest(f)) {
				unknownLangTloc += loc;
			} else {
				unknownLangLoc += loc;
			}
		}

		coverages.put("unknown", unknownLangTloc / (float)unknownLangLoc);

		return coverages;
	}

	private static Map<String, FunctionIndexer> getIndexers() {
		Map<String, FunctionIndexer> indexerForExtension = new HashMap<String, FunctionIndexer>();
		indexerForExtension.put("java", new JavaIndexer());
		indexerForExtension.put("js", new JavaScriptIndexer());
		indexerForExtension.put("rb", new RubyIndexer());
		indexerForExtension.put("py", new PythonIndexer());
		return indexerForExtension;
	}

	private enum Mode {
		FunctionDeclarations,
		FunctionCalls
	}

	private static List<List<String>> determineFunctionsForKnownLanguages(final Mode mode, final Map<String, FunctionIndexer> indexerForExtension, final List<File> files, List<String> knownLangs) {
		Transform<String, List<String>> langToFuncNames = new Transform<String, List<String>>() {
			@Override
			public List<String> invoke(final String ext) {
				Predicate<File> hasExtAndCorrectType = new Predicate<File>() {
					@Override
					public boolean invoke(File f) {
						boolean isTest = false;
						try {
							isTest = UnitTestDetector.containsTest(f);
						} catch(Exception e) {
							e.printStackTrace();
						}
						return f.getName().endsWith("."+ext) && ((mode == Mode.FunctionCalls) ? isTest : !isTest);
					}
				};
				List<File> srcFiles = ListHelpers.filter(hasExtAndCorrectType, files);
				FunctionIndexer indexer = indexerForExtension.get(ext);

				List<String> names = new LinkedList<String>();

				try {
					for(File srcFile : srcFiles) {
						List<String> namesInFile = null;
						String src = FileHelpers.readEntireFile(srcFile);
						switch(mode) {
							case FunctionDeclarations:
								namesInFile = indexer.listFunctionDeclarations(src);
								break;
							case FunctionCalls:
								namesInFile = indexer.listFunctionCalls(src);
								break;
						}
						names.addAll(namesInFile);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}

				return names;
			}
		};
		return ListHelpers.map(langToFuncNames, knownLangs);
	}
}
