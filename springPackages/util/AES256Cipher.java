package project.common.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AES256Cipher {
	public static byte[] ivBytes = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    public static String uniqkey = "abcdefghijklmnopqrstuvwxyz123456"; // 32bit
    public static String key = "qrjoy.com.paraencriptionsolution"; // gen key

    // 암호화
	public String AES_Encode(String str)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException,	IllegalBlockSizeException, BadPaddingException
	{
		byte[] textBytes = str.getBytes("UTF-8");
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
		Cipher cipher = null;
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		
		return Base64.encodeBase64String(cipher.doFinal(textBytes));
	}

	//복호화
	public String AES_Decode(String str)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		str = str.replace(" ", "+");
		// BASE64Decoder decoder = new BASE64Decoder();
		byte[] textBytes = null;
		try{
			textBytes = Base64.decodeBase64(str);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		 
		// Base64.decodeBase64(str);
		SecretKeySpec newKey = null;
		AlgorithmParameterSpec ivSpec = null;
		try {
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				ivSpec = new IvParameterSpec(ivBytes); 
				newKey = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
				cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
				return new String(cipher.doFinal(textBytes), "UTF-8");			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		finally{
			newKey = null; ivSpec = null;
		}
	}

	public String AES_uniq_Decode(String str)	throws java.io.UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException
	{
		str = str.replace(" ", "+");
		byte[] textBytes = null;
		try{
			textBytes = Base64.decodeBase64(str);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		
		SecretKeySpec newKey = null;
		AlgorithmParameterSpec ivSpec = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ivSpec = new IvParameterSpec(ivBytes); 
			newKey = new SecretKeySpec(uniqkey.getBytes("UTF-8"), "AES"); // uniq 전용키
			cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
			return new String(cipher.doFinal(textBytes), "UTF-8");			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
		finally{
			newKey = null; ivSpec = null;
		}
	}
}
