package com.agrigoryan.podparser;

public class PODMaterialBlock {
	public String name;
	public int idxTexDiffuse;
	public int idxTexAmbient;
	public int idxTexSpecularColor;
	public int idxTexSpecularLevel;
	public int idxTexBump;
	public int idxTexEmissive;
	public int idxTexGlossiness;
	public int idxTexOpacity;
	public int idxTexReflection;
	public int idxTexRefraction;
	public float matOpacity;
	public float[] matAmbient = new float[4];
	public float[] matDiffuse = new float[4];
	public float[] matSpecular = new float[4];
	public float matShininess;
	public String effectFile;
	public String effectName;
	public int blendSrcRGB;
	public int blendSrcA;
	public int blendDstRGB;
	public int blendDstA;
	public int blendOpRGB;
	public int blendOpA;
	public float[] blendColor = {0f, 0f, 0f, 0f};
	public float[] blendFactor = {0f, 0f, 0f, 0f};

	public int flags;
	public byte[] userData;
}
