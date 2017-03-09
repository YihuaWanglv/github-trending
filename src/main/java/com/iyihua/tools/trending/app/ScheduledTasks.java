package com.iyihua.tools.trending.app;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    public final static long ONE_Minute =  60 * 1000;

//    @Scheduled(cron="0 15 12 * * ?")
    @Scheduled(fixedDelay=ONE_Minute)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        log.info("now the task begin..............................");
        try {
			Document doc = Jsoup.connect("https://github.com/trending").get();
			Elements elements = doc.select(".repo-list");
			for (Element element : elements) {
				System.err.println(element.text());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
