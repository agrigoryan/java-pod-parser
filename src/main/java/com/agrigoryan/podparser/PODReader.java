package com.agrigoryan.podparser;

import java.io.*;

public class PODReader {

	private byte[] bytes;

	private DataInputStream data;

	public PODReader(InputStream in) throws IOException {
		try {
			this.bytes = loadBytes(in);
			this.data = new DataInputStream(new ByteArrayInputStream(this.bytes));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private byte[] loadBytes(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];

		int readBytes = 0;
		while ((readBytes = in.read(buffer)) > 0) {
			out.write(buffer, 0, readBytes);
		}

		out.close();
		return out.toByteArray();
	}

	public PODBlockMarker readMarker() throws IOException {
		PODBlockMarker marker = new PODBlockMarker();
		readMarker(marker);
		return marker;
	}

	public boolean readMarker(PODBlockMarker marker) throws IOException {
		marker.name = readUnsignedInt();
		marker.length = readInt();
		return true;
	}

	public void readFloatArray(float[] array) throws IOException {
		readFloatArray(array, array.length);
	}

	public void readFloatArray(float[] array, int n) throws IOException {
		for (int i = 0; i < n; i++) {
			array[i] = readFloat();
		}
	}

	public void readIntArray(int[] array) throws IOException {
		readIntArray(array, array.length);
	}

	public void readIntArray(int[] array, int n) throws IOException {
		for (int i = 0; i < n; i++) {
			array[i] = readInt();
		}
	}

	public void readShortArray(short[] array) throws IOException {
		readShortArray(array, array.length);
	}

	public void readShortArray(short[] array, int n) throws IOException {
		for (int i = 0; i < n; i++) {
			array[i] = readShort();
		}
	}

	public String readString(int n) throws IOException {
		byte[] bytes = new byte[n];
		readFully(bytes);
		return new String(bytes, "UTF-8");
	}

	public long readUnsignedInt() throws IOException {
		long n4 = data.read();
		long n3 = data.read();
		long n2 = data.read();
		long n1 = data.read();
		return ((n1 & 0xff) << 24) | ((n2 & 0xff) << 16) | ((n3 & 0xff) << 8) | (n4 & 0xff);
	}

	public void readFully(byte[] b) throws IOException {
		data.readFully(b);
	}

	public void readFully(byte[] b, int off, int len) throws IOException {
		data.readFully(b, off, len);
	}

	public int skipBytes(int n) throws IOException {
		return data.skipBytes(n);
	}

	public boolean readBoolean() throws IOException {
		return data.readBoolean();
	}

	public byte readByte() throws IOException {
		return data.readByte();
	}

	public int readUnsignedByte() throws IOException {
		return data.readUnsignedByte();
	}

	public short readShort() throws IOException {
		int low = data.read();
		int high = data.read();
		return (short) ((high << 8) | (low & 0xff));
	}

	public int readUnsignedShort() throws IOException {
		int low = data.read();
		int high = data.read();
		return ((high & 0xff) << 8) | (low & 0xff);
	}

	public char readChar() throws IOException {
		return data.readChar();
	}

	public int readInt() throws IOException {
		int n4 = data.read();
		int n3 = data.read();
		int n2 = data.read();
		int n1 = data.read();

		return ((n1 & 0xff) << 24) | ((n2 & 0xff) << 16) | ((n3 & 0xff) << 8) | (n4 & 0xff);
	}

	public long readLong() throws IOException {
		int n8 = data.read();
		int n7 = data.read();
		int n6 = data.read();
		int n5 = data.read();
		int n4 = data.read();
		int n3 = data.read();
		int n2 = data.read();
		int n1 = data.read();

		return (((long) (n1 & 0xff) << 56) | ((long) (n2 & 0xff) << 48) | ((long) (n3 & 0xff) << 40)
			| ((long) (n4 & 0xff) << 32) | ((long) (n5 & 0xff) << 24) | ((long) (n6 & 0xff) << 16)
			| ((long) (n7 & 0xff) << 8) | ((long) (n8 & 0xff)));
	}

	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}
}
