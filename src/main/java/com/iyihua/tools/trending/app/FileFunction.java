package com.iyihua.tools.trending.app;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileFunction {

	public static void main(String[] args) {
		List<String> lines = Arrays.asList("The first line", "The second line 中文");
		Path file = Paths.get("the-file-name.md");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
