package com.iyihua.tools.trending.app;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.iyihua.tools.trending.entity.Repository;

/**
 * Jsoup网页抓取帮助类
 * 
 * @author Administrator
 *
 */
public class HttpJsoupHelper {

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpJsoupHelper.class);
	public static final String GITHUB_HOST = "https://github.com";

	public static void loadGitTrending() {
		try {

			Map<String, List<Repository>> keySet = new HashMap<String, List<Repository>>();
			keySet.put("Java", new ArrayList<Repository>());

			Document doc = Jsoup.connect("https://github.com/trending").get();
			Elements elements = doc.select(".repo-list > li");
			for (Element element : elements) {
				String title = "";
				String link = "";
				String descr = "";
				String lan = "";
				Elements lanElements = element.select("span[itemprop=programmingLanguage]");
				for (Element lanE : lanElements) {
					lan = lanE.text();
				}
				System.out.println(lan);

				if (!keySet.containsKey(lan)) {
					continue;
				}

				Elements titles = element.select(".d-inline-block > h3 > a");

				for (Element titleElement : titles) {
					title = titleElement.text();
					link = titleElement.attr("href");
				}
				System.err.println(title);
				System.err.println(link);

				Elements descrElements = element.select(".py-1 > p");
				for (Element descrElement : descrElements) {
					descr = descrElement.text();
				}
				System.out.println(descr);

				keySet.get(lan).add(Repository.builder().language(lan).title(title).link(GITHUB_HOST + link)
						.description(descr).build());

			}
			System.err.println("---------------------------------------");
			System.err.println("---------------------------------------");
			System.err.println("---------------------------------------");
			for (String key : keySet.keySet()) {
				System.err.println(key);
				List<Repository> vs = keySet.get(key);
				for (Repository repository : vs) {
					System.out.println(repository.toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Repository> loadGitTrending(String lan) throws SocketTimeoutException {
		List<Repository> list = Lists.newArrayList();
		try {
			Document doc = Jsoup.connect("https://github.com/trending/" + lan).get();
			Elements elements = doc.select(".repo-list > li");
			for (Element element : elements) {
				String title = "";
				String link = "";
				String descr = "";
				Elements titles = element.select(".d-inline-block > h3 > a");
				for (Element titleElement : titles) {
					title = titleElement.text();
					link = titleElement.attr("href");
				}
				LOGGER.info("[title]"+title);
				LOGGER.info("[link]"+link);

				Elements descrElements = element.select(".py-1 > p");
				for (Element descrElement : descrElements) {
					descr = descrElement.text();
				}
				LOGGER.info("[description]"+descr);
				list.add(Repository.builder().language(lan).title(title).link(GITHUB_HOST + link).description(descr)
						.build());

			}
			System.err.println("---------------------------------------");
			for (Repository repository : list) {
				LOGGER.info(repository.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) {
		try {
			loadGitTrending("java");
		} catch (SocketTimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
