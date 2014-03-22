package com.agrigoryan.podparser;

public class PODSceneBlock {
	public float[] colorBackground = new float[3];
	public float[] colorAmbient = new float[3];
	public int numCamera;
	public int numLight;
	public int numMesh;
	public int numNode;
	public int numMeshNode;
	public int numTexture;
	public int numMaterial;
	public int numFrame;
	public int fps;
	public int flags;
	public PODCameraBlock[] cameras;
	public PODLightBlock[] lights;
	public PODMeshBlock[] meshes;
	public PODNodeBlock[] nodes;
	public PODTextureBlock[] textures;
	public PODMaterialBlock[] materials;
	public byte[] userData;
}