FROM gradle:8.7.0-jdk17-jammy as cache

# build gradle libraries
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY ./parse-logs/build.gradle /home/gradle/java-code/
WORKDIR /home/gradle/java-code
RUN gradle clean build -i --stacktrace

FROM openjdk:17-oraclelinux8

# create user and install linux tools
ARG USERNAME=vscode
ARG USER_GROUP_NAME=workspace
ARG USER_UID=1019
ARG USER_GID=1000
ARG PKG="git vim curl unzip zip sudo findutils"

SHELL ["/bin/bash", "-c"]

RUN microdnf update \
    && microdnf install ${PKG} \
    && groupadd --gid ${USER_GID} ${USER_GROUP_NAME} \
    && useradd --uid ${USER_UID} --shell /bin/bash --gid ${USER_GID} -m ${USERNAME} \
    && echo %${USERNAME} ALL=\(ALL\) NOPASSWD:ALL > /etc/sudoers.d/${USER_GROUP_NAME} \
    && chmod 0440 /etc/sudoers.d/${USER_GROUP_NAME}

# copy gradle libraries
COPY --from=cache /home/gradle/cache_home /home/${USERNAME}/.gradle

# create vscode extension folder
RUN mkdir -p /home/${USERNAME}/.vscode-server/extensions \
    && chown -R ${USERNAME} \
    /home/${USERNAME}/.vscode-server

# copy gradle and download gradle wrapper 8.7
ARG APP_HOME=/app
WORKDIR ${APP_HOME}/parse-logs

COPY ./parse-logs/gradlew ${APP_HOME}/parse-logs/gradlew
COPY ./parse-logs/gradlew.bat ${APP_HOME}/parse-logs/gradlew.bat
COPY ./parse-logs/build.gradle ${APP_HOME}/parse-logs/build.gradle
COPY ./parse-logs/settings.gradle ${APP_HOME}/parse-logs/settings.gradle
COPY ./parse-logs/gradle ${APP_HOME}/parse-logs/gradle
COPY ./parse-logs/.gradle ${APP_HOME}/.gradle
RUN ${APP_HOME}/parse-logs/gradlew build --stacktrace

# prepare workdir
COPY . ${APP_HOME}

COPY ./compose/production/spring/entrypoint /entrypoint
RUN sed -i 's/\r$//g' /entrypoint
RUN chmod +x /entrypoint

COPY ./compose/local/spring/start /start
RUN sed -i 's/\r$//g' /start
RUN chmod +x /start

ENTRYPOINT ["/entrypoint"]
