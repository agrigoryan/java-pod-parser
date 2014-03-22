package com.agrigoryan.podparser;

public class PODBlockType {

	private static int val = 0;

	public static final int POD_VERSION = (val = 1000);
	public static final int POD_SCENE = ++val;
	public static final int POD_EXP_OPT = ++val;
	public static final int POD_HISTORY = ++val;
	public static final int POD_ENDIANNESS_MIS_MATCH = -402456576;

	public static final int POD_COLOR_BACKGROUND = (val = 2000);
	public static final int POD_COLOR_AMBIENT = ++val;
	public static final int POD_NUM_CAMERA = ++val;
	public static final int POD_NUM_LIGHT = ++val;
	public static final int POD_NUM_MESH = ++val;
	public static final int POD_NUM_NODE = ++val;
	public static final int POD_NUM_MESH_NODE = ++val;
	public static final int POD_NUM_TEXTURE = ++val;
	public static final int POD_NUM_MATERIAL = ++val;
	public static final int POD_NUM_FRAME = ++val;
	public static final int POD_CAMERA = ++val;
	public static final int POD_LIGHT = ++val;
	public static final int POD_MESH = ++val;
	public static final int POD_NODE = ++val;
	public static final int POD_TEXTURE = ++val;
	public static final int POD_MATERIAL = ++val;
	public static final int POD_FLAGS = ++val;
	public static final int POD_FPS = ++val;
	public static final int POD_USER_DATA = ++val;

	public static final int POD_MAT_NAME = (val = 3000);
	public static final int POD_MAT_IDX_TEX_DIFFUSE = ++val;
	public static final int POD_MAT_OPACITY = ++val;
	public static final int POD_MAT_AMBIENT = ++val;
	public static final int POD_MAT_DIFFUSE = ++val;
	public static final int POD_MAT_SPECULAR = ++val;
	public static final int POD_MAT_SHININESS = ++val;
	public static final int POD_MAT_EFFECT_FILE = ++val;
	public static final int POD_MAT_EFFECT_NAME = ++val;
	public static final int POD_MAT_IDX_TEX_AMBIENT = ++val;
	public static final int POD_MAT_IDX_TEX_SPECULAR_COLOR = ++val;
	public static final int POD_MAT_IDX_TEX_SPECULAR_LEVEL = ++val;
	public static final int POD_MAT_IDX_TEX_BUMP = ++val;
	public static final int POD_MAT_IDX_TEX_EMISSIVE = ++val;
	public static final int POD_MAT_IDX_TEX_GLOSSINESS = ++val;
	public static final int POD_MAT_IDX_TEX_OPACITY = ++val;
	public static final int POD_MAT_IDX_TEX_REFLECTION = ++val;
	public static final int POD_MAT_IDX_TEX_REFRACTION = ++val;
	public static final int POD_MAT_BLEND_SRC_RGB = ++val;
	public static final int POD_MAT_BLEND_SRC_A = ++val;
	public static final int POD_MAT_BLEND_DST_RGB = ++val;
	public static final int POD_MAT_BLEND_DST_A = ++val;
	public static final int POD_MAT_BLEND_OP_RGB = ++val;
	public static final int POD_MAT_BLEND_OP_A = ++val;
	public static final int POD_MAT_BLEND_COLOR = ++val;
	public static final int POD_MAT_BLEND_FACTOR = ++val;
	public static final int POD_MAT_FLAGS = ++val;
	public static final int POD_MAT_USER_DATA = ++val;

	public static final int POD_TEX_NAME = (val = 4000);

	public static final int POD_NODE_IDX = (val = 5000);
	public static final int POD_NODE_NAME = ++val;
	public static final int POD_NODE_IDX_MAT = ++val;
	public static final int POD_NODE_IDX_PARENT = ++val;
	public static final int POD_NODE_POS = ++val;
	public static final int POD_NODE_ROT = ++val;
	public static final int POD_NODE_SCALE = ++val;
	public static final int POD_NODE_ANIM_POS = ++val;
	public static final int POD_NODE_ANIM_ROT = ++val;
	public static final int POD_NODE_ANIM_SCALE = ++val;
	public static final int POD_NODE_MATRIX = ++val;
	public static final int POD_NODE_ANIM_MATRIX = ++val;
	public static final int POD_NODE_ANIM_FLAGS = ++val;
	public static final int POD_NODE_ANIM_POS_IDX = ++val;
	public static final int POD_NODE_ANIM_ROT_IDX = ++val;
	public static final int POD_NODE_ANIM_SCALE_IDX = ++val;
	public static final int POD_NODE_ANIM_MATRIX_IDX = ++val;
	public static final int POD_NODE_USER_DATA = ++val;

	public static final int POD_MESH_NUM_VTX = (val = 6000);
	public static final int POD_MESH_NUM_FACES = ++val;
	public static final int POD_MESH_NUM_UVW = ++val;
	public static final int POD_MESH_FACES = ++val;
	public static final int POD_MESH_STRIP_LENGTH = ++val;
	public static final int POD_MESH_NUM_STRIPS = ++val;
	public static final int POD_MESH_VTX = ++val;
	public static final int POD_MESH_NOR = ++val;
	public static final int POD_MESH_TAN = ++val;
	public static final int POD_MESH_BIN = ++val;
	public static final int POD_MESH_UVW = ++val;    // Will come multiple times
	public static final int POD_MESH_VTX_COL = ++val;
	public static final int POD_MESH_BONE_IDX = ++val;
	public static final int POD_MESH_BONE_WEIGHT = ++val;
	public static final int POD_MESH_INTERLEAVED = ++val;
	public static final int POD_MESH_BONE_BATCHES = ++val;
	public static final int POD_MESH_BONE_BATCH_BONE_CNTS = ++val;
	public static final int POD_MESH_BONE_BATCH_OFFSETS = ++val;
	public static final int POD_MESH_BONE_BATCH_BONE_MAX = ++val;
	public static final int POD_MESH_BONE_BATCH_CNT = ++val;
	public static final int POD_MESH_UNPACK_MATRIX = ++val;

	public static final int POD_LIGHT_IDX_TGT = (val = 7000);
	public static final int POD_LIGHT_COLOR = ++val;
	public static final int POD_LIGHT_TYPE = ++val;
	public static final int POD_LIGHT_CONSTANT_ATTENUATION = ++val;
	public static final int POD_LIGHT_LINEAR_ATTENUATION = ++val;
	public static final int POD_LIGHT_QUADRATIC_ATTENUATION = ++val;
	public static final int POD_LIGHT_FALLOFF_ANGLE = ++val;
	public static final int POD_LIGHT_FALLOFF_EXPONENT = ++val;

	public static final int POD_CAM_IDX_TGT = (val = 8000);
	public static final int POD_CAM_FOV = ++val;
	public static final int POD_CAM_FAR = ++val;
	public static final int POD_CAM_NEAR = ++val;
	public static final int POD_CAM_ANIM_FOV = ++val;

	public static final int POD_DATA_TYPE = (val = 9000);
	public static final int POD_N = ++val;
	public static final int POD_STRIDE = ++val;
	public static final int POD_DATA = ++val;
}