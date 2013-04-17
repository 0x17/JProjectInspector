package org.andreschnabel.jprojectinspector.runners;

import java.io.File;

import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

public class GeneralPurposeRunner {

	public static void main(String[] args) {
		System.out.println("Num java files here: " + FileHelpers.recursivelyCountFilesWithExtension(new File("."), ".java"));
	}

}
