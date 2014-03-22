package com.agrigoryan.podparser;

public class PODLightBlock {

	public static final int LIGHT_TYPE_POINT = 0;
	public static final int LIGHT_TYPE_DIRECTIONAL = 1;
	public static final int LIGHT_TYPE_SPOT = 2;

	public int idxTarget;
	public float[] color = new float[3];
	public int type;
	public float constantAttenuation;
	public float linearAttenuation;
	public float quadraticAttenuation;
	public float falloffAngle;
	public float falloffExponent;
}
