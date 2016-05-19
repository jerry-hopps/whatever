package net.nemo.whatever.util;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public abstract class DESCoder{  
    
    public static final String ALGORITHM = "DES";  
  
    /** 
     * BASE64解密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static byte[] decryptBASE64(String key) throws Exception {  
        return (new BASE64Decoder()).decodeBuffer(key);  
    }  
      
    /** 
     * BASE64加密 
     *  
     * @param key 
     * @return 
     * @throws Exception 
     */  
    public static String encryptBASE64(byte[] key) throws Exception {  
        return (new BASE64Encoder()).encodeBuffer(key);  
    }  
      
    private static Key toKey(byte[] key) throws Exception {  
        DESKeySpec dks = new DESKeySpec(key);  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
        SecretKey secretKey = keyFactory.generateSecret(dks);  
  
        // 当使用其他对称加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码  
        // SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);  
  
        return secretKey;  
    }  
  
      
    public static byte[] decrypt(byte[] data, String key) throws Exception {  
        Key k = toKey(decryptBASE64(key));  
  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        cipher.init(Cipher.DECRYPT_MODE, k);  
  
        return cipher.doFinal(data);  
    }  
  
      
    public static byte[] encrypt(byte[] data, String key) throws Exception {  
        Key k = toKey(decryptBASE64(key));  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        cipher.init(Cipher.ENCRYPT_MODE, k);  
  
        return cipher.doFinal(data);  
    }  
  
      
    public static String initKey() throws Exception {  
        return initKey(null);  
    }  
  
      
    public static String initKey(String seed) throws Exception {  
        SecureRandom secureRandom = null;  
  
        if (seed != null) {  
            secureRandom = new SecureRandom(decryptBASE64(seed));  
        } else {  
            secureRandom = new SecureRandom();  
        }  
  
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);  
        kg.init(secureRandom);  
  
        SecretKey secretKey = kg.generateKey();  
  
        return encryptBASE64(secretKey.getEncoded());  
    }  
    
    public static void main(String[] args) throws Exception {
    	String inputStr = "tuo.shi@oracle.com";  
        String key = "f1s7XT6zdac=";
        System.err.println("原文:\t" + inputStr);  
        System.err.println("密钥:\t" + key);  
  
        byte[] inputData = inputStr.getBytes();  
        inputData = DESCoder.encrypt(inputData, key);  
  
        String encryptedStr = DESCoder.encryptBASE64(inputData);
        
        System.err.println("加密后:\t" + encryptedStr);
  
        byte[] outputData = DESCoder.decrypt(inputData, key);  
        String outputStr = new String(outputData);  
  
        System.err.println("解密后:\t" + outputStr);   
	}
}  
