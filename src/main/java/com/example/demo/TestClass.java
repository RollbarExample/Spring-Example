package com.example.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestClass {

	private static final Logger logger = LoggerFactory.getLogger(TestClass.class);
	/*
	 * static { RuntimeMXBean rt = ManagementFactory.getRuntimeMXBean(); String pid
	 * = rt.getName(); MDC.put("PID", pid); }
	 */

	public static void main(String[] args) {
		// logger.info("Exiting main");

		logger.debug("This is debug");
		logger.debug("This is debug");
		try {
			String test = null;
			test.length();
		} catch (Exception e) {
			e.printStackTrace();
		}
FileInputStream fis = null;
try {
	fis = new FileInputStream("B:/myfile.txt");
} catch (FileNotFoundException e) {
	e.printStackTrace();
}
	}
}
