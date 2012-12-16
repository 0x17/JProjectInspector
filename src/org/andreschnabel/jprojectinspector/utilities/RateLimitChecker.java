package org.andreschnabel.jprojectinspector.utilities;


import com.google.gson.Gson;

public class RateLimitChecker {

	static class RateLimitResult {
		int remaining;
		int limit;
	}

	private static RateLimitResult rateLimitResult;

	static {
		String rateLimitUri = "https://api.github.com/rate_limit";
		String rateLimitJson = null;
		try {
			rateLimitJson = Helpers.loadUrlIntoStr(rateLimitUri);
		} catch(Exception e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		rateLimitResult = gson.fromJson(rateLimitJson, RateLimitResult.class);
		System.out.println("Rate limit = " + rateLimitResult.limit);
		System.out.println("Remaining = " + rateLimitResult.remaining);
	}

	public static boolean apiCall() {
		rateLimitResult.remaining--;
		return rateLimitResult.remaining == 0;
	}
}