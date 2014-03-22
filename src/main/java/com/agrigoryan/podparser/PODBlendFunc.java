package com.agrigoryan.podparser;

public class PODBlendFunc {
	private static int val = 0;

	public static final int BLEND_FUNC_ZERO = (val = 0);
	public static final int BLEND_FUNC_ONE = ++val;
	public static final int BLEND_FUNC_BLEND_FACTOR = ++val;
	public static final int BLEND_FUNC_ONE_MINUS_BLEND_FACTOR = ++val;

	public static final int BLEND_FUNC_SRC_COLOR = (val = 0x3000);
	public static final int BLEND_FUNC_ONE_MINUS_SRC_COLOR = ++val;
	public static final int BLEND_FUNC_SRC_ALPHA = ++val;
	public static final int BLEND_FUNC_ONE_MINUS_SRC_ALPHA = ++val;
	public static final int BLEND_FUNC_DST_ALPHA = ++val;
	public static final int BLEND_FUNC_ONE_MINUS_DST_ALPHA = ++val;
	public static final int BLEND_FUNC_DST_COLOR = ++val;
	public static final int BLEND_FUNC_ONE_MINUS_DST_COLOR = ++val;
	public static final int BLEND_FUNC_SRC_ALPHA_SATURATE = ++val;

	public static final int BLEND_FUNC_CONSTANT_COLOR = (val = 0x8001);
	public static final int BLEND_FUNC_ONE_MINUS_CONSTANT_COLOR = ++val;
	public static final int BLEND_FUNC_CONSTANT_ALPHA = ++val;
	public static final int BLEND_FUNC_ONE_MINUS_CONSTANT_ALPHA = ++val;

}
