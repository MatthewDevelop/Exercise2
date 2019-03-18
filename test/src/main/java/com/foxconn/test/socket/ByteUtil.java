package com.foxconn.test.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 工具类
 * 
 */
public class ByteUtil {

	/**
	 * 十六进制转换
	 * 
	 * @param hash
	 * @return
	 */
	public static final String toHex(byte hash[]) {
		if (hash == null) {
			return "";
		}
		StringBuffer buf = new StringBuffer(hash.length * 2);
		int i;

		for (i = 0; i < hash.length; i++) {
			if ((hash[i] & 0xff) < 0x10) {
				buf.append("0");
			}
			buf.append(Long.toString(hash[i] & 0xff, 16));
		}
		return buf.toString();
	}

	/**
	 * 字符串转换
	 * 
	 * @param datas
	 * @return
	 */
	public static String toStr(byte[] datas) {
		if (datas == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		if (datas != null && datas.length > 0) {
			for (byte d : datas) {
				sb.append((char) (d));
			}
		}
		return sb.toString();
	}

	/**
	 * int型转换
	 * 
	 * @param v
	 * @return
	 */
	public static int toInt(String v) {
		return Integer.valueOf(v);
	}

	/**
	 * 
	 * @param b
	 * @return
	 */
	public static int makeUint8(byte b) {
		return (0xff & b);
	}

	/**
	 * 
	 * @param b
	 * @param c
	 * @return
	 */
	public static int makeUint16(byte b, byte c) {
		return ((0xff & b) << 8) | (0xff & c);
	}

	/**
	 * 
	 * @param b
	 * @param c
	 * @param d
	 * @param e
	 * @return
	 */
	public static int makeUint32(byte b, byte c, byte d, byte e) {
		return ((0xff & b) << 24) | ((0xff & c) << 16) | ((0xff & d) << 8)
				| (0xff & e);
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public static byte[] makeByte2(int v) {
		byte[] buf = { (byte) ((v & 0xff00) >> 8), (byte) (v & 0xff) };
		return buf;
	}

	/**
	 * 
	 * @param v
	 * @return
	 */
	public static byte makeByte1(int v) {
		return (byte) (v & 0xff);
	}

	/**
	 * 
	 * @param t
	 * @return
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
			try {
				sw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * long转换为byte[]
	 * */
	public static byte[] longToByteArray(long s, int size) {
		byte[] targets = new byte[size];
		for (int i = 0; i < size; i++) {
			int offset = (targets.length - 1 - i) * 8;
			targets[i] = (byte) ((s >>> offset) & 0xff);
		}
		return targets;
	}
}
