FROM openjdk:8-jre-alpine
MAINTAINER Wang Chen Chen<932560435@qq.com>
ENV VERSION 1.0
# 컨테이너 볼륨 마운트
VOLUME ["/tmp","/var/log/restful-api"]
ADD spring-restful-api-2.0.jar app.jar
# 환경을 상하이 시간으로 설정
RUN echo "Asia/Shanghai" > /etc/timezone
# Spring 환경 정의
ENV SPRING_ENV="dev"
# 서비스 노출 포트 PORT
EXPOSE 8090
# Spring Boot 앱 시작 명령
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=$SPRING_ENV","-c"]
