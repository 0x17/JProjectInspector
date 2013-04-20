package org.andreschnabel.jprojectinspector.utilities.helpers;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelpers {

	public static <T> void writeObjToJsonFile(T obj, String outFilename) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonStr = gson.toJson(obj);
		FileHelpers.writeStrToFile(jsonStr, outFilename);
	}

}
