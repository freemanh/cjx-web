package com.chejixing.util;

public class CRCUtils {
	public static final short POLY16 = (short) 0xA001;

	public static short getCrc16(byte[] buf, int length) {
		short crc = (short) 0xFFFF;
		for (int i = 0; i < length; i++) {
			crc ^= (buf[i] & (short) 0x00ff);
			for (int j = 0; j < 8; j++) {
				if ((crc & 1) != 0) {
					crc = (short) ((crc >>> 1) & 0x7fff);
					crc ^= POLY16;
				} else {
					crc = (short) ((crc >>> 1) & 0x7fff);
				}
			}
		}
		return crc;
	}

	public static void main(String[] args) {
		byte[] bytes = new byte[] {0x00, 0x03,0x0e, 0x00, 0x03, 0x01, 0x06, 0x00, 0x4a, 0x00, (byte)0x97, 0x00, 0x14, 0x01,  0x00, 0x01, 0x02};

		short crc2 = CRCUtils.getCrc16(bytes, bytes.length);
		System.out.println(Integer.toHexString(crc2));

	}

	public static short getCrc16(byte[] content) {
		return getCrc16(content, content.length);
	}
}
