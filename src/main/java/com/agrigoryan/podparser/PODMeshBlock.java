package com.agrigoryan.podparser;

public class PODMeshBlock {
	public int numVertex;
	public int numFaces;
	public int numUVW;
	public int numStrips;
	public int[] stripLength;
	public PODDataBlock faces;
	public PODDataBlock vertex;
	public PODDataBlock normals;
	public PODDataBlock tangents;
	public PODDataBlock binormals;
	public PODDataBlock[] uvw;
	public PODDataBlock vtxColors;
	public PODDataBlock boneIdx;
	public PODDataBlock boneWeight;
	public boolean interleavedData;
	public byte[] interleaved;
	public PODBoneBatchesBlock boneBatches;
	public int primitiveType;
}
