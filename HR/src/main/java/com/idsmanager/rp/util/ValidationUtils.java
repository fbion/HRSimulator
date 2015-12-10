package com.idsmanager.rp.util;

import com.sun.org.apache.regexp.internal.RE;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class ValidationUtils {
	public final static String reg = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|"
			+ "(\\b(select|update|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";

	public final static Pattern sqlPattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

	/**
	 * 必须字段 thinkpad dushaofei
	 * 
	 * @param value
	 * @return
	 */
	public static boolean validateRequired(String value) {
		boolean isFieldValid = false;
		if (value != null && value.trim().length() > 0) {
			isFieldValid = true;
		}
		return isFieldValid;
	}

	/**
	 * 验证正则表达式 thinkpad dushaofei
	 * 
	 * @param value
	 * @param expression
	 * @return
	 */
	public static boolean matchPattern(String value, String expression) {
		boolean match = false;
		if (validateRequired(expression)) {
			RE r = new RE(expression);
			match = r.match(value);
		}
		return match;
	}

	/**
	 * 验证Integer类型 thinkpad dushaofei
	 * 
	 * @param value
	 * @return
	 */
	public static boolean validateInt(String value) {
		boolean isFieldValid = false;
		try {
			Integer.parseInt(value);
			isFieldValid = true;
		} catch (Exception e) {
			isFieldValid = false;
		}
		return isFieldValid;
	}

	/**
	 * 验证字段长度 thinkpad dushaofei
	 * 
	 * @param value
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static boolean validateLength(String value, int minLength,
			int maxLength) {
		String validatedValue = value;
		if (!validateRequired(value)) {
			validatedValue = "";
		}
		return (validatedValue.length() >= minLength && validatedValue.length() <= maxLength);
	}

	/**
	 * 过滤用户输入要保护应用程序免遭跨站点脚本编制的攻击 
	 * 通过将敏感字符转换其对应的字符实体来清理 HTML 
	 * thinkpad dushaofei
	 * 
	 * @param value
	 * @return
	 */
	public static String filterHtml(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer result = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); ++i) {
			switch (value.charAt(i)) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			case '%':
				result.append("&#37;");
				break;
			case ';':
				result.append("&#59;");
				break;
			case '(':
				result.append("&#40;");
				break;
			case ')':
				result.append("&#41;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '+':
				result.append("&#43;");
				break;
			default:
				result.append(value.charAt(i));
				break;
			}
		}
		return result.toString();
	}
	/**
	 * 过滤用户输入要保护应用程序免遭跨站点脚本编制的攻击 
	 * 如果有就返回False
	 * thinkpad dushaofei
	 * 
	 * @param value
	 * @return
	 */
	public static boolean filterHtmlToIllegal(String value) {
		if (value == null) {
			return false;
		}
		boolean when = true;
		StringBuffer result = new StringBuffer(value.length());
		for (int i = 0; i < value.length(); ++i) {
			switch (value.charAt(i)) {
			case '<':
			case '>':
				when = false;
				return when;
			case '"':
				when = false;
				return when;
			case '\'':
				when = false;
				return when;
			case '%':
				when = false;
				return when;
			case ';':
				when = false;
				return when;
			case '(':
				when = false;
				return when;
			case ')':
				when = false;
				return when;
			case '&':
				when = false;
				return when;
			case '+':
				when = false;
				return when;
			default:
				result.append(value.charAt(i));
				break;
			}
		}
		return when;
	}
	/**
	 * 验证Cookie thinkpad dushaofei
	 * 
	 * @param request
	 * @return
	 */
	public static boolean validateCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		boolean when = false;
		if (cookies != null) {
			// find the "user" cookie
			for (int i = 0; i < cookies.length; ++i) {
				if (cookies[i].getName().equals("user")) {
					// validate the cookie value
					if (validateRequired(cookies[i].getValue())) {
						when = true;
					}
				}
			}
		}
		return when;
	}
}
