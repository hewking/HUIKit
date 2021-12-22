package com.hewking.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Description:
 * @Author: jianhao
 * @Date: 2021-07-27 20:12
 * @License: Copyright Since 2020 Hive Box Technology. All rights reserved.
 * @Notice: This content is limited to the internal circulation of
 *  Hive Box, and it is prohibited to leak or used for other commercial purposes.
 */
public class AESEncryptUtil {

  /**
   * AES加密字符串
   *
   * @param content 需要被加密的字符串
   * @param password 加密需要的密码
   * @return 密文
   */
  public static byte[] encrypt(byte[] content, String password) {
    try {
      // 创建 AES 的 key 生产者
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      // 利用用户密码作为随机数初始化出128位的key生产者。SecureRandom 是生产安全随机数序列，password.getBytes()是种子
      // 只要种子相同，序列就一样，所以解密只要有password就行
      keyGenerator.init(128, new SecureRandom(password.getBytes()));
      // 根据用户密码生成一个密钥
      SecretKey secretKey = keyGenerator.generateKey();
      // 返回基本编码格式的密钥。如果此密钥不支持编码，则返回null
      byte[] enCodeFormat = secretKey.getEncoded();
      // 转换为 AES 专用密钥
      SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
      // 创建密码器
      Cipher cipher = Cipher.getInstance("AES");

      byte[] byteContent = content;
      // 初始化为加密模式的密码器
      cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
      // 加密
      byte[] result = cipher.doFinal(byteContent);

      return result;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 解密AES加密过的字符串
   *
   * @param content AES加密过过的内容
   * @param password 加密时的密码
   * @return 明文
   */
  public static byte[] decrypt(byte[] content, String password) {
    try {
      // 创建 AES 的 key 生产者
      KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
      // 利用用户密码作为随机数初始化出128位的key生产者。SecureRandom 是生产安全随机数序列，password.getBytes()是种子
      // 只要种子相同，序列就一样，所以解密只要有password就行
      keyGenerator.init(128, new SecureRandom(password.getBytes()));
      // 根据用户密码生成一个密钥
      SecretKey secretKey = keyGenerator.generateKey();
      // 返回基本编码格式的密钥。如果此密钥不支持编码，则返回null
      byte[] enCodeFormat = secretKey.getEncoded();
      // 转换为 AES 专用密钥
      SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
      // 创建密码器
      Cipher cipher = Cipher.getInstance("AES");

      // 初始化为解密模式的密码器
      cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
      // 加密
      byte[] result = cipher.doFinal(content);
      // 返回明文
      return result;
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (NoSuchPaddingException e) {
      e.printStackTrace();
    } catch (InvalidKeyException e) {
      e.printStackTrace();
    } catch (BadPaddingException e) {
      e.printStackTrace();
    } catch (IllegalBlockSizeException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) throws UnsupportedEncodingException {

    String content = "hello World!";
    String secretKey = "uzvOCn8WVjjkugcIJTM+RQ==";
    byte[] encodeStr = encrypt(content.getBytes("UTF-8"), secretKey);
    System.out.println("encodeStr = " + new String(encodeStr, "UTF-8"));
    byte[] decodeStr = decrypt(encodeStr, secretKey);
    System.out.println("decodeStr = " + new String(decodeStr, "UTF-8"));

  }

}
