gradlew命令说明你想使用gradle wrapper;
gradle命令则是使用的你配置在环境变量当中的gradle.


GRADLE_USER_HOME该环境变量决定了执行project/gradle/gradle-rapper.jar时下载project/gradle/gradle-wrapper.properties中指定版本gradle的存放位置。
简言之：在命令行中输入的以gradlew的开头的命令会使用GRADLE_USER_HOME指定环境变量所在位置来存放下载的gradle.
Gradle user home是idea的变量参数，同GRADLE_USER_HOME的含义

https://blog.csdn.net/iot_ai/article/details/106617626