package com.iyihua.tools.trending.core;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件写入帮助类
 * 
 * @author Administrator
 *
 */
public class FileHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileHelper.class);

	private static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat DATE_FORMAT_YEAR = new SimpleDateFormat("yyyy");

	public static void main(String[] args) {

		write("- 你是谁.");
	}

	public static void writeModule(String repository, String content) {

		content = "\n" + content;
		Date time = new Date();
		String date = DATE_FORMAT_DATE.format(time);
		String year = DATE_FORMAT_YEAR.format(time);
		File repositoryFile = new File(repository);
		File y = new File(repository + File.separator + year);
		String filePath = repository + File.separator + year + File.separator + date + ".md";
		try {
			if (!repositoryFile.exists()) {
				LOGGER.info("path[" + repository + "] not exist. then create dir");
				repositoryFile.mkdirs();
			}
			if (!y.exists()) {
				LOGGER.info("path[" + year + "] not exist. then create dir");
				y.mkdirs();
			}
			writeContent(filePath, content);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Write error!", e);
		}

	}

	public static void write(String content) {

		content = "\n" + content;
		Date time = new Date();
		String date = DATE_FORMAT_DATE.format(time);
		String year = DATE_FORMAT_YEAR.format(time);
		File y = new File(year);
		String filePath = year + File.separator + date + ".md";
		try {
			if (!y.exists()) {
				LOGGER.info("path[" + year + "] not exist. then create dir");
				y.mkdirs();
			}
			writeContent(filePath, content);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error("Write error!", e);
		}

	}

	private static void writeContent(String filePath, String content) throws IOException {
		LOGGER.info("begin to write...");
		File f = new File(filePath);
		Path file = Paths.get(filePath);
		if (!f.exists()) {
			LOGGER.info("file[" + filePath + "] not exist. now create.");
			Files.write(file, ("# " + filePath).getBytes());
		}
		Files.write(file, content.getBytes(), StandardOpenOption.APPEND);
		LOGGER.info("[" + filePath + "] content writed.");
	}

}
