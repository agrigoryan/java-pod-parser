package com.agrigoryan.podparser;

public class PODDataType {
	private static int val = 0;

	public static final int DATA_NONE = (val = 0);
	public static final int DATA_FLOAT = ++val;
	public static final int DATA_INT = ++val;
	public static final int DATA_UNSIGNED_SHORT = ++val;
	public static final int DATA_RGBA = ++val;
	public static final int DATA_ARGB = ++val;
	public static final int DATA_D3DCOLOR = ++val;
	public static final int DATA_UBYTE4 = ++val;
	public static final int DATA_DEC3N = ++val;
	public static final int DATA_FIXED_16_16 = ++val;
	public static final int DATA_UNSIGNED_BYTE = ++val;
	public static final int DATA_SHORT = ++val;
	public static final int DATA_SHORT_NORM = ++val;
	public static final int DATA_BYTE = ++val;
	public static final int DATA_BYTE_NORM = ++val;
	public static final int DATA_UNSIGNED_BYTE_NORM = ++val;
	public static final int DATA_UNSIGNED_SHORT_NORM = ++val;
	public static final int DATA_HALF_FLOAT = ++val;
}