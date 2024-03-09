package com.hexagram2021.everyxdance.api;

public final class EveryXDanceMath {
	private EveryXDanceMath() {
	}

	/**
	 *
	 * @param x		input variable, 0.0 &le; x &le; 1.0.
	 * @param k		steepness, i.e., the gradient when x = s. k &gt; 1.0.
	 * @param s	  	f(s) = s, f&#39;(s) = k. 0.0 &le; s &le; 1.0.
	 * @return		f(x) = x<sup>k</sup> / (x<sup>k</sup> + a(s)(1-x)<sup>k</sup>).<br/>
	 * 				a(s) = (s / (1-s))<sup>k-1</sup>.<br/>
	 * 				s(a) = a<sup>1 / (k-1)</sup> / (1 + a<sup>1 / (k-1)</sup>).<br/>
	 * 				0.0 &le; f(x) &le; 1.0.<br/>
	 * 				f(x) = 1 - f(1-x) only when s = 0.5.<br/>
	 * 				f(x<sub>1</sub>) < f(x<sub>2</sub>) when x<sub>1</sub> < x<sub>2</sub>.<br/>
	 * 				f&#39;(0) = f&#39;(1) = 0, f&#39;(s) = k.<br/>
	 * 				f&#39;(x) = f&#39;(1-x) only when s = 0.5.
	 */
	@SuppressWarnings("GrazieInspection")
	public static double factSmooth(double x, double k, double s) {
		double dp = Math.pow(x, k);
		double dn = Math.pow(1.0D - x, k);
		double as = Math.pow(s / (1.0D - s), k - 1.0D);
		return dp / (dp + as * dn);
	}
}
