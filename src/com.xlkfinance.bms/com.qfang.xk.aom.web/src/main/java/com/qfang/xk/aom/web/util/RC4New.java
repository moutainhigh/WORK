package com.qfang.xk.aom.web.util;

import java.io.UnsupportedEncodingException;

public class RC4New
{
	public static String decry_RC4(byte[] data, String key)
	{
		if (data == null || key == null)
		{
			return null;
		}
		return asString(RC4Base(data, key));
	}

	public static String decry_RC4(String data, String key)
	{
		if (data == null || key == null)
		{
			return null;
		}
		return new String(RC4Base(HexString2Bytes(data), key));
	}

	public static byte[] encry_RC4_byte(String data, String key , String encoding) throws Exception
	{
		if (data == null || key == null)
		{
			return null;
		}
		byte b_data[] = data.getBytes(encoding);
		return RC4Base(b_data, key);
	}

	public static String encry_RC4_string(String data, String key ,String encoding)throws Exception
	{
		if (data == null || key == null)
		{
			return null;
		}
		return toHexString(asString(encry_RC4_byte(data, key , encoding)));
	}

	public static String asString(byte[] buf)
	{
		StringBuffer strbuf = new StringBuffer(buf.length);
		for (int i = 0; i < buf.length; i++)
		{
			strbuf.append((char) buf[i]);
			//System.out.println(byte2bits(buf[i]));
		}
		return strbuf.toString();
	}

	public static String asStringInt(byte[] buf)
	{
		StringBuffer strbuf = new StringBuffer(buf.length);
		for (int i = 0; i < buf.length; i++)
		{
			strbuf.append(buf[i] & 0xff);
			System.out.println(byte2bits(buf[i]));
		}
		return strbuf.toString();
	}

	public static String byte2bits(byte b)
	{
		int z = b;
		z |= 256;
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}

	private static byte[] initKey(String aKey)
	{
		byte[] b_key = aKey.getBytes();
		byte state[] = new byte[256];

		for (int i = 0; i < 256; i++)
		{
			state[i] = (byte) i;
		}
		int index1 = 0;
		int index2 = 0;
		if (b_key == null || b_key.length == 0)
		{
			return null;
		}
		for (int i = 0; i < 256; i++)
		{
			index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
			byte tmp = state[i];
			state[i] = state[index2];
			state[index2] = tmp;
			index1 = (index1 + 1) % b_key.length;
		}
		return state;
	}

	private static byte[] initKey(byte[] b_key)
	{

		byte state[] = new byte[256];

		for (int i = 0; i < 256; i++)
		{
			state[i] = (byte) i;
		}
		int index1 = 0;
		int index2 = 0;
		if (b_key == null || b_key.length == 0)
		{
			return null;
		}
		for (int i = 0; i < 256; i++)
		{
			index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
			byte tmp = state[i];
			state[i] = state[index2];
			state[index2] = tmp;
			index1 = (index1 + 1) % b_key.length;
		}
		return state;
	}

	public static String toHexString(String s)
	{
		String str = "";
		for (int i = 0; i < s.length(); i++)
		{
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch & 0xFF);
			if (s4.length() == 1)
			{
				s4 = '0' + s4;
			}
			str = str + s4;
		}
		return str;// 0x表示十六进制
	}

	public static byte[] HexString2Bytes(String src)
	{
		int size = src.length();
		byte[] ret = new byte[size / 2];
		byte[] tmp = src.getBytes();
		for (int i = 0; i < size / 2; i++)
		{
			ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
		}
		return ret;
	}

	private static byte uniteBytes(byte src0, byte src1)
	{
		char _b0 = (char) Byte.decode("0x" + new String(new byte[]
		{ src0 })).byteValue();
		_b0 = (char) (_b0 << 4);
		char _b1 = (char) Byte.decode("0x" + new String(new byte[]
		{ src1 })).byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static byte[] RC4Base(byte[] input, String mKkey)
	{
		int x = 0;
		int y = 0;
		byte key[] = initKey(mKkey);
		int xorIndex;
		byte[] result = new byte[input.length];

		for (int i = 0; i < input.length; i++)
		{
			x = (x + 1) & 0xff;
			y = ((key[x] & 0xff) + y) & 0xff;
			byte tmp = key[x];
			key[x] = key[y];
			key[y] = tmp;
			xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
			result[i] = (byte) (input[i] ^ key[xorIndex]);
		}
		return result;
	}

	public static byte[] RC4Base(byte[] input, byte[] keyC)
	{
		int x = 0;
		int y = 0;
		byte key[] = initKey(keyC);
		int xorIndex;
		byte[] result = new byte[input.length];

		for (int i = 0; i < input.length; i++)
		{
			x = (x + 1) & 0xff;
			y = ((key[x] & 0xff) + y) & 0xff;
			byte tmp = key[x];
			key[x] = key[y];
			key[y] = tmp;
			xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
			result[i] = (byte) (input[i] ^ key[xorIndex]);
		}
		return result;
	}

	public static void main(String[] args) throws Exception
	{
		String key = "abcdefg123456789";
		String input = "helloworld";

		String x = RC4New.encry_RC4_string(input, key,"UTF-8");

		String y = RC4New.decry_RC4(x, key);
		System.out.println(x);//RC4密文
		System.out.println(y);//RC4密文解密

		String t = asString(RC4New.RC4Base(input.getBytes(), key));
		System.out.println(t);
		System.out.println(RC4New.toHexString(t));
		System.out.println(new String(RC4New.RC4Base(input.getBytes(), key), "UTF-8"));

		System.out.println(asString(RC4New.RC4Base(Base16.decode("e85852461e1af12fb129"), key)));
		//e679d9080ba606a4b326d5e7ea2562d5
		System.out.println(MD5Util.MD5(asStringInt(RC4New.RC4Base(input.getBytes(), key)) + "cn/huoqiu"));

		byte[] xx = RC4New.RC4Base(input.getBytes(), key);
		byte[] tt = "cn/huoqiu".getBytes();
		int num = xx.length + tt.length;
		byte[] yy = new byte[num];
		int j = 0;
		for (int i = 0; i < num; i++)
		{
			if (i < xx.length)
			{
				yy[i] = xx[i];
			}
			else
			{
				yy[i] = tt[j++];
			}
		}

		System.out.println(MD5Util.MD5(yy));

		System.out.println(MD5Util.MD5("e85852461e1af12fb129huoqiu"));
		//System.out.println(MD5.GetMD5Code(new String(RC4New.RC4Base(input.getBytes(),key),"UTF-8")+"huoqiu"));

	}
}
