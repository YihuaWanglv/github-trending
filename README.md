# github-trending

github-trending is a java applicaion about catching snapshot of github-trending page https://github.com/trending


## what is this?

github-trending is a java application, will run a daily task, read github-trending page, save to the md file group by date. And finally push commit to the github repository.

- tips: For some reason, I build this application by spring boot. However, the more simple way to do this job is using liunx crontab to run a daily shell job, and juse run a simple java application.

## how to use?

### @Before
- you need a github account.
- you need a running environment.

### create your github ssh key of your running environment, for example:a linux server. and save the ssh key to github setting page. 

### git clone project
```
git clone git@github.com:YihuaWanglv/github-trending.git
```

### maven package
```
cd github-trending
mvn clean package
```

### move github-trending.jar to project root path

### nohu run github-trending.jar
```
nohup java -jar java-github-trending.jar < /dev/null > /data/logs/java-github-trending.log 2>&1 &
```

## how it works?

- using spring @Scheduled as daily task.
- using Jsoup lib to get and parse page content
- use java.io lib to create markdown file and append content to md file.
- use java Runtime class to run command to commit and push to github.


## also see python version here.
- [分享一个自己写的github-trending小工具](http://www.jianshu.com/p/25722080c73d?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io)

