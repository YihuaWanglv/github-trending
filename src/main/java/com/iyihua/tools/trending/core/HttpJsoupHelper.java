package com.iyihua.tools.trending.core;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

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

	public static List<Repository> loadOschinaExplore(String module) throws SocketTimeoutException {
		List<Repository> list = Lists.newArrayList();
		try {
			Document doc = Jsoup.connect(StaticConfig.Host.OSCHINA + "/explore/" + module).get();
			Elements elements = doc.select("#git-discover-list > .item");
			for (Element element : elements) {
				String title = "";
				String link = "";
				String descr = "";
				String lan = "";
				Elements contents = element.select(".content");
				for (Element content : contents) {
					Elements titles = content.select(".project-title > a");
					if (titles != null && titles.size() > 0) {
						Element titleElement = titles.get(0);
						title = titleElement.text();
						link = titleElement.attr("href");
					}
					Elements langs = content.select(".lang-label > a");
					if (langs != null && langs.size() > 0) {
						Element langElement = langs.get(0);
						lan = langElement.text();
					}
					Elements descs = content.select(".project-desc");
					if (descs != null && descs.size() > 0) {
						Element descElement = descs.get(0);
						descr = descElement.text();
					}
				}
				LOGGER.info("[title]" + title);
				LOGGER.info("[link]" + link);
				LOGGER.info("[description]" + descr);
				LOGGER.info("[lang]" + lan);
				list.add(Repository.builder().language(lan).title(title).link(StaticConfig.Host.OSCHINA + link)
						.description(descr).module(module).repository(module).build());

			}
			System.err.println("---------------------------------------");
			for (Repository repository : list) {
				LOGGER.info(repository.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static List<Repository> loadGitTrending(String lan) throws SocketTimeoutException {
		List<Repository> list = Lists.newArrayList();
		try {
			Document doc = Jsoup.connect(StaticConfig.Host.GITHUB + "/trending/" + lan).get();
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
				LOGGER.info("[title]" + title);
				LOGGER.info("[link]" + link);

				Elements descrElements = element.select(".py-1 > p");
				for (Element descrElement : descrElements) {
					descr = descrElement.text();
				}
				LOGGER.info("[description]" + descr);
				list.add(Repository.builder().language(lan).title(title).link(StaticConfig.Host.GITHUB + link)
						.description(descr).module(lan).build());

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
