package com.dev.frontend.services;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

public class Utils {

	private static final String CUSTOMER_NAME_TEMPLATE = "({0}){1}";

	public static String formartCustomerName(Long id, String name) {
		return MessageFormat.format(CUSTOMER_NAME_TEMPLATE, id, name);
	}

	public static Double parseDouble(String value) {
		if (value == null || value.isEmpty()) {
			return 0D;
		}
		try {
			return Double.parseDouble(value);
		} catch (final Exception e) {
			return 0D;
		}
	}

	public static Integer parseInteger(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		try {
			return Integer.parseInt(value);
		} catch (final Exception e) {
			return null;
		}
	}

	public static Long parseLong(String value) {
		if (value == null || value.isEmpty()) {
			return null;
		}
		try {
			return Long.parseLong(value);
		} catch (final Exception e) {
			return null;
		}
	}

	public static String numberToString(Number value) {
		if (value == null) {
			return StringUtils.EMPTY;
		}

		return String.valueOf(value);
	}
}