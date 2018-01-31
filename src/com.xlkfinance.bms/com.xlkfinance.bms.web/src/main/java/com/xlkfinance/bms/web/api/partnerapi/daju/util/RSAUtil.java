package com.xlkfinance.bms.web.api.partnerapi.daju.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtil
{
  public static final String KEY_ALGORITHM = "RSA";
  public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
  private static final String PUBLIC_KEY = "RSAPublicKey";
  private static final String PRIVATE_KEY = "RSAPrivateKey";
  private static final int MAX_ENCRYPT_BLOCK = 117;
  private static final int MAX_DECRYPT_BLOCK = 128;

  public static Map<String, Object> genKeyPair()
  {
    KeyPair keyPair = null;
    try {
      KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
      keyPairGen.initialize(1024);
      keyPair = keyPairGen.generateKeyPair();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    RSAPublicKey publicKey = (RSAPublicKey)keyPair.getPublic();
    RSAPrivateKey privateKey = (RSAPrivateKey)keyPair.getPrivate();
    Map map = new HashMap(2);
    map.put("RSAPublicKey", publicKey);
    map.put("RSAPrivateKey", privateKey);
    return map;
  }

  public static String sign(byte[] data, String privateKey)
    throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
  {
    byte[] keyBytes = Base64Util.base64ToByteArray(privateKey);
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
    Signature signature = Signature.getInstance("MD5withRSA");
    signature.initSign(privateK);
    signature.update(data);
    return Base64Util.byteArrayToBase64(signature.sign());
  }

  public static boolean verify(byte[] data, String publicKey, String sign)
    throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException
  {
    byte[] keyBytes = Base64Util.base64ToByteArray(publicKey);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    PublicKey publicK = keyFactory.generatePublic(keySpec);
    Signature signature = Signature.getInstance("MD5withRSA");
    signature.initVerify(publicK);
    signature.update(data);
    return signature.verify(Base64Util.base64ToByteArray(sign));
  }

  public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
    throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
  {
    byte[] keyBytes = Base64Util.base64ToByteArray(privateKey);
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    byte[] decryptedData = (byte[])null;
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(2, privateK);
    int inputLen = encryptedData.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;

    int i = 0;
    while (inputLen - offSet > 0)
    {
      byte[] cache;
      if (inputLen - offSet > 128)
        cache = cipher.doFinal(encryptedData, offSet, 128);
      else {
        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * 128;
    }
    decryptedData = out.toByteArray();
    out.close();
    return decryptedData;
  }

  public static String decryptByPrivateKey(String encryptedData, String privateKey)
  {
    String result = "";
    try {
      byte[] decryptData = decryptByPrivateKey(Base64Util.base64ToByteArray(encryptedData), privateKey);
      result = new String(decryptData, "UTF-8");
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
    throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
  {
    byte[] keyBytes = Base64Util.base64ToByteArray(publicKey);
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key publicK = keyFactory.generatePublic(x509KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(2, publicK);
    int inputLen = encryptedData.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;

    int i = 0;
    while (inputLen - offSet > 0)
    {
      byte[] cache;
      if (inputLen - offSet > 128)
        cache = cipher.doFinal(encryptedData, offSet, 128);
      else {
        cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * 128;
    }
    byte[] decryptedData = out.toByteArray();
    out.close();
    return decryptedData;
  }

  public static String decryptByPublicKey(String encryptedData, String publicKey) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
    byte[] decryptData = decryptByPublicKey(Base64Util.base64ToByteArray(encryptedData), publicKey);
    return new String(decryptData, "UTF-8");
  }

  public static byte[] encryptByPublicKey(byte[] data, String publicKey)
    throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
  {
    byte[] keyBytes = Base64Util.base64ToByteArray(publicKey);
    X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key publicK = keyFactory.generatePublic(x509KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(1, publicK);
    int inputLen = data.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;

    int i = 0;
    while (inputLen - offSet > 0)
    {
      byte[] cache;
      if (inputLen - offSet > 117)
        cache = cipher.doFinal(data, offSet, 117);
      else {
        cache = cipher.doFinal(data, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * 117;
    }
    byte[] encryptedData = out.toByteArray();
    out.close();
    return encryptedData;
  }

  public static String encryptByPublicKey(String data, String publicKey) throws Exception {
    try {
      byte[] encodeData = (byte[])null;
      encodeData = encryptByPublicKey(data.getBytes("UTF-8"), publicKey);
      return Base64Util.byteArrayToBase64(encodeData);
    } catch (Exception e) {
      throw e;
    }
  }

  public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
    throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException
  {
    byte[] keyBytes = Base64Util.base64ToByteArray(privateKey);
    PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
    Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
    cipher.init(1, privateK);
    int inputLen = data.length;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    int offSet = 0;

    int i = 0;
    while (inputLen - offSet > 0)
    {
      byte[] cache;
      if (inputLen - offSet > 117)
        cache = cipher.doFinal(data, offSet, 117);
      else {
        cache = cipher.doFinal(data, offSet, inputLen - offSet);
      }
      out.write(cache, 0, cache.length);
      i++;
      offSet = i * 117;
    }
    byte[] encryptedData = out.toByteArray();
    out.close();
    return encryptedData;
  }

  public static String encryptByPrivateKey(String data, String privateKey) {
    String result = "";
    try {
      byte[] encodeData = encryptByPrivateKey(data.getBytes("UTF-8"), privateKey);
      result = Base64Util.byteArrayToBase64(encodeData);
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (InvalidKeySpecException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  public static String getPrivateKey(Map<String, Object> keyMap)
  {
    Key key = (Key)keyMap.get("RSAPrivateKey");
    return Base64Util.byteArrayToBase64(key.getEncoded());
  }

  public static String getPublicKey(Map<String, Object> keyMap)
  {
    Key key = (Key)keyMap.get("RSAPublicKey");
    return Base64Util.byteArrayToBase64(key.getEncoded());
  }
}