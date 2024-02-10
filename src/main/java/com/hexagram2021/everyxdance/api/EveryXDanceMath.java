package com.hexagram2021.everyxdance.api;

public final class EveryXDanceMath {
	private EveryXDanceMath() {
	}

	/**
	 *
	 * @param x		input variable, 0.0 <= x <= 1.0
	 * @param k		steepness, i.e., the gradient when x = 0.5
	 * @return		f(x) = {@code x}<sup>{@code k}</sup> / ({@code x}<sup>{@code k}</sup> + {@code (1-x)}<sup>{@code k}</sup>)<br/>
	 * 				0.0 <= f(x) <= 1.0.<br/>
	 * 				f(x) = 1 - f(1-x)<br/>
	 * 				f(x1) < f(x2) when x1 < x2.<br/>
	 * 				f&#39;(0) = f&#39;(1) = 0, f&#39;(0.5) = k.<br/>
	 * 				f&#39;(x) = f&#39;(1-x).
	 */
	public static double factSmooth(double x, double k) {
		double dp = Math.pow(x, k);
		double dn = Math.pow(1.0D - x, k);
		return dp / (dp + dn);
	}
}
