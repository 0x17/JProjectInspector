package org.andreschnabel.jprojectinspector.metrics.test.coverage;

import org.andreschnabel.jprojectinspector.metrics.OfflineMetric;
import org.andreschnabel.jprojectinspector.metrics.code.Cloc;
import org.andreschnabel.jprojectinspector.metrics.code.ClocResult;
import org.andreschnabel.jprojectinspector.metrics.test.UnitTestDetector;
import org.andreschnabel.jprojectinspector.metrics.test.coverage.indexers.IndexerRegistry;
import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.Predicate;
import org.andreschnabel.jprojectinspector.utilities.functional.Transform;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RoughFunctionCoverage implements OfflineMetric {

	public static Map<String, Double> approxFunctionCoverage(File rootPath) throws Exception {
		final Map<String, FunctionIndexer> indexerForExtension = IndexerRegistry.indexerForExtension;

		final List<File> files = FileHelpers.filesInTree(rootPath);

		List<String> knownLangs = Func.fromArray(new String[]{"java", "js", "rb", "py"});
		List<List<String>> funcDeclsForKnownLangs = determineFunctionsForKnownLanguages(Mode.FunctionDeclarations, indexerForExtension, files, knownLangs);
		List<List<String>> funcCallsForKnownLangs = determineFunctionsForKnownLanguages(Mode.FunctionCalls, indexerForExtension, files, knownLangs);

		Map<String, Double> coverages = new HashMap<String, Double>();

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
			List<String> calledFuncs = Func.filter(wasCalled, funcDecls);

			coverages.put(lang, funcDecls.isEmpty() ? 0.0f : (double)calledFuncs.size() / (double)funcDecls.size());
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
			List<ClocResult> res = Cloc.determineLinesOfCode(f.getParentFile(), f.getName());
			int loc = ClocResult.accumResults(res).codeLines;

			if(UnitTestDetector.isTest(f)) {
				unknownLangTloc += loc;
			} else {
				unknownLangLoc += loc;
			}
		}

		coverages.put("unknown", unknownLangLoc == 0 ? 0.0f : unknownLangTloc / (double)unknownLangLoc);

		return coverages;
	}

	@Override
	public String getName() {
		return "cov";
	}

	@Override
	public String getDescription() {
		return "Number of called methods divided by number of declared methods for JavaScript, Java, Ruby, Python. Otherweise TLOC/LOC.";
	}

	@Override
	public double measure(File repoRoot) throws Exception {
		Map<String, Double> coverages = approxFunctionCoverage(repoRoot);
		String firstKey = null;
		for(String key : coverages.keySet()) {
			firstKey = key;
			break;
		}
		return coverages.get(firstKey);
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
							isTest = UnitTestDetector.isTest(f);
						} catch(Exception e) {
							e.printStackTrace();
						}
						return f.getName().endsWith("."+ext) && ((mode == Mode.FunctionCalls) ? isTest : !isTest);
					}
				};
				List<File> srcFiles = Func.filter(hasExtAndCorrectType, files);
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
		return Func.map(langToFuncNames, knownLangs);
	}
}
