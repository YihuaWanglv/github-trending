package com.iyihua.tools.trending.app;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.iyihua.tools.trending.core.FileHelper;
import com.iyihua.tools.trending.core.HttpJsoupHelper;
import com.iyihua.tools.trending.core.StaticConfig;
import com.iyihua.tools.trending.entity.Repository;

@Component
public class TaskOschinaExplore {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskOschinaExplore.class);

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");
	public final static long ONE_MINUTE = 60 * 1000;
	public final static long ONE_DAY = 1000 * 60 * 60 * 24;

	@Value("${config.register.module.oschina}")
	private String CONFIG_REGISTER_MODULE;

	@Scheduled(fixedRate = ONE_DAY)
	public void reportCurrentTime() {
		LOGGER.info("The time is now {}", DATE_FORMAT.format(new Date()));
		LOGGER.info("now the task begin..............................");

		String[] modules = CONFIG_REGISTER_MODULE.split(",");
		Map<String, List<Repository>> maps = Maps.newHashMap();
		for (String module : modules) {
			List<Repository> repositorys = Lists.newArrayList();
			try {
				repositorys = HttpJsoupHelper.loadOschinaExplore(module);
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
				LOGGER.error("loading repository " + module + "failure: {}", e);
				try {
					repositorys = HttpJsoupHelper.loadOschinaExplore(module);
				} catch (SocketTimeoutException e1) {
					e1.printStackTrace();
					LOGGER.error("loading repository " + module + "failure again! pass continue... : {}", e);
				}
			}
			maps.put(module, repositorys);
		}

		StringBuilder sb = new StringBuilder();
		for (String key : maps.keySet()) {
			LOGGER.info("begin to get content of : " + key);
			sb.append("\n\n\n## ").append(key).append("\n");
			List<Repository> vs = maps.get(key);
			if (null == vs)
				continue;
			for (Repository repository : vs) {
				LOGGER.info(repository.toString());
				sb.append("\n- ").append("[").append(repository.getTitle()).append("](").append(repository.getLink())
						.append(") : ").append(repository.getDescription());
			}
		}

		FileHelper.writeModule(StaticConfig.Repository.OSCHINA, sb.toString());

//		GitHelper.proccess();
	}

}
