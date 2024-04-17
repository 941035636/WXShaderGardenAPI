package com.jt.www.util.yangguang.util;


import org.apache.commons.codec.digest.DigestUtils;

import javax.crypto.Cipher;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @author yinshaobo at 2021/5/19 10:17
 */
public class SecurityUtils {

    private static final String SIGN_TYPE_RSA = "RSA";
    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";
    private static final int MAX_ENCRYPT_BLOCK_1024 = 117;
    private static final int MAX_DECRYPT_BLOCK_1024 = 128;
    private static final int MAX_ENCRYPT_BLOCK_2048 = 245;
    private static final int MAX_DECRYPT_BLOCK_2048 = 256;
    private static final int MAX_ENCRYPT_LIMIT = 300;
    private static final int MAX_DECRYPT_LIMIT = 1000;

    /**
     * 生成RSA密钥对
     * 渠道方可使用该方法自主生成密钥对，将公钥线下提供给渠道管理方
     * index: 0-公钥 1-私钥
     */
    public static String[] getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        return new String[]{publicKeyString, privateKeyString};
    }

    /**
     * 使用RSA公钥对业务数据加密
     *
     * @param content   业务数据json
     * @param publicKey RSA公钥
     * @return 加密后的字符串
     * @throws Exception e
     */
    public static String rsaEncrypt(String content, String publicKey) throws Exception {
        PublicKey pubKey = getPublicKeyFromX509(SIGN_TYPE_RSA, new ByteArrayInputStream(publicKey.getBytes()));
        Cipher cipher = Cipher.getInstance(SIGN_TYPE_RSA);
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] data = content.getBytes(StandardCharsets.UTF_8);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;

        int maxEncryptBlock = MAX_ENCRYPT_BLOCK_1024;

        if (publicKey.length() > MAX_ENCRYPT_LIMIT) {
            maxEncryptBlock = MAX_ENCRYPT_BLOCK_2048;
        }
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxEncryptBlock) {
                cache = cipher.doFinal(data, offSet, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxEncryptBlock;
        }
        byte[] encryptedData = Base64.getEncoder().encode(out.toByteArray());
        out.close();
        return new String(encryptedData, StandardCharsets.UTF_8);
    }

    /**
     * RSA解密
     * @param content 密文
     * @param privateKey 私钥
     * @return 解密后的明文
     * @throws Exception e
     */
    public static String rsaDecrypt(String content, String privateKey) throws Exception {
        PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA, new ByteArrayInputStream(privateKey.getBytes()));
        Cipher cipher = Cipher.getInstance(SIGN_TYPE_RSA);
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] encryptedData = Base64.getDecoder().decode(content.getBytes(StandardCharsets.UTF_8));
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        int maxDecryptBlock = MAX_DECRYPT_BLOCK_1024;
        if (privateKey.length() > MAX_DECRYPT_LIMIT) {
            maxDecryptBlock = MAX_DECRYPT_BLOCK_2048;
        }
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > maxDecryptBlock) {
                cache = cipher.doFinal(encryptedData, offSet, maxDecryptBlock);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * maxDecryptBlock;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();

        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    /**
     * SHA1withRSA算法数据加签
     *
     * @param content    要签名的字符串
     * @param privateKey 私钥
     * @return 签名串
     * @throws Exception e
     */
    public static String rsaSign(String content, String privateKey) throws Exception {
        PrivateKey priKey = getPrivateKeyFromPKCS8(SIGN_TYPE_RSA,
                new ByteArrayInputStream(privateKey.getBytes(StandardCharsets.UTF_8)));
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initSign(priKey);
        signature.update(content.getBytes(StandardCharsets.UTF_8));
        byte[] signed = signature.sign();
        return new String(Base64.getEncoder().encode(signed));
    }

    /**
     * SHA1withRSA算法数据验签
     * @param content 待验签字符串
     * @param sign 签文
     * @param publicKey 公钥
     * @return result
     * @throws Exception e
     */
    public static boolean rsaCheckSign(String content, String sign, String publicKey) throws Exception {
        PublicKey pubKey = getPublicKeyFromX509(SIGN_TYPE_RSA, new ByteArrayInputStream(publicKey.getBytes()));
        Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
        signature.initVerify(pubKey);
        signature.update(content.getBytes(StandardCharsets.UTF_8));
        return signature.verify(Base64.getDecoder().decode(sign.getBytes()));
    }

    //***********************************下面时MD5方式加签验签**********************************************************

    /**
     * MD5加签
     * @param content 待加签字符串
     * @return 签文
     * @throws Exception e
     */
    public static String md5Sign(String content) throws Exception {
        return DigestUtils.md5Hex(content.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * MD5验签
     * @param content 待验签字符串
     * @param sign 签文
     * @return result
     * @throws Exception e
     */
    public static boolean md5CheckSign(String content, String sign) throws Exception {
        String s = md5Sign(content);
        return sign.equals(s);
    }

    private static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtils.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.getDecoder().decode(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins == null || algorithm == null) {
            return null;
        }
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        byte[] encodedKey = StreamUtils.readText(ins).getBytes();
        encodedKey = Base64.getDecoder().decode(encodedKey);
        return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
    }
}
