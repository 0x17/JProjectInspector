package org.andreschnabel.jprojectinspector.githubapi;


import org.andreschnabel.jprojectinspector.utilities.helpers.Helpers;

import com.google.gson.Gson;

/**
 * Prüfe überschreiten von RateLimit der GitHub-Web-API.
 */
public final class RateLimitChecker {
	
	private RateLimitChecker() {}

	static class RateLimitResult {
		int remaining;
		int limit;
	}

	private final static RateLimitResult rateLimitResult;

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
