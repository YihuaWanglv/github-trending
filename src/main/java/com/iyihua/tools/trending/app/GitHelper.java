package com.iyihua.tools.trending.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * git命令帮助类
 * 
 * @author Administrator
 *
 */
public class GitHelper {

	public static void proccess() {
		Process proc;
		BufferedReader br;
		String s;
		try {
			proc = Runtime.getRuntime().exec("./rungit.sh");
			br = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((s = br.readLine()) != null)
				System.out.println("line: " + s);
			proc.waitFor();
			System.out.println("exit: " + proc.exitValue());
			proc.destroy();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
