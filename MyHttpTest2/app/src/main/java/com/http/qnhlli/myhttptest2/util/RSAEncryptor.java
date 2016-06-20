package com.http.qnhlli.myhttptest2.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAEncryptor {

	private static final String PUBLIC_KEY_FILE = "rsa_public_key.pem";// 公钥文件地址
	private static final String PRIVATE_KEY_FILE = "pkcs8_private_key.pem"; // 私钥
																			// 文件地址
	private RSAPrivateKey privateKey;// 私钥
	private RSAPublicKey publicKey;// 公钥

	private static RSAEncryptor instance = null;

	private RSAEncryptor() {
		

	}

	// 静态工厂方法
	public static RSAEncryptor getInstance(Context c) {
		if (instance == null) {
			try {
				instance = new RSAEncryptor(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public RSAEncryptor(Context c) throws Exception {
		String pubKey = getKeyFromFile(c, PUBLIC_KEY_FILE);
		String priKey = getKeyFromFile(c, PRIVATE_KEY_FILE);
		System.out.println("public key====>>"+pubKey);
		System.out.println("private key====>>"+priKey);
		loadPublicKey(pubKey);
		loadPrivateKey(priKey);
	}

	private String getKeyFromFile(Context c, String fileName) {
		InputStreamReader data;
		StringBuffer result = new StringBuffer("");
		try {
//			data = new InputStreamReader(c.getResources().getAssets().open(fileName));
			data=new InputStreamReader(c.getClass().getClassLoader().getResourceAsStream("assets/"+fileName));
			BufferedReader br = new BufferedReader(data);
			String line = "";
			while ((line = br.readLine()) != null) {
				result.append(line).append("\r");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(fileName + "=========key=======>> " + result.toString());
		return result.toString();
	}

	/**
	 * RAS 加密
	 * 
	 * @param plainText
	 *            明文
	 * @return
	 */
	public String encrypt(String plainText) {
		try {
			return instance.encryptWithBase64(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * RSA 解密
	 * 
	 * @param cipherText
	 *            密文
	 * @return
	 */
	public String decrypt(String cipherText) {
		try {
			return instance.decryptWithBase64(cipherText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取私钥
	 * 
	 * @return 当前的私钥对象
	 */
	public RSAPrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * 获取公钥
	 * 
	 * @return 当前的公钥对象
	 */
	public RSAPublicKey getPublicKey() {
		return publicKey;
	}

	/**
	 * 随机生成密钥对 ，生产环境上不使用。
	 */
	public void genKeyPair() {
		KeyPairGenerator keyPairGen = null;
		try {
			keyPairGen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		keyPairGen.initialize(1024, new SecureRandom());
		KeyPair keyPair = keyPairGen.generateKeyPair();
		this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
		this.publicKey = (RSAPublicKey) keyPair.getPublic();
	}

	public String getKeyFromFile(String filePath) throws Exception {
		String key = null;
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(filePath));
			String line = null;
			List<String> list = new ArrayList<String>();
			while ((line = bufferedReader.readLine()) != null) {
				list.add(line);
			}
			// remove the firt line and last line
			StringBuilder stringBuilder = new StringBuilder();
			for (int i = 1; i < list.size() - 1; i++) {
				stringBuilder.append(list.get(i)).append("\r");
			}
			key = stringBuilder.toString();
		} catch (Exception e) {
			throw new Exception("公钥数据流读取错误");
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}
		return key;
	}

	public String decryptWithBase64(String base64String) throws Exception {
		byte[] binaryData = decrypt(getPrivateKey(), Base64.decode(base64String));
		String string = new String(binaryData);
		return string;
	}

	public String encryptWithBase64(String string) throws Exception {
		byte[] binaryData = encrypt(getPublicKey(), string.getBytes());
		String base64String = Base64.encode(binaryData);
		return base64String;
	}

	/**
	 * 从文件中输入流中加载公钥
	 * 
	 * @param in
	 *            公钥输入流
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPublicKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("公钥数据流读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥输入流为空");
		}
	}

	/**
	 * 从字符串中加载公钥
	 * 
	 * @param publicKeyStr
	 *            公钥数据字符串
	 * @throws Exception
	 *             加载公钥时产生的异常
	 */
	public void loadPublicKey(String publicKeyStr) throws Exception {
		try {
			// BASE64Decoder base64Decoder = new BASE64Decoder();
			byte[] buffer = Base64.decode((publicKeyStr));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
			this.publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			throw new Exception("公钥非法");
			// } catch (IOException e) {
			// throw new Exception("公钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("公钥数据为空");
		}
	}

	/**
	 * 从文件中加载私钥
	 * 
	 * @param keyFileName
	 *            私钥文件名
	 * @return 是否成功
	 * @throws Exception
	 */
	public void loadPrivateKey(InputStream in) throws Exception {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String readLine = null;
			StringBuilder sb = new StringBuilder();
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}
			loadPrivateKey(sb.toString());
		} catch (IOException e) {
			throw new Exception("私钥数据读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥输入流为空");
		}
	}

	public void loadPrivateKey(String privateKeyStr) throws Exception {
		try {
			// BASE64Decoder base64Decoder = new BASE64Decoder();
			// byte[] buffer = base64Decoder.decodeBuffer(privateKeyStr);
			byte[] buffer = Base64.decode(privateKeyStr);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			this.privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此算法");
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new Exception("私钥非法");
			// } catch (IOException e) {
			// throw new Exception("私钥数据内容读取错误");
		} catch (NullPointerException e) {
			throw new Exception("私钥数据为空");
		}
	}

	/**
	 * 加密过程
	 * 
	 * @param publicKey
	 *            公钥
	 * @param plainTextData
	 *            明文数据
	 * @return
	 * @throws Exception
	 *             加密过程中的异常信息
	 */
	public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception {
		if (publicKey == null) {
			throw new Exception("加密公钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// , new BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] output = cipher.doFinal(plainTextData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此加密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("加密公钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("明文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("明文数据已损坏");
		}
	}

	/**
	 * 解密过程
	 * 
	 * @param privateKey
	 *            私钥
	 * @param cipherData
	 *            密文数据
	 * @return 明文
	 * @throws Exception
	 *             解密过程中的异常信息
	 */
	public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception {
		if (privateKey == null) {
			throw new Exception("解密私钥为空, 请设置");
		}
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");// , new BouncyCastleProvider());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] output = cipher.doFinal(cipherData);
			return output;
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("无此解密算法");
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			return null;
		} catch (InvalidKeyException e) {
			throw new Exception("解密私钥非法,请检查");
		} catch (IllegalBlockSizeException e) {
			throw new Exception("密文长度非法");
		} catch (BadPaddingException e) {
			throw new Exception("密文数据已损坏");
		}
	}

//	 public static void main(String[] args) throws Exception {
//	 try {
//	 String plainText = "15626512035";
//	 String cipherText = RSAEncryptor.getInstance().encrypt(plainText);
//	 String result = RSAEncryptor.getInstance().decrypt(cipherText);
//	 System.out.println("plainText:" + plainText);
//	 System.out.println("cipherText:" + cipherText);
//	 System.out.println("result:" + result);
//	 } catch (Exception e) {
//	 e.printStackTrace();
//	 }
//	 }

}
