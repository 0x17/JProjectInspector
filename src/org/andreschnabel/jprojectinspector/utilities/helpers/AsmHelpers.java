package org.andreschnabel.jprojectinspector.utilities.helpers;

import org.andreschnabel.jprojectinspector.utilities.functional.Func;
import org.andreschnabel.jprojectinspector.utilities.functional.ITransform;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Hilfsfunktionen zum Umgang mit der Bytecode-Manipulationsbibliothek ASM.
 */
public class AsmHelpers {
	
	private static interface HitCallback {
		public void hit();
	}
	
	private static class FindInterfaceVisitor extends ClassVisitor {
		private HitCallback callback;
		private String relevantInterface;
		public FindInterfaceVisitor(String relevantInterface, HitCallback callback) {
			super(Opcodes.ASM4);
			this.callback = callback;
			this.relevantInterface = relevantInterface;
		}
		@Override
		public void visit(int version, int acess, String name, String signature, String superName, String[] interfaces) {
			for(String iface : interfaces) {
				String[] parts = iface.split("/");
				if(parts[parts.length-1].equals(relevantInterface))
					callback.hit();
			}
		}
	}
	
	public static List<File> findClassFilesImplementingInterfaceInDir(File dir, String ifaceName) throws Exception {
		List<File> files = FileHelpers.filesInTree(dir);
		List<File> result = new LinkedList<File>();
		for(File f : files) {
			if(FileHelpers.extension(f).equals("class")) {
		      ClassReader cr = new ClassReader(FileHelpers.readBytes(f));
		      final List<Integer> hits = new LinkedList<Integer>();
		      HitCallback callback = new HitCallback() {
					@Override
					public void hit() {
						hits.add(0);
					}
				};
				cr.accept(new FindInterfaceVisitor(ifaceName, callback), 0);
				if(!hits.isEmpty())
					result.add(f);
			}
		}
		return result;
	}
	
	public static List<Class<?>> findClassesImplementingInterfaceInDir(File dir, String ifaceName) throws Exception {
		ITransform<File, Class<?>> fileToClass = new ITransform<File, Class<?>>() {
			@Override
			public Class<?> invoke(File f) {
				String classname = f.getName().replace("/", ".");
				try {
					return FileHelpers.loadClassFromFile(f, classname);
				} catch(Exception e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		return Func.map(fileToClass, findClassFilesImplementingInterfaceInDir(dir, ifaceName));
	}

}
