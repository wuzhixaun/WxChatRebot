FROM adoptopenjdk/openjdk11
MAINTAINER jackWu <627521884@qq.com>

RUN echo '本地使用mvn clean package'

ADD ./target/WxChatRebot.jar WxChatRebot.jar

ENTRYPOINT ["java","-jar","/WxChatRebot.jar","&"]