package org.andreschnabel.jprojectinspector.utilities.serialization;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.andreschnabel.jprojectinspector.utilities.helpers.FileHelpers;

import java.io.IOException;

/**
 * Hilfsfunktionen zur Serialisierung in Json.
 */
public class JsonHelpers {

	public static <T> void writeObjToJsonFile(T obj, String outFilename) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String jsonStr = gson.toJson(obj);
		FileHelpers.writeStrToFile(jsonStr, outFilename);
	}

}
