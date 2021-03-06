package com.agrigoryan.podparser;


import java.io.IOException;
import java.util.Stack;

import static com.agrigoryan.podparser.PODBlockType.*;

public class PODParser {

	private static final long POD_BLOCK_END = 0x80000000L;
	private static String SUPPORTED_VERSION = "AB.POD.2.0 ";
	private BlockMarkersPool markersPool;

	private PODSceneBlock scene;

	public PODParser() {
		markersPool = new BlockMarkersPool();
	}

	public PODSceneBlock getScene() {
		return scene;
	}

	public PODSceneBlock read(PODReader src) throws IOException, PODParseException {
		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			if (marker.name == POD_VERSION) {
				readVersion(marker, src);
			} else if (marker.name == POD_SCENE) {
				this.scene = new PODSceneBlock();
				readScene(this.scene, src);
				break;
			} else if (marker.name == (POD_SCENE | POD_BLOCK_END)) {
				break;
			} else if (marker.name == POD_EXP_OPT) {
				src.skipBytes(marker.length);
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
		return this.scene;
	}

	private void readVersion(PODBlockMarker marker, PODReader src) throws PODParseException, IOException {
		String version = src.readString(marker.length);
		if (!SUPPORTED_VERSION.equals(version)) {
			throw new PODParseException("unsupported pod file version " + version);
		}
	}

	private void readScene(PODSceneBlock scene, PODReader src) throws IOException, PODParseException {
		int cameras = 0;
		int lights = 0;
		int materials = 0;
		int meshes = 0;
		int textures = 0;
		int nodes = 0;

		scene.fps = 30;

		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			if (marker.name == (POD_SCENE | POD_BLOCK_END)) {
				if ((cameras != scene.numCamera) ||
					(lights != scene.numLight) ||
					(materials != scene.numMaterial) ||
					(meshes != scene.numMesh) ||
					(textures != scene.numTexture) ||
					(nodes != scene.numNode)) {
					throw new PODParseException("invalid pod data");
				}
				break;
			} else if (marker.name == POD_COLOR_BACKGROUND) {
				src.readFloatArray(scene.colorBackground);
			} else if (marker.name == POD_COLOR_AMBIENT) {
				src.readFloatArray(scene.colorAmbient);
			} else if (marker.name == POD_NUM_CAMERA) {
				scene.numCamera = src.readInt();
				scene.cameras = new PODCameraBlock[scene.numCamera];
			} else if (marker.name == POD_NUM_LIGHT) {
				scene.numLight = src.readInt();
				scene.lights = new PODLightBlock[scene.numLight];
			} else if (marker.name == POD_NUM_MESH) {
				scene.numMesh = src.readInt();
				scene.meshes = new PODMeshBlock[scene.numMesh];
			} else if (marker.name == POD_NUM_NODE) {
				scene.numNode = src.readInt();
				scene.nodes = new PODNodeBlock[scene.numNode];
			} else if (marker.name == POD_NUM_MESH_NODE) {
				scene.numMeshNode = src.readInt();
			} else if (marker.name == POD_NUM_TEXTURE) {
				scene.numTexture = src.readInt();
				scene.textures = new PODTextureBlock[scene.numTexture];
			} else if (marker.name == POD_NUM_MATERIAL) {
				scene.numMaterial = src.readInt();
				scene.materials = new PODMaterialBlock[scene.numMaterial];
			} else if (marker.name == POD_NUM_FRAME) {
				scene.numFrame = src.readInt();
			} else if (marker.name == POD_FPS) {
				scene.fps = src.readInt();
			} else if (marker.name == POD_FLAGS) {
				scene.flags = src.readInt();
			} else if (marker.name == POD_FLAGS) {
				scene.flags = src.readInt();
			} else if (marker.name == POD_CAMERA) {
				PODCameraBlock camera = new PODCameraBlock();
				scene.cameras[cameras++] = camera;
				readCamera(camera, src);
			} else if (marker.name == POD_LIGHT) {
				PODLightBlock light = new PODLightBlock();
				scene.lights[lights++] = light;
				readLight(light, src);
			} else if (marker.name == POD_MATERIAL) {
				PODMaterialBlock material = new PODMaterialBlock();
				scene.materials[materials++] = material;
				readMaterial(material, src);
			} else if (marker.name == POD_MESH) {
				PODMeshBlock mesh = new PODMeshBlock();
				scene.meshes[meshes++] = mesh;
				readMesh(mesh, src);
			} else if (marker.name == POD_NODE) {
				PODNodeBlock node = new PODNodeBlock();
				scene.nodes[nodes++] = node;
				readNode(node, src);
			} else if (marker.name == POD_TEXTURE) {
				PODTextureBlock texture = new PODTextureBlock();
				scene.textures[textures++] = texture;
				readTexture(texture, src);
			} else if (marker.name == POD_USER_DATA) {
				scene.userData = new byte[marker.length];
				src.readFully(scene.userData);
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
	}

	private void readCamera(PODCameraBlock camera, PODReader src) throws IOException {
		camera.animFov = null;

		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			if (marker.name == (POD_CAMERA | POD_BLOCK_END)) {
				//END READING
				break;
			} else if (marker.name == POD_CAM_IDX_TGT) {
				camera.idxTarget = src.readInt();
			} else if (marker.name == POD_CAM_FOV) {
				camera.fov = src.readFloat();
			} else if (marker.name == POD_CAM_FAR) {
				camera.far = src.readFloat();
			} else if (marker.name == POD_CAM_NEAR) {
				camera.near = src.readFloat();
			} else if (marker.name == POD_CAM_ANIM_FOV) {
				camera.numFrames = marker.length;
				camera.animFov = new float[camera.numFrames];
				src.readFloatArray(camera.animFov);
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
	}

	private void readLight(PODLightBlock light, PODReader src) throws IOException {
		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			if (marker.name == (POD_LIGHT | POD_BLOCK_END)) {
				//END READING
				break;
			} else if (marker.name == POD_LIGHT_IDX_TGT) {
				light.idxTarget = src.readInt();
			} else if (marker.name == POD_LIGHT_COLOR) {
				src.readFloatArray(light.color);
			} else if (marker.name == POD_LIGHT_TYPE) {
				light.type = src.readInt();
			} else if (marker.name == POD_LIGHT_CONSTANT_ATTENUATION) {
				light.constantAttenuation = src.readFloat();
			} else if (marker.name == POD_LIGHT_LINEAR_ATTENUATION) {
				light.linearAttenuation = src.readFloat();
			} else if (marker.name == POD_LIGHT_QUADRATIC_ATTENUATION) {
				light.quadraticAttenuation = src.readFloat();
			} else if (marker.name == POD_LIGHT_FALLOFF_ANGLE) {
				light.falloffAngle = src.readFloat();
			} else if (marker.name == POD_LIGHT_FALLOFF_EXPONENT) {
				light.falloffExponent = src.readFloat();
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
	}

	private void readMaterial(PODMaterialBlock material, PODReader src) throws IOException {
		material.idxTexDiffuse = -1;
		material.idxTexSpecularColor = -1;
		material.idxTexSpecularLevel = -1;
		material.idxTexBump = -1;
		material.idxTexEmissive = -1;
		material.idxTexGlossiness = -1;
		material.idxTexOpacity = -1;
		material.idxTexReflection = -1;
		material.idxTexRefraction = -1;

		material.blendSrcRGB = material.blendSrcA = PODBlendFunc.BLEND_FUNC_ONE;
		material.blendDstRGB = material.blendDstA = PODBlendFunc.BLEND_FUNC_ZERO;
		material.blendOpRGB = material.blendOpA = PODBlendOp.BLEND_OP_ADD;

		material.flags = 0;
		material.userData = null;

		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			if (marker.name == (POD_MATERIAL | POD_BLOCK_END)) {
				//END READING
				break;
			} else if (marker.name == POD_MAT_FLAGS) {
				material.flags = src.readInt();
			} else if (marker.name == POD_MAT_NAME) {
				material.name = src.readString(marker.length).trim();
			} else if (marker.name == POD_MAT_IDX_TEX_DIFFUSE) {
				material.idxTexDiffuse = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_AMBIENT) {
				material.idxTexAmbient = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_SPECULAR_COLOR) {
				material.idxTexSpecularColor = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_SPECULAR_LEVEL) {
				material.idxTexSpecularLevel = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_BUMP) {
				material.idxTexBump = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_EMISSIVE) {
				material.idxTexEmissive = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_GLOSSINESS) {
				material.idxTexGlossiness = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_OPACITY) {
				material.idxTexOpacity = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_REFLECTION) {
				material.idxTexReflection = src.readInt();
			} else if (marker.name == POD_MAT_IDX_TEX_REFRACTION) {
				material.idxTexRefraction = src.readInt();
			} else if (marker.name == POD_MAT_OPACITY) {
				material.matOpacity = src.readFloat();
			} else if (marker.name == POD_MAT_AMBIENT) {
				src.readFloatArray(material.matAmbient, 3);
				material.matAmbient[3] = 1f;
			} else if (marker.name == POD_MAT_DIFFUSE) {
				src.readFloatArray(material.matDiffuse, 3);
				material.matDiffuse[3] = 1f;
			} else if (marker.name == POD_MAT_SPECULAR) {
				src.readFloatArray(material.matSpecular, 3);
				material.matSpecular[3] = 1f;
			} else if (marker.name == POD_MAT_SHININESS) {
				material.matShininess = src.readFloat();
			} else if (marker.name == POD_MAT_EFFECT_FILE) {
				material.effectFile = src.readString(marker.length).trim();
			} else if (marker.name == POD_MAT_EFFECT_NAME) {
				material.effectName = src.readString(marker.length).trim();
			} else if (marker.name == POD_MAT_BLEND_SRC_RGB) {
				material.blendSrcRGB = src.readInt();
			} else if (marker.name == POD_MAT_BLEND_SRC_A) {
				material.blendSrcA = src.readInt();
			} else if (marker.name == POD_MAT_BLEND_DST_RGB) {
				material.blendDstRGB = src.readInt();
			} else if (marker.name == POD_MAT_BLEND_DST_A) {
				material.blendDstA = src.readInt();
			} else if (marker.name == POD_MAT_BLEND_OP_RGB) {
				material.blendOpRGB = src.readInt();
			} else if (marker.name == POD_MAT_BLEND_OP_A) {
				material.blendOpA = src.readInt();
			} else if (marker.name == POD_MAT_BLEND_COLOR) {
				src.readFloatArray(material.blendColor);
			} else if (marker.name == POD_MAT_BLEND_FACTOR) {
				src.readFloatArray(material.blendFactor);
			} else if (marker.name == POD_MAT_USER_DATA) {
				material.userData = new byte[marker.length];
				src.readFully(material.userData);
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
	}

	private void readMesh(PODMeshBlock mesh, PODReader src) throws IOException, PODParseException {
		int uvws = 0;

		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			if (marker.name == (POD_MESH | POD_BLOCK_END)) {
				if (uvws != mesh.numUVW) {
					throw new PODParseException("invalid pod data");
				}
				if (mesh.numStrips != 0) {
					System.out.println("PAHOOOOO " + mesh.numStrips);
				}
				fixInterleavedEndianness(mesh); //do we need this? we can change endianness when reading values
				break;
			} else if (marker.name == POD_MESH_NUM_VTX) {
				mesh.numVertex = src.readInt();
			} else if (marker.name == POD_MESH_NUM_FACES) {
				mesh.numFaces = src.readInt();
			} else if (marker.name == POD_MESH_NUM_UVW) {
				mesh.numUVW = src.readInt();
				mesh.uvw = new PODDataBlock[mesh.numUVW];
			} else if (marker.name == POD_MESH_STRIP_LENGTH) {
				mesh.stripLength = new int[marker.length / 4];
				src.readIntArray(mesh.stripLength);
			} else if (marker.name == POD_MESH_NUM_STRIPS) {
				mesh.numStrips = src.readInt();
			} else if (marker.name == POD_MESH_INTERLEAVED) {
				mesh.interleavedData = true;
				mesh.interleaved = new byte[marker.length];
				src.readFully(mesh.interleaved);
			} else if (marker.name == POD_MESH_BONE_BATCHES) {
				if (mesh.boneBatches == null) mesh.boneBatches = new PODBoneBatchesBlock();
				mesh.boneBatches.batches = new int[marker.length / 4];
				src.readIntArray(mesh.boneBatches.batches);
			} else if (marker.name == POD_MESH_BONE_BATCH_BONE_CNTS) {
				if (mesh.boneBatches == null) mesh.boneBatches = new PODBoneBatchesBlock();
				mesh.boneBatches.batchBoneCnt = new int[marker.length / 4];
				src.readIntArray(mesh.boneBatches.batchBoneCnt);
			} else if (marker.name == POD_MESH_BONE_BATCH_OFFSETS) {
				if (mesh.boneBatches == null) mesh.boneBatches = new PODBoneBatchesBlock();
				mesh.boneBatches.batchOffset = new int[marker.length / 4];
				src.readIntArray(mesh.boneBatches.batchOffset);
			} else if (marker.name == POD_MESH_BONE_BATCH_BONE_MAX) {
				if (mesh.boneBatches == null) mesh.boneBatches = new PODBoneBatchesBlock();
				mesh.boneBatches.batchBoneMax = src.readInt();
			} else if (marker.name == POD_MESH_BONE_BATCH_CNT) {
				if (mesh.boneBatches == null) mesh.boneBatches = new PODBoneBatchesBlock();
				mesh.boneBatches.batchCnt = src.readInt();
			} else if (marker.name == POD_MESH_FACES) {
				mesh.faces = new PODDataBlock();
				readPODData(mesh.faces, src, POD_MESH_FACES, true);
			} else if (marker.name == POD_MESH_VTX) {
				mesh.vertex = new PODDataBlock();
				readPODData(mesh.vertex, src, POD_MESH_VTX, (mesh.interleaved == null));
			} else if (marker.name == POD_MESH_NOR) {
				mesh.normals = new PODDataBlock();
				readPODData(mesh.normals, src, POD_MESH_NOR, (mesh.interleaved == null));
			} else if (marker.name == POD_MESH_TAN) {
				mesh.tangents = new PODDataBlock();
				readPODData(mesh.tangents, src, POD_MESH_TAN, (mesh.interleaved == null));
			} else if (marker.name == POD_MESH_BIN) {
				mesh.binormals = new PODDataBlock();
				readPODData(mesh.binormals, src, POD_MESH_BIN, (mesh.interleaved == null));
			} else if (marker.name == POD_MESH_UVW) {
				PODDataBlock uvw = new PODDataBlock();
				mesh.uvw[uvws++] = uvw;
				readPODData(uvw, src, POD_MESH_UVW, (mesh.interleaved == null));
			} else if (marker.name == POD_MESH_VTX_COL) {
				mesh.vtxColors = new PODDataBlock();
				readPODData(mesh.vtxColors, src, POD_MESH_VTX_COL, (mesh.interleaved == null));
			} else if (marker.name == POD_MESH_BONE_IDX) {
				mesh.boneIdx = new PODDataBlock();
				readPODData(mesh.boneIdx, src, POD_MESH_BONE_IDX, (mesh.interleaved == null));
			} else if (marker.name == POD_MESH_BONE_WEIGHT) {
				mesh.boneWeight = new PODDataBlock();
				readPODData(mesh.boneWeight, src, POD_MESH_BONE_WEIGHT, (mesh.interleaved == null));
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
	}

	private void readPODData(PODDataBlock data, PODReader src, int spec, boolean validData) throws IOException, PODParseException {
		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			if (marker.name == (spec | POD_BLOCK_END)) {
				//END READING
				break;
			} else if (marker.name == POD_DATA_TYPE) {
				data.type = src.readInt();
			} else if (marker.name == POD_N) {
				data.n = src.readInt();
			} else if (marker.name == POD_STRIDE) {
				data.stride = src.readInt();
			} else if (marker.name == POD_DATA) {
				if (validData) {
					int size = PODUtils.dataTypeSize(data.type);
					if (size == 0) {
						throw new PODParseException("invalid pod data");
					}
					switch (size) {
						case 1: {
							data.data = new byte[marker.length];
							src.readFully((byte[]) data.data);
							break;
						}
						case 2: {
							data.data = new short[marker.length / 2];
							src.readShortArray((short[]) data.data);
							break;
						}
						case 4: {
							data.data = new int[marker.length / 4];
							src.readIntArray((int[]) data.data);
							break;
						}
						default: {
						}
					}
				} else {
					data.data = src.readInt();
				}
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
	}

	private void readNode(PODNodeBlock node, PODReader src) throws IOException {
		boolean oldNodeFormat = false;
		float[] pos = {0f, 0f, 0f};
		float[] quat = {0f, 0f, 0f, 1f};
		float[] scale = {1f, 1f, 1f, 0f, 0f, 0f, 0f};

		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			//END READING
			if (marker.name == (POD_NODE | POD_BLOCK_END)) {
				if (oldNodeFormat) {
					if (node.animPosition != null) {
						node.animFlags |= PODAnimationFlags.HAS_POSITION_ANIM;
					} else {
						node.animPosition = pos;
					}
					if (node.animRotation != null) {
						node.animFlags |= PODAnimationFlags.HAS_ROTATION_ANIM;
					} else {
						node.animRotation = quat;
					}
					if (node.animScale != null) {
						node.animFlags |= PODAnimationFlags.HAS_SCALE_ANIM;
					} else {
						node.animScale = scale;
					}
				}
				break;
			} else if (marker.name == POD_NODE_IDX) {
				node.idx = src.readInt();
			} else if (marker.name == POD_NODE_NAME) {
				node.name = src.readString(marker.length).trim();
			} else if (marker.name == POD_NODE_IDX_MAT) {
				node.idxMaterial = src.readInt();
			} else if (marker.name == POD_NODE_IDX_PARENT) {
				node.idxParent = src.readInt();
			} else if (marker.name == POD_NODE_ANIM_FLAGS) {
				node.animFlags = src.readInt();
			} else if (marker.name == POD_NODE_ANIM_POS_IDX) {
				node.animPositionIdx = new int[marker.length / 4];
				src.readIntArray(node.animPositionIdx);
			} else if (marker.name == POD_NODE_ANIM_POS) {
				node.animPosition = new float[marker.length / 4];
				src.readFloatArray(node.animPosition);
			} else if (marker.name == POD_NODE_ANIM_ROT_IDX) {
				node.animRotationIdx = new int[marker.length / 4];
				src.readIntArray(node.animRotationIdx);
			} else if (marker.name == POD_NODE_ANIM_ROT) {
				node.animRotation = new float[marker.length / 4];
				src.readFloatArray(node.animRotation);
			} else if (marker.name == POD_NODE_ANIM_SCALE_IDX) {
				node.animScaleIdx = new int[marker.length / 4];
				src.readIntArray(node.animScaleIdx);
			} else if (marker.name == POD_NODE_ANIM_SCALE) {
				node.animScale = new float[marker.length / 4];
				src.readFloatArray(node.animScale);
			} else if (marker.name == POD_NODE_ANIM_MATRIX_IDX) {
				node.animMatrixIdx = new int[marker.length / 4];
				src.readIntArray(node.animMatrixIdx);
			} else if (marker.name == POD_NODE_ANIM_MATRIX) {
				node.animMatrix = new float[marker.length / 4];
				src.readFloatArray(node.animMatrix);
			} else if (marker.name == POD_NODE_USER_DATA) {
				node.userData = new byte[marker.length];
				src.readFully(node.userData);
			} else if (marker.name == POD_NODE_POS) {
				oldNodeFormat = true;
				src.readFloatArray(pos, 3);
			} else if (marker.name == POD_NODE_ROT) {
				oldNodeFormat = true;
				src.readFloatArray(quat, 4);
			} else if (marker.name == POD_NODE_SCALE) {
				oldNodeFormat = true;
				src.readFloatArray(scale, 3);
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
	}

	private void readTexture(PODTextureBlock texture, PODReader src) throws IOException {
		PODBlockMarker marker = obtainMarker();
		while (src.readMarker(marker)) {
			if (marker.name == (POD_TEXTURE | POD_BLOCK_END)) {
				//END READING
				break;
			} else if (marker.name == POD_TEX_NAME) {
				texture.name = src.readString(marker.length).trim();
			} else {
				src.skipBytes(marker.length);
			}
		}
		free(marker);
	}

	private void fixInterleavedEndianness(PODMeshBlock mesh) throws PODParseException {
		if (!mesh.interleavedData) {
			return;
		}
		fixPODDataInterleavedEndianness(mesh.interleaved, mesh.vertex, mesh.numVertex);
		fixPODDataInterleavedEndianness(mesh.interleaved, mesh.normals, mesh.numVertex);
		fixPODDataInterleavedEndianness(mesh.interleaved, mesh.tangents, mesh.numVertex);
		fixPODDataInterleavedEndianness(mesh.interleaved, mesh.binormals, mesh.numVertex);

		for (int i = 0; i < mesh.numUVW; i++) {
			fixPODDataInterleavedEndianness(mesh.interleaved, mesh.uvw[i], mesh.numVertex);
		}

		fixPODDataInterleavedEndianness(mesh.interleaved, mesh.vtxColors, mesh.numVertex);
		fixPODDataInterleavedEndianness(mesh.interleaved, mesh.boneIdx, mesh.numVertex);
		fixPODDataInterleavedEndianness(mesh.interleaved, mesh.boneWeight, mesh.numVertex);
	}

	private void fixPODDataInterleavedEndianness(byte[] interleaved, PODDataBlock data, int elementCount) throws PODParseException {
		if (data.n == 0) {
			return;
		}
		int size = PODUtils.dataTypeSize(data.type);
		if (size == 0) {
			throw new PODParseException("invalid pod data");
		}
		int offset = (Integer) data.data;
		byte v1, v2, v3, v4;
		switch (size) {
			case 1:
				return;
			case 2: {
				for (int i = 0; i < elementCount; i++) {
					for (int j = 0; j < data.n; j++) {
						v1 = interleaved[offset + size * j];
						v2 = interleaved[offset + size * j + 1];
						interleaved[offset + size * j] = v2;
						interleaved[offset + size * j + 1] = v1;
					}
					offset += data.stride;
				}
				break;
			}
			case 4: {
				for (int i = 0; i < elementCount; i++) {
					for (int j = 0; j < data.n; j++) {
						v1 = interleaved[offset + size * j];
						v2 = interleaved[offset + size * j + 1];
						v3 = interleaved[offset + size * j + 2];
						v4 = interleaved[offset + size * j + 3];
						interleaved[offset + size * j] = v4;
						interleaved[offset + size * j + 1] = v3;
						interleaved[offset + size * j + 2] = v2;
						interleaved[offset + size * j + 3] = v1;
					}
					offset += data.stride;
				}
				break;
			}
			default: {
			}
		}
	}

	private PODBlockMarker obtainMarker() {
		return markersPool.obtain();
	}

	private void free(PODBlockMarker marker) {
		markersPool.free(marker);
	}

	private static final class BlockMarkersPool {

		private Stack<PODBlockMarker> freeMarkers;

		public BlockMarkersPool() {
			freeMarkers = new Stack<PODBlockMarker>();
		}

		public PODBlockMarker obtain() {
			if (freeMarkers.size() > 0) {
				return freeMarkers.pop();
			} else {
				return new PODBlockMarker();
			}
		}

		public void free(PODBlockMarker marker) {
			if (marker == null) {
				throw new IllegalArgumentException("object cannot be null");
			}
			freeMarkers.add(marker);
		}
	}
}