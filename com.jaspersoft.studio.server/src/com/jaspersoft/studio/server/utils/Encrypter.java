/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package com.jaspersoft.studio.server.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.CharEncoding;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONObject;

import net.sf.jasperreports.util.Base64Util;

/**
 * Class used to encrypt and decrypt strings
 *
 *
 * @author gtoffoli
 */
public class Encrypter {
	Cipher ecipher;
	Cipher dcipher;

	// 8-byte Salt
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32, (byte) 0x56, (byte) 0x35, (byte) 0xE3,
			(byte) 0x03 };

	// Iteration count
	int iterationCount = 19;

	public Encrypter(String passPhrase) {
		try {
			// KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), salt,
			// iterationCount);
			KeySpec keySpec = new DESKeySpec((passPhrase.getBytes()));
			SecretKey key = SecretKeyFactory.getInstance("DES").generateSecret(keySpec);

			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);

		} catch (InvalidKeySpecException | javax.crypto.NoSuchPaddingException | java.security.NoSuchAlgorithmException
				| java.security.InvalidKeyException e) {
			e.printStackTrace();
		}
	}

	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes(StandardCharsets.UTF_8);

			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			return encodeBase64(enc);
		} catch (javax.crypto.BadPaddingException | IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String decrypt(String str) {
		try {
			// Decode base64 to get bytes
			byte[] dec = decodeBase64(str);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, StandardCharsets.UTF_8);
		} catch (javax.crypto.BadPaddingException | IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String encodeBase64(byte[] bytes) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			Base64Util.encode(new ByteArrayInputStream(bytes), os);
			return os.toString();
		} catch (IOException ex) {
		}
		return null;
	}

	private byte[] decodeBase64(String s) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			Base64Util.decode(new ByteArrayInputStream(s.getBytes()), os);
			return os.toByteArray();
		} catch (IOException ex) {
		}
		return null;
	}

	private static String byteArrayToHexString(byte[] byteArr) {
		StringBuilder sb = new StringBuilder();
		for (byte b : byteArr) {
			sb.append(Character.forDigit((b & 0xF0) >> 4, 16));
			sb.append(Character.forDigit(b & 0x0F, 16));
		}
		return sb.toString();
	}

	private static PublicKey getPublicKey(String n, String e) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		int radix = 16;
		BigInteger modulus = new BigInteger(n, radix);
		BigInteger publicExponent = new BigInteger(e, radix);
		RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, publicExponent);
		return keyFactory.generatePublic(publicKeySpec);
	}

	public static String encryptRSA(String pass, String key) {
		try {
			JSONObject jo = new JSONObject(key);
			PublicKey k = getPublicKey(jo.getString("n"), jo.getString("e"));

			Cipher c = Cipher.getInstance("RSA/NONE/NoPadding", new BouncyCastleProvider());
			c.init(Cipher.ENCRYPT_MODE, k);
			pass = URLEncoder.encode(pass, CharEncoding.UTF_8);
			return byteArrayToHexString(c.doFinal(pass.getBytes()));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pass;

	}
}
