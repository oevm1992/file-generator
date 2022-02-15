FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /usr/src/app

ARG JAR_FILE=/build/libs/generator-0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]

FROM nginx:1.11-alpine

# Se agregan metadatos a la imagen
LABEL Descripción="Web Malla Promocional" Autor="Arquitectura" Versión="v1.0.0"

RUN apk add --no-cache tzdata
ENV TZ=America/Lima
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /
RUN cd var && mkdir www && cd www && mkdir html && cd html && mkdir fps-app-web-mallapromocional
WORKDIR /
RUN rm -r /etc/nginx/nginx.conf && rm -r /etc/nginx/conf.d/default.conf
COPY docker/nginx/nginx.conf /etc/nginx/
COPY docker/nginx/mallapromocional.conf /etc/nginx/conf.d/
# Se copian los ficheros hacia la carpeta de nginx
COPY dist/fps-app-web-mallapromocional /var/www/html/fps-app-web-mallapromocional