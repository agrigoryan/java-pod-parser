package com.agrigoryan.podparser;

public class PODBlendOp {
	private static int val = 0;

	public static final int BLEND_OP_ADD = (val = 0x8006);
	public static final int BLEND_OP_MIN = ++val;
	public static final int BLEND_OP_MAX = ++val;
	public static final int BLEND_OP_SUBTRACT = (val = 0x800A);
	public static final int BLEND_OP_REVERSE_SUBTRACT = ++val;
}
