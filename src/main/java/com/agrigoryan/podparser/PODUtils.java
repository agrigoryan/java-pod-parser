package com.agrigoryan.podparser;

public class PODUtils {
	public static int dataTypeSize(int type) {
		if (type == PODDataType.DATA_FLOAT) {
			return 4;
		} else if (type == PODDataType.DATA_INT) {
			return 4;
		} else if (type == PODDataType.DATA_SHORT
			|| type == PODDataType.DATA_SHORT_NORM
			|| type == PODDataType.DATA_UNSIGNED_SHORT
			|| type == PODDataType.DATA_UNSIGNED_SHORT_NORM) {
			return 2;
		} else if (type == PODDataType.DATA_RGBA
			|| type == PODDataType.DATA_ARGB
			|| type == PODDataType.DATA_D3DCOLOR
			|| type == PODDataType.DATA_UBYTE4
			|| type == PODDataType.DATA_DEC3N
			|| type == PODDataType.DATA_FIXED_16_16) {
			return 4;
		} else if (type == PODDataType.DATA_BYTE
			|| type == PODDataType.DATA_BYTE_NORM
			|| type == PODDataType.DATA_UNSIGNED_BYTE
			|| type == PODDataType.DATA_UNSIGNED_BYTE_NORM) {
			return 1;
		} else if (type == PODDataType.DATA_HALF_FLOAT) {
			return 2;
		} else {
			return 0;
		}
	}
}
