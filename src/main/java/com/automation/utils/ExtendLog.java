package com.automation.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ExtendLog {
	private static final Log LOGGER = LogFactory.getLog(ExtendLog.class);

	private static enum Type {
		CHECK, ACTION, DEBUG, INFO
	};

	public static void check(String content) {
		log(Type.CHECK, content);
	}

	public static void action(String content) {
		log(Type.ACTION, content);
	}

	public static void debug(String content) {
		log(Type.DEBUG, content);
	}

	public static void info(String content) {
		log(Type.INFO, content);
	}

	private static void log(Type type, String content) {
		LOGGER.info("[" + type.toString() + "] " + content);
	}

}
