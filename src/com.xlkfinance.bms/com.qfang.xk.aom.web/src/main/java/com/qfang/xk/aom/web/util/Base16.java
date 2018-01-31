package com.qfang.xk.aom.web.util;

public class Base16
{

	/**
	 * 对字节数据进行Base16编码。
	 * @param src 源字节数组
	 * @return 编码后的字符串
	 */
	public static String encode(byte src[]) throws Exception
	{
		StringBuffer strbuf = new StringBuffer(src.length * 2);
		int i;

		for (i = 0; i < src.length; i++)
		{
			if (((int) src[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) src[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	/**
	 * 对Base16编码的字符串进行解码。
	 * @param src 源字串
	 * @return 解码后的字节数组
	 */
	public static byte[] decode(String hexString) throws Exception
	{
		byte[] bts = new byte[hexString.length() / 2];
		for (int i = 0; i < bts.length; i++)
		{
			bts[i] = (byte) Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
		}
		return bts;
	}

}
