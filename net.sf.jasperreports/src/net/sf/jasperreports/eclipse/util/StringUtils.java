/*******************************************************************************
 * Copyright (C) 2010 - 2016. TIBCO Software Inc. 
 * All Rights Reserved. Confidential & Proprietary.
 ******************************************************************************/
package net.sf.jasperreports.eclipse.util;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Set;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtils {

	private StringUtils() {
	}

	public static String replaceAllIns(String text, String regexp, String replacement) {
		return Pattern.compile(regexp, Pattern.CASE_INSENSITIVE).matcher(text).replaceAll(replacement);
	}

	// Constants
	public static final String EMPTY_STRING = "";
	public static final String SPACE = " ";
	public static final String TAB = "\t";
	public static final String SINGLE_QUOTE = "'";
	public static final String PERIOD = ".";
	public static final String DOUBLE_QUOTE = "\"";
	public static final String BACKSLASH = "\\";
	public static final String DOUBLE_BACKSLASH = "\\\\";

	public static String toPackageName(String str) {
		StringBuilder tmp = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char charAt = str.charAt(i);
			if (charAt == '-')
				charAt = '_';
			if (i == 0)
				if (Character.isJavaIdentifierStart(charAt)) {
					tmp.append(charAt);
					continue;
				} else
					tmp.append("_");
			if (Character.isJavaIdentifierPart(charAt))
				tmp.append(charAt);
		}
		if (tmp.length() == 0)
			return "com";
		return tmp.toString();
	}

	private static String[] encodings;

	public static String[] getEncodings() {
		if (encodings == null) {
			SortedMap<String, Charset> m = Charset.availableCharsets();
			Set<String> keySet = m.keySet();
			encodings = keySet.toArray(new String[keySet.size()]);
		}
		return encodings;
	}

	public static String xmlEncode(String text, String invalidCharReplacement) {
		if (text == null || text.length() == 0) {
			return text;
		}

		int length = text.length();
		StringBuffer ret = new StringBuffer();
		int last = 0;

		for (int i = 0; i < length; i++) {
			char c = text.charAt(i);
			if (Character.isISOControl(c) && c != '\t' && c != '\r' && c != '\n' && c != 0) {
				last = appendText(text, ret, i, last);
				if (invalidCharReplacement == null) {
					// the invalid character is preserved
					ret.append(c);
				} else if ("".equals(invalidCharReplacement)) {
					// the invalid character is removed
					continue;
				} else {
					// the invalid character is replaced
					ret.append(invalidCharReplacement);
				}
			}
		}
		appendText(text, ret, length, last);
		return ret.toString();
	}

	private static int appendText(String text, StringBuffer ret, int current, int old) {
		if (old < current) {
			ret.append(text.substring(old, current));
		}
		return current + 1;
	}

	/**
	 * Returns a float number instance represented by the input string.
	 * 
	 * @param value
	 *            the string to convert
	 * @return the float number instance, <code>null</code> if the number can not be
	 *         properly converted.
	 */
	public static Float safeParseAsFloat(String value) {
		try {
			if (!org.apache.commons.lang3.StringUtils.isEmpty(value)) {
				return Float.valueOf(value);
			}
		} catch (NumberFormatException ex) {
			return null;
		}
		return null;
	}

	public static String replaceLast(String input, String regex, String replacement) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (!matcher.find())
			return input;
		int lastMatchStart = 0;
		do {
			lastMatchStart = matcher.start();
		} while (matcher.find());
		matcher.find(lastMatchStart);
		StringBuffer sb = new StringBuffer(input.length());
		matcher.appendReplacement(sb, replacement);
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public static String getTrailingZeros(String value, char decimalSeparator ) {
		int decimalSeparatorIndex = value.indexOf(decimalSeparator);
		if (decimalSeparatorIndex != -1 && value.endsWith("0")) {
			String decimalPart = value.substring(decimalSeparatorIndex + 1); 
			int trailingZeros = 0;
			boolean appendSeparator = true;
			for(char c : decimalPart.toCharArray()) {
				if (c == '0') {
					trailingZeros++;
				} else {
					trailingZeros = 0;
					appendSeparator = false;
				}
			}
			return (appendSeparator ? decimalSeparator : "") + org.apache.commons.lang3.StringUtils.repeat("0", trailingZeros);
		}
		return "";
	}

	/**
	 * Replaces any single backslash character with a double backslash.<br/>
	 * It can be useful when performing simple path conversions (i.e. Windows
	 * systems).
	 * 
	 * @param str
	 *            the input string
	 * @return the modified string
	 */
	public static String replaceBackslashWithDoubleBackslash(String str) {
		if (str != null) {
			return str.replace(BACKSLASH, DOUBLE_BACKSLASH);
		} else {
			return null;
		}
	}

	/**
	 * Tries to apply Base64 decode to the specified string.</br>
	 * If something goes wrong it returns the original string.
	 * 
	 * @param txt
	 *            the string to decode
	 * @return the decoded string if possible, the original one otherwise
	 */
	public static String safeDecode64(String txt) {
		try {
			return new String(Base64.getDecoder().decode(txt));
		} catch (Exception e) {
			return txt;
		}
	}

	/**
	 * Checks if a string is either <code>null</code> or empty.
	 * 
	 * @param s
	 *            the string to check
	 * @return <code>true</code> if the string is null/empty, <code>false</code>
	 *         otherwise
	 */
	public static boolean isNullOrEmpty(String s) {
		return s == null || s.isEmpty();
	}
}
